package Auora.model.player;

import Auora.model.Item;
import Auora.model.World;
import Auora.network.packet.Packet.PacketType;
import Auora.network.packet.PacketBuilder;
import Auora.rscache.NpcDefinitions;
import Auora.service.logic.LogicService;
import Auora.util.Misc;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * PlayerUpdate.java
 * 
 * @project RuneCore
 * @author 'Mystic Flow
 * @date 14 Aug 2011
 */
public class PlayerUpdate {

	private final Player player;
	private final List<Player> localPlayers = new LinkedList<Player>();
	private final boolean[] playerExists = new boolean[2048];

	public PlayerUpdate(Player player) {
		this.player = player;
	}

	public void loginData(PacketBuilder stream) {
		try {
			stream.startBitAccess();
			stream.writeBits(30, player.getLocation().getY()
					| ((player.getLocation().getZ() << 28) | (player
							.getLocation().getX() << 14)));
			int playerIndex = player.getIndex();
			for (int index = 1; index < 2048; index++) {
				if (index == playerIndex)
					continue;
				Player other = World.getPlayers().get(index);
				if (other == null || !other.isOnline()) {
					stream.writeBits(18, 0);
					continue;
				}
				if (!player.getLocation().withinDistance(other.getLocation())) {
					stream.writeBits(18, 0);
					continue;
				}
				stream.writeBits(18, other.getLocation().get18BitsHash());
			}
			stream.finishBitAccess();
		} catch (Exception e) {

		}
	}

	public void sendUpdate() {
		try {
			if (player.getMask().getRegion().isDidMapRegionChange()) {
				player.getFrames().sendMapRegion(true);
			}
			boolean chatUpdate = player.getMask().isChatUpdate();
			PacketBuilder packet = new PacketBuilder(53, PacketType.VAR_SHORT);
			PacketBuilder updateBlock = new PacketBuilder();
			appendPlayerUpdateBlock(updateBlock, player, false);
			packet.startBitAccess();
			if (player.getMask().isUpdateNeeded()) {
				packet.writeBits(1, 1);
				sendLocalPlayerUpdate(packet, player);
			} else {
				packet.writeBits(1, 0);
			}
			if (chatUpdate) {
				player.getFrames().sendPublicChatMessage(player.getIndex(),
						player.getClientCrownId(),
						player.getMask().getLastChatMessage());
			}
			Iterator<Player> it = localPlayers.iterator();
			while (it.hasNext()) {
				Player other = it.next();
				if (other != null && (!other.getConnection().isDisconnected())&& other.getLocation().withinDistance(
								player.getLocation(), 16)) {
					if (chatUpdate) {
						other.getFrames().sendPublicChatMessage(player.getIndex(), player.getClientCrownId(),
								player.getMask().getLastChatMessage());
					}
					appendPlayerUpdateBlock(updateBlock, other, false);
					if (other.getMask().isUpdateNeeded()) {
						packet.writeBits(1, 1);
						packet.writeBits(11, other.getIndex());
						sendLocalPlayerUpdate(packet, other);
						continue;
					}
					packet.writeBits(1, 0);
				} else {
					if(other.getConnection().isDisconnected()) {
						if(!LogicService.meetsCondition(other)) {
							if (chatUpdate) {
								other.getFrames().sendPublicChatMessage(
										player.getIndex(), player.getClientCrownId(),
										player.getMask().getLastChatMessage());
							}
							appendPlayerUpdateBlock(updateBlock, other, false);
							if (other.getMask().isUpdateNeeded()) {
								packet.writeBits(1, 1);
								packet.writeBits(11, other.getIndex());
								sendLocalPlayerUpdate(packet, other);
								continue;
							}
							packet.writeBits(1, 0);
							continue;
						}
 					}
					playerExists[other.getIndex()] = false;
					packet.writeBits(1, 1);
					packet.writeBits(11, other.getIndex());
					removeLocalPlayer(packet, other);
					it.remove();
				}
			}
			if (chatUpdate) {
				player.getMask().setChatUpdate(false);
			}
			int addedCount = 0;
			for (Player other : World.getPlayers()) {
				if (addedCount >= 10 || localPlayers.size() >= 180) {
					break;
				}
				if (other == null) {
					continue;
				}
				if (other.getIndex() == player.getIndex()  || (other.getConnection().isDisconnected()&& LogicService.meetsCondition(other))
						|| playerExists[other.getIndex()]
						|| !player.getLocation().withinDistance(
								other.getLocation(), 16) || !other.isOnline()) {
					continue;
				}
				addedCount++;
				localPlayers.add(other);
				packet.writeBits(1, 1);
				packet.writeBits(11, other.getIndex());
				addLocalPlayer(packet, other);
				appendPlayerUpdateBlock(updateBlock, other, true);
				playerExists[other.getIndex()] = true;
			}
			packet.writeBits(1, 0);
			packet.finishBitAccess();
			packet.writeBytes(updateBlock.getBuffer());
			player.getConnection().getChannel().write(packet.build());
		} catch (Exception e) {
			e.printStackTrace();//StackTrace();
		}
	}

	private void addLocalPlayer(PacketBuilder packet, Player other) {
		try {
			packet.writeBits(2, 0);
			boolean hashUpdated = false;
			packet.writeBits(1, hashUpdated ? 0 : 1);
			if (!hashUpdated) {
				packet.writeBits(2, 3);
				packet.writeBits(18, other.getLocation().get18BitsHash());
			}
			packet.writeBits(6, other.getLocation().getX()
					- (other.getLocation().getRegionX() << 6));
			packet.writeBits(6, other.getLocation().getY()
					- (other.getLocation().getRegionY() << 6));
			packet.writeBits(1, 1);
		} catch (Exception e) {

		}
	}

	private void sendLocalPlayerUpdate(PacketBuilder packet, Player other) {
		try {
			if (other.getMask().getRegion().isDidTeleport()) {
				sendLocalPlayerTeleport(packet, other);
				return;
			}
			int walkDir = other.getWalk().getWalkDir();
			int runDir = other.getWalk().getRunDir();
			sendLocalPlayerStatus(packet, walkDir > -1 ? 1 : runDir > -1 ? 2
					: 0, true);
			if (walkDir < 0 && runDir < 0)
				return;
			packet.writeBits(walkDir > -1 ? 3 : 4, walkDir > -1 ? walkDir
					: runDir);
		} catch (Exception e) {

		}
	}

	private void sendLocalPlayerTeleport(PacketBuilder packet, Player other) {
		try {
			sendLocalPlayerStatus(packet, 3, true);
			packet.writeBits(1, 1);
			packet.writeBits(30, other.getLocation().get30BitsHash());
		} catch (Exception e) {

		}
	}

	private void sendLocalPlayerStatus(PacketBuilder packet, int type,
			boolean status) {
		try {
			packet.writeBits(1, status ? 1 : 0);
			packet.writeBits(2, type);
		} catch (Exception e) {

		}
	}

	private void removeLocalPlayer(PacketBuilder packet, Player other) {
		try {
			sendLocalPlayerStatus(packet, 0, false);
			packet.writeBits(1, 0);
		} catch (Exception e) {

		}
	}

	
	
	private void appendPlayerUpdateBlock(PacketBuilder updateBlock,
			Player other, boolean forceAppearance) {
		try {
			if (!other.getMask().isUpdateNeeded() && !forceAppearance) {
				return;
			}
			int maskData = 0;
			if (other.getMask().isHit2Update()) {
				//("hit 2");
				maskData |= 0x10000;
			}
			if (other.getMask().isAnimationUpdate()) {
				//("anim");
				maskData |= 0x80;
			}
			if (other.getMask().isGraphicUpdate()) {
				//("graphics "+other.getMask().getLastGraphics().getId());
				maskData |= 0x1000;
			}
			if (other.getWalk().getWalkDir() != -1
					|| other.getWalk().getRunDir() != -1) {
				maskData |= 0x10;
				//("walk dir");
			}
			/*
			 * if (other.getMask().isChatUpdate() != null) { maskData |= 0x400;
			 * }
			 */
			if (other.getMask().isTurnToUpdate()) {
				//("turn 2");
				maskData |= 0x2;
			}
			if (other.getMask().isApperanceUpdate() || forceAppearance) {
				//("appearance");
				maskData |= 0x20;
			}
			if (other.getMask().getLastHeal() != null) {
				//("heal");
				maskData |= 0x100;
			}
			if (other.getWalk().isDidTele()) {
				//("tele");
				maskData |= 0x2000;
			}
			if (other.getMask().isHitUpdate()) {
				//("norm hit");
				maskData |= 0x40;
			}
		
			if (maskData > 128)
				maskData |= 0x1;
			if (maskData > 32768)
				maskData |= 0x800;
			updateBlock.writeByte(maskData);
			if (maskData > 128)
				updateBlock.writeByte(maskData >> 8);
			if (maskData > 32768)
				updateBlock.writeByte(maskData >> 16);
			if (other.getMask().isHit2Update()) {
				applyHit2Mask(other, updateBlock);
			}
			if (other.getMask().isAnimationUpdate()) {
				applyAnimationMask(other, updateBlock);
			}
			if (other.getMask().isGraphicUpdate()) {
				applyGraphicMask(other, updateBlock);
			}
			if (other.getWalk().getWalkDir() != -1
					|| other.getWalk().getRunDir() != -1) {
				applyMovementMask(other, updateBlock);
			}
			/*
			 * if (other.getMask().getForceText() != null) {
			 * applyForceText(other, updateBlock); }
			 */
			if (other.getMask().isTurnToUpdate()) {
				applyTurnToMask(other, updateBlock);
			}
			if (other.getMask().isApperanceUpdate() || forceAppearance) {
				applyAppearanceMask(other, updateBlock);
			}
			if (other.getMask().getLastHeal() != null) {
				applyHealMask(other, updateBlock);
			}
			if (other.getWalk().isDidTele()) {
				applyTeleTypeMask(updateBlock);
			}
			if (other.getMask().isHitUpdate()) {
				applyHitMask(other, updateBlock);
			}
			
			//("---------");
		} catch (Exception e) {

		}
	}


	@SuppressWarnings("unused")
	private static void applyForceText(Player p, PacketBuilder updateBlock) {
		// updateBlock.writeRS2String(p.getMask().getForceText().getLastForceText());
	}

	public static void applyForceMovement(Player p, PacketBuilder updateBlock) {

	}

	public static void applyTurnToMask(Player p, PacketBuilder outStream) {
		outStream.writeShortA(p.getMask().getTurnToIndex());
	}

	public static void applyTeleTypeMask(PacketBuilder outStream) {
		outStream.writeByteC(127);
	}

	public static void applyHitMask(Player p, PacketBuilder outStream) {
		try {
			int dmg = p.getHits().getHitDamage1();
			if(dmg < 0) {
				dmg = 0;
			}
			outStream.writeSmart(dmg);
			outStream.writeByte(p.getHits().getHitType1());
			int Amthp = p.getSkills().getHitPoints();
			int Maxhp = p.getSkills().getLevelForXp(3) * 10;
			if (Amthp > Maxhp)
				Amthp = Maxhp;
			int worked = Amthp * 255 / Maxhp;
			outStream.writeByte(worked);
		} catch (Exception e) {

		}
	}

	public static void applyHit2Mask(Player p, PacketBuilder outStream) {
		try {
			outStream.writeSmart(p.getHits().getHitDamage2());
			outStream.writeByteS(p.getHits().getHitType2());
		} catch (Exception e) {

		}
	}

	public static void applyHealMask(Player p, PacketBuilder outStream) {
		try {
			outStream.writeShort(p.getMask().getLastHeal().getHealDelay());
			outStream.writeByteS(p.getMask().getLastHeal().getBarDelay());
			outStream.writeByteS(p.getMask().getLastHeal().getHealSpeed());
		} catch (Exception e) {

		}
	}

	public static void applyAnimationMask(Player p, PacketBuilder outStream) {
		try {
			outStream.writeShortA(p.getMask().getLastAnimation().getId());
			outStream.writeByteA(p.getMask().getLastAnimation().getDelay());
		} catch (Exception e) {

		}
	}

	public static void applyGraphicMask(Player p, PacketBuilder outStream) {
		try {
			outStream.writeShortA(p.getMask().getLastGraphics().getId());
			outStream.writeInt(p.getMask().getLastGraphics().getDelay());
			outStream.writeByte(p.getMask().getLastGraphics().getHeight());
		} catch (Exception e) {

		}
	}

	public static void applyMovementMask(Player p, PacketBuilder outStream) {
		try {
			outStream.writeByteC(p.getWalk().getWalkDir() != -1 ? 1 : 2);
		} catch (Exception e) {

		}
	}

	public static void applyAppearanceMask(Player p, PacketBuilder outStream) {
		try {
			PacketBuilder playerUpdate = new PacketBuilder();
			NpcDefinitions def = p.getAppearence().getNpcType() != -1 ? NpcDefinitions.forID(p.getAppearence().getNpcType()) : null;
			int hash = 0;
			hash |= p.getAppearence().getGender() & 0x1;
			playerUpdate.writeByte(hash);
			int amountOfKills = p.dangerousKills + p.safeKills;
		
			if (p.skullTimer > 0){
				playerUpdate.writeByte(-1);
				playerUpdate.writeByte(0);
			} else if (p.redskullTimer > 0){
				playerUpdate.writeByte(-1);
				playerUpdate.writeByte(1);			
			} else {
				playerUpdate.writeByte(-1);
				playerUpdate.writeByte(-1);
			}
			playerUpdate.writeByte(p.getPrayer().getHeadIcon());
			if (p.getAppearence().getNpcType() == -1) {
				for (int i = 0; i < 4; i++) {
					if (p.getEquipment().get(i) == null)
						playerUpdate.writeByte(0);
					else
						playerUpdate.writeShort(32768 + p.getEquipment().get(i)
								.getDefinition().equipId);
				}
				if ((p.getEquipment().get(Equipment.SLOT_CHEST) != null) && p.getEquipment().get(Equipment.SLOT_CHEST).getId() > 0) {
					playerUpdate.writeShort(32768 + p.getEquipment()
							.get(Equipment.SLOT_CHEST).getDefinition().equipId);
				} else {
					playerUpdate
							.writeShort(0x100 + p.getAppearence().getLook()[2]);
				}
				if ((p.getEquipment().get(Equipment.SLOT_SHIELD) != null)&& p.getEquipment().get(Equipment.SLOT_SHIELD).getId() > 0) {
					playerUpdate
							.writeShort(32768 + p.getEquipment()
									.get(Equipment.SLOT_SHIELD).getDefinition().equipId);
				} else {
					playerUpdate.writeByte((byte) 0);
				}
				Item chest = p.getEquipment().get(Equipment.SLOT_CHEST);
				if (chest != null && chest.getId() > 0) {
					if (!Equipment.isFullBody(chest.getDefinition())) {
						playerUpdate.writeShort(0x100 + p.getAppearence()
								.getLook()[3]);
					} else {
						playerUpdate.writeByte((byte) 0);
					}
				} else {
					playerUpdate
							.writeShort(0x100 + p.getAppearence().getLook()[3]);
				}
				if ((p.getEquipment().get(Equipment.SLOT_LEGS) != null) &&  p.getEquipment().get(Equipment.SLOT_LEGS).getId() > 0) {
					playerUpdate.writeShort(32768 + p.getEquipment()
							.get(Equipment.SLOT_LEGS).getDefinition().equipId);
				} else {
					playerUpdate
							.writeShort(0x100 + p.getAppearence().getLook()[5]);
				}
				Item hat = p.getEquipment().get(Equipment.SLOT_HAT);
				if (hat != null && hat.getId() > 0) {
					if (!Equipment.isFullHat(hat.getDefinition())
							&& !Equipment.isFullMask(hat.getDefinition())) {
						playerUpdate.writeShort(0x100 + p.getAppearence()
								.getLook()[0]);
					} else {
						playerUpdate.writeByte((byte) 0);
					}
				} else {
					playerUpdate
							.writeShort(0x100 + p.getAppearence().getLook()[0]);
				}
				if ((p.getEquipment().get(Equipment.SLOT_HANDS) != null) &&  p.getEquipment().get(Equipment.SLOT_HANDS).getId() > 0) {
					playerUpdate.writeShort(32768 + p.getEquipment()
							.get(Equipment.SLOT_HANDS).getDefinition().equipId);
				} else {
					playerUpdate
							.writeShort(0x100 + p.getAppearence().getLook()[4]);
				}
				if ((p.getEquipment().get(Equipment.SLOT_FEET) != null) &&  p.getEquipment().get(Equipment.SLOT_FEET).getId() > 0) {
					playerUpdate.writeShort(32768 + p.getEquipment()
							.get(Equipment.SLOT_FEET).getDefinition().equipId);
				} else {
					playerUpdate
							.writeShort(0x100 + p.getAppearence().getLook()[6]);
				}
				if (hat != null &&  hat.getId() > 0) {
					if (!Equipment.isFullMask(hat.getDefinition())) {
						playerUpdate.writeShort(0x100 + p.getAppearence()
								.getLook()[1]);
					} else {
						playerUpdate.writeByte((byte) 0);
					}
				} else {
					playerUpdate
							.writeShort(0x100 + p.getAppearence().getLook()[1]);
				}
			} else {
				playerUpdate.writeShort(-1);
				playerUpdate.writeShort(def.id);
				playerUpdate.writeByte(0);
			}
			for (int j = 0; j < 5; j++) {
				playerUpdate.writeByte(p.getAppearence().getColour()[j]);
			}
			playerUpdate.writeShort(p.getAppearence().getNpcType() != -1
					&& def != null ? -1 : p.getEquipment().getRenderAnim());
			StringBuilder nameBuilder = new StringBuilder();
			
			/*
			if (p.showDonator) {
				if (p.isDonator) {
					nameBuilder.append("<col=298A08>"+(p.isExtremeMember ? "$$$" : "$")+"</col> ");
				}
			}
			
			if (p.getPlayerSettings().getTitleFlag() != -1) {
				if(p.getPlayerSettings().getTitleFlag() > 26) {
					p.getPlayerSettings().setTitleFlag((byte)-1);
				}
				nameBuilder.append("<col=cc6600>"
						+ p.getPlayerSettings().getTitle() + "</col> ");
			}
			*/
			nameBuilder.append(p.getDisplayName());

			if (p.getTitles().getTitle() == null || p.getTitles().getTitle().equals("")){
				playerUpdate.writeRS2String(Misc.formatPlayerNameForDisplay(p.getDisplayName()));
			} else {
				if(p.getStaffRights() == StaffRights.PLAYER) {
					if (!p.getToggleName()) {
						playerUpdate.writeRS2String(p.getTitles().getColor() + p.getTitles().getShad() + p.getTitles().getTitle() + "</col></shad> " + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "");
					} else {
						playerUpdate.writeRS2String(p.getDonatorRights().getColor() + p.getDonatorRights().getShad() + p.getDonatorRights().getTitle() + "</col></shad> " + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "");
					}
				} else {
					if(!p.getToggleName()) {
						playerUpdate.writeRS2String(p.getTitles().getColor() + p.getTitles().getShad() + p.getTitles().getTitle() + "</col></shad> " + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "");
					} else {
						playerUpdate.writeRS2String(p.getStaffRights().getColor() + p.getStaffRights().getShad() + p.getStaffRights().getTitle() + "</col></shad> " + Misc.formatPlayerNameForDisplay(p.getDisplayName()) + "");
					}
				}
			}

			playerUpdate.writeShort(p.getSkills().getCombatLevel());
			playerUpdate.writeShort(1);
			playerUpdate.writeByte(0);
			outStream.writeByteS(playerUpdate.position());
			outStream.addBytes128(playerUpdate.getBuffer());
		} catch (Exception e) {

		}
	}

}
