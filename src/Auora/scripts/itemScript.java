package Auora.scripts;

import Auora.model.Item;
import Auora.model.player.DuelArena.Rules;
import Auora.model.player.*;
import Auora.rscache.ItemDefinitions;

/**
 * @author Jonathan spawnscape
 */
public class itemScript {

    public void option1(Player p, int itemId, int interfaceId, int slot) {
        if (p == null) {
            return;
        }
        if (p.isDead()) {
            return;
        }
        switch (itemId) {
            case 11846:
            case 11848:
            case 11850:
            case 11852:
            case 11854:
            case 11856:
                return;
        }
        int[] kills200Only = {1000};

        int[] extremeOnly = {};

        int[] DungMasterCape = {19710};

        int[] Chaotics = {18349, 18351, 18353, 18355, 18357, 18359};

        int[] legendary555Only = {15017};

        int[] adminOnly = {7142};

        int[] donator555Only = {13899, 13902, 13896, 13884, 13890, 13887, 13888, 13889, 13893, 13894, 13895, 13899, 13900,
                13901, 13906, 13907, 13913,
                13919, 13925, 13931, 13885,
                13886, 13891, 13892, 13897, 13898, 13903,
                13904, 13910, 13916,
                13922, 13928, 13858, 13859, 13860, 13861, 13862,
                13863, 13864, 13865, 13866, 13867, 13868, 13869,
                13934, 13937, 13940,
                13943, 13870, 13871, 13872, 13873, 13874, 13875, 13876, 13877,
                13878, 13879, 13880, 13881, 13882, 13883, 13946,
                13949, 13952, 13954, 13955,
                13956, 13957,
                13451, 13458, 13459, 13460, 12670,
                12671, 13450, 13455, 13456, 13457, 14085,
                13740, 13741, 13740, 13742, 13744, 13738};
        if (interfaceId == 387) {
            if (slot < 0 || itemId < 0) {
                return;
            }
            if (itemId == 7927) {
                p.isMorphed = false;
                p.getFrames().sendChatMessage(0, "You are back to normal.");
                p.getAppearence().setNpcType((short) -1);
                p.getMask().setApperanceUpdate(true);
                p.isNpc = false;
                p.getFrames().closeInventoryInterface();
            }
            if (slot <= 15 && p.getEquipment().get(slot) != null) {
                if (!p.getInventory().addItem(p.getEquipment().get(slot).getDefinition().getId(), p.getEquipment().get(slot).getAmount())) {
                    return;
                }
                p.getEquipment().set(slot, null);
            }
        }
        if (interfaceId == 149) {
            if (System.currentTimeMillis() < p.getCombatDefinitions().getLastEmote() - 600) {
                return;
            }
            if (slot < 0 || slot >= Inventory.SIZE)
                return;
            Item item = p.getInventory().getContainer().get(slot);
            if (item == null)
                return;
            if (item.getId() != itemId)
                return;
            int targetSlot = Equipment.getItemType(itemId);
            int currentSlot = targetSlot == 7 ? 6
                    : targetSlot > 7 ? targetSlot - 2 : targetSlot;
            if (!ItemDefinitions.forID(item.getId()).isWearItem()
                    && item.getId() != 3801 && item.getId() != 3803
                    && item.getId() != 14712 && item.getId() != 15084 && item.getId() != 17606 && item.getId() != 15262 && item.getId() != 19032 && item.getId() != 19033 && item.getId() != 19034 && item.getId() != 19035 && item.getId() != 19036 && item.getId() != 19670) {
                p.getFrames().sendChatMessage(0, "You can't wear this item.");
                return;
            }
            boolean isTwoHanded = Equipment.isTwoHanded(item.getDefinition());

            if (p.getDuelSession() != null && p.getDuelSession().getRule(Rules.WHIP_DDS)) {
                if (!p.getDuelSession().equipWep(p, item.getId()))
                    return;
            } else if (p.getDuelPartner() != null && p.getDuelPartner().getDuelSession().getRule(Rules.WHIP_DDS)) {
                if (!p.getDuelPartner().getDuelSession().equipWep(p, item.getId()))
                    return;
            }

            if (p.getDuelSession() != null) {
                if (!p.getDuelSession().canEquip(p, currentSlot))
                    return;
                else if (isTwoHanded
                        && !p.getDuelSession().canEquip(p,
                        Equipment.SLOT_SHIELD))
                    return;
            } else if (p.getDuelPartner() != null) {
                if (!p.getDuelPartner().getDuelSession()
                        .canEquip(p, currentSlot))
                    return;
                else if (isTwoHanded
                        && !p.getDuelPartner().getDuelSession()
                        .canEquip(p, Equipment.SLOT_SHIELD))
                    return;
            }
            for (int i : donator555Only) {
                if (itemId == i) {
                    if (p.getDonatorRights().ordinal() == 0) {
                        p.getFrames().sendChatMessage(0, "You can't wield this item because you are not a Donator.");
                        return;
                    } else {
                        break;
                    }
                }
            }
            for (int i : adminOnly) {
                if (itemId == i) {
                    if (p.getStaffRights() != StaffRights.ADMINISTRATOR && p.getStaffRights() != StaffRights.OWNER && p.getStaffRights() != StaffRights.GLOBAL_ADMIN && p.getStaffRights() != StaffRights.COMMUNITY_MANAGER && p.getStaffRights() != StaffRights.STAFF_MANAGER && p.getStaffRights() != StaffRights.DEVELOPER) {
                        p.getFrames().sendChatMessage(0, "You can't wield this item because you must be an Administrator.");
                        return;
                    } else {
                        break;
                    }
                }
            }

          /*  if ((itemId == 10663 || itemId == 10664 || itemId == 10665 || itemId == 10666 || itemId == 10667 || itemId == 10668 || itemId == 10669 || itemId == 10670 || itemId == 10671 || itemId == 10672 || itemId == 10673 || itemId == 10674) && !p.getSkills().has_required_levels()) {
                return;
            }*/

            for (int i : extremeOnly) {
                if (itemId == i) {
                    if (p.getDonatorRights().ordinal() == 0 || p.getDonatorRights().ordinal() == 2) {
                        p.getFrames().sendChatMessage(0, "You can't wield this item because you must be a Premium Donator.");
                        return;
                    } else {
                        break;
                    }
                }
            }
            if (itemId == 17606) {
                p.animate(829);
                p.graphics(535);
                p.getFrames().sendChatMessage(0, "You drink the potion.. Hmm tastes weird.");
                p.getSkills().set(Skills.MAGIC, p.getSkills().getLevelForXp(Skills.MAGIC) + 10 + Math.round(p.getSkills().getLevelForXp(Skills.MAGIC) * 1 / 100));

            }
            if (itemId >= 9777 && itemId <= 9779 || itemId == 10650) {
                if (p.getSkills().getLevel(17) < 99) {
                    p.getFrames().sendChatMessage(0, "You must have 99 thieving to wield this.");
                    return;
                }
            }

            if (itemId == 4315) {
                if (p.getSkills().getXp(0) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M attack experience to wield this.");
                    return;
                }
            }
            if (itemId == 4317) {
                if (p.getSkills().getXp(1) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M defence experience to wield this.");
                    return;
                }
            }

            if (itemId == 15262) {
                p.getInventory().deleteItem(15262, 1);
                p.getInventory().addItem(12530, 5000);
                p.sm("You open the spirit shard pack and find 5k spirit shards inside.");
            }

            if (itemId == 19032) {
                p.getInventory().deleteItem(19032, 1);
                p.getInventory().addItem(4278, 5000);
                p.sm("You open the Donation Box and find 5000 Donator tokens inside.");
            }
            if (itemId == 19670) {

                p.getDialogue().startDialogue("Titles");

            }

            if (itemId == 19033) {
                p.getInventory().deleteItem(19033, 1);
                p.getInventory().addItem(4278, 10000);
                p.sm("You open the Donation Box and find 10000 Donator tokens inside.");
            }

            if (itemId == 19034) {
                p.getInventory().deleteItem(19034, 1);
                p.getInventory().addItem(4278, 30000);
                p.sm("You open the Donation Box and find 30000 Donator tokens inside.");
            }

            if (itemId == 19035) {
                p.getInventory().deleteItem(19035, 1);
                p.getInventory().addItem(4278, 60000);
                p.sm("You open the Donation Box and find 60000 Donator tokens inside.");
            }

            if (itemId == 19036) {
                p.getInventory().deleteItem(19036, 1);
                p.getInventory().addItem(4278, 115000);
                p.sm("You open the Donation Box and find 115000 Donator tokens inside.");
            }


            if (itemId == 4319) {
                if (p.getSkills().getXp(2) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M strength experience to wield this.");
                    return;
                }
            }
            if (itemId == 4321) {
                if (p.getSkills().getXp(3) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M constitution experience to wield this.");
                    return;
                }
            }
            if (itemId == 18359) {
                if (p.getSkills().getLevelForXp(24) < 80) {
                    p.getFrames().sendChatMessage(0, "You must have level 80 Dungeoneering to wield this.");
                    return;
                }
            }
            if (itemId == 18357) {
                if (p.getSkills().getLevelForXp(24) < 80) {
                    p.getFrames().sendChatMessage(0, "You must have level 80 Dungeoneering to wield this.");
                    return;
                }
            }
            if (itemId == 18355) {
                if (p.getSkills().getLevelForXp(24) < 80) {
                    p.getFrames().sendChatMessage(0, "You must have level 80 Dungeoneering to wield this.");
                    return;
                }
            }
            if (itemId == 18353) {
                if (p.getSkills().getLevelForXp(24) < 80) {
                    p.getFrames().sendChatMessage(0, "You must have level 80 Dungeoneering to wield this.");
                    return;
                }
            }
            if (itemId == 18349) {
                if (p.getSkills().getLevelForXp(24) < 80) {
                    p.getFrames().sendChatMessage(0, "You must have level 80 Dungeoneering to wield this.");
                    return;
                }
            }
            if (itemId == 18351) {
                if (p.getSkills().getLevelForXp(24) < 80) {
                    p.getFrames().sendChatMessage(0, "You must have level 80 Dungeoneering to wield this.");
                    return;
                }
            }
            if (itemId == 4323) {
                if (p.getSkills().getXp(4) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M range experience to wield this.");
                    return;
                }
            }
            if (itemId == 4325) {
                if (p.getSkills().getXp(5) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M prayer experience to wield this.");
                    return;
                }
            }
            if (itemId == 4327) {
                if (p.getSkills().getXp(6) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M magic experience to wield this.");
                    return;
                }
            }
            if (itemId == 4329) {
                if (p.getSkills().getXp(7) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M cooking experience to wield this.");
                    return;
                }
            }
            if (itemId == 4331) {
                if (p.getSkills().getXp(8) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M woodcutting experience to wield this.");
                    return;
                }
            }
            if (itemId == 4333) {
                if (p.getSkills().getXp(9) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M fletching experience to wield this.");
                    return;
                }
            }
            if (itemId == 4335) {
                if (p.getSkills().getXp(10) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M fishing experience to wield this.");
                    return;
                }
            }
            if (itemId == 4337) {
                if (p.getSkills().getXp(11) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M firemaking experience to wield this.");
                    return;
                }
            }
            if (itemId == 4339) {
                if (p.getSkills().getXp(12) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M crafting experience to wield this.");
                    return;
                }
            }
            if (itemId == 4341) {
                if (p.getSkills().getXp(13) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M smithing experience to wield this.");
                    return;
                }
            }
            if (itemId == 4343) {
                if (p.getSkills().getXp(14) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M mining experience to wield this.");
                    return;
                }
            }
            if (itemId == 4345) {
                if (p.getSkills().getXp(15) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M herblore experience to wield this.");
                    return;
                }
            }
            if (itemId == 4347) {
                if (p.getSkills().getXp(16) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M agility experience to wield this.");
                    return;
                }
            }
            if (itemId == 4349) {
                if (p.getSkills().getXp(17) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M thieving experience to wield this.");
                    return;
                }
            }
            if (itemId == 4351) {
                if (p.getSkills().getXp(18) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M slayer experience to wield this.");
                    return;
                }
            }
            if (itemId == 4353) {
                if (p.getSkills().getXp(19) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M farming experience to wield this.");
                    return;
                }
            }
            if (itemId == 4355) {
                if (p.getSkills().getXp(20) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M runecrafting experience to wield this.");
                    return;
                }
            }
            if (itemId == 4357) {
                if (p.getSkills().getXp(21) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M hunter experience to wield this.");
                    return;
                }
            }
            if (itemId == 4359) {
                if (p.getSkills().getXp(22) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M construction experience to wield this.");
                    return;
                }
            }
            if (itemId == 4361) {
                if (p.getSkills().getXp(23) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M summoning experience to wield this.");
                    return;
                }
            }
            if (itemId == 19709) {
                if (p.getSkills().getXp(24) < 100000000) {
                    p.getFrames().sendChatMessage(0, "You must have 100M dungeoneering experience to wield this.");
                    return;
                }
            }
            if (p.getEquipment().contains(14600) && p.getAppearence().getGender() == 0) {
                p.getEquipment().deleteItem(14600, 1);
                p.getEquipment().set(4, new Item(14601));
                p.getEquipment().refresh();
            }
            if (p.getEquipment().contains(14604) && p.getAppearence().getGender() == 0) {
                p.getEquipment().deleteItem(14604, 1);
                p.getEquipment().set(7, new Item(14603));
                p.getEquipment().refresh();
            }
            if (p.getEquipment().contains(14601) && p.getAppearence().getGender() == 1) {
                p.getEquipment().deleteItem(14601, 1);
                p.getEquipment().set(4, new Item(14600));
            }
            if (p.getEquipment().contains(14595) && p.getAppearence().getGender() == 1) {
                p.getEquipment().deleteItem(14595, 1);
                p.getEquipment().set(4, new Item(14600));
                p.getEquipment().refresh();
            }
            if (p.getEquipment().contains(14603) && p.getAppearence().getGender() == 1) {
                p.getEquipment().deleteItem(14603, 1);
                p.getEquipment().set(7, new Item(14604));
                p.getEquipment().refresh();
            }
            boolean hasReq = true;
            if (item.getDefinition().skillRequirimentId != null) {
                for (int skillIndex = 0; skillIndex < item.getDefinition().skillRequirimentId.size(); skillIndex++) {
                    int reqId = item.getDefinition().skillRequirimentId.get(skillIndex);
                    int reqLvl = -1;
                    if (item.getDefinition().skillRequirimentLvl.size() > skillIndex)
                        reqLvl = item.getDefinition().skillRequirimentLvl.get(skillIndex);
                    if (reqId > 25 || reqId < 0 || reqLvl < 0 || reqLvl > 120)
                        continue;
                    if (p.getSkills().getLevelForXp(reqId) < reqLvl) {
                        if (hasReq)
                            p.getFrames().sendChatMessage((byte) 0, "You are not high enough level to use this item.");
                        p.getFrames().sendChatMessage((byte) 0, "You need to have a " + (Skills.SKILL_NAME[reqId].toLowerCase()) + " level of " + reqLvl + ".");
                        hasReq = false;
                    }
                }
            }
            if (!hasReq) {
                return;
            }
            if (item.getDefinition().getId() == itemId) {
                if (targetSlot == -1) {
                    return;
                }
                if (Equipment.isTwoHanded(item.getDefinition())
                        && p.getInventory().getFreeSlots() == 0
                        && p.getEquipment().get(5) != null) {
                    p.getFrames().sendChatMessage((byte) 0,
                            "Not enough free space in your inventory.");
                    return;
                }
                p.getInventory().deleteItem(item.getDefinition().getId(), item.getAmount());
                if (p.getEquipment().get(targetSlot) != null && (itemId != p.getEquipment().get(targetSlot).getDefinition().getId() || !item.getDefinition().isStackable())) {
                    if (p.getInventory().getContainer().get(slot) == null) {
                        p.getInventory().getContainer().set(slot, p.getEquipment().get(targetSlot));
                    } else {
                        p.getInventory().addItem(p.getEquipment().get(targetSlot).getId(), p.getEquipment().get(targetSlot).getAmount());
                    }
                    p.getInventory().refresh();
                    p.getEquipment().set(targetSlot, null);
                }
                if (targetSlot == 3) {
                    item.getDefinition();
                    if (Equipment.isTwoHanded(ItemDefinitions.forID(itemId)) && p.getEquipment().get(5) != null) {
                        if (!p.getInventory().addItem(p.getEquipment().get(5).getDefinition().getId(), p.getEquipment().get(5).getAmount())) {
                            p.getInventory().addItem(itemId, item.getAmount(), slot);
                            return;
                        }
                        p.getEquipment().set(5, null);
                    }

                } else if (targetSlot == 5) {
                    item.getDefinition();

                    if (p.getEquipment().get(3) != null && Equipment.isTwoHanded(ItemDefinitions.forID(p.getEquipment().get(3).getDefinition().getId()))) {
                        //if(Equipment.isTwoHanded(ItemDefinitions.forID(itemId)) && player.getEquipment().get(3) != null) {
                        if (!p.getInventory().addItem(p.getEquipment().get(3).getDefinition().getId(), p.getEquipment().get(3).getAmount())) {
                            //player.getInventory().addItem(itemId, item.getAmount(), slot);
                            p.getInventory().addItem(p.getEquipment().get(targetSlot).getId(), p.getEquipment().get(targetSlot).getAmount());
                            return;
                        }
                        p.getEquipment().set(3, null);
                    }
                }
                int oldAmt = 0;
                if (p.getEquipment().get(targetSlot) != null) {
                    oldAmt = p.getEquipment().get(targetSlot).getAmount();
                }
                Item item2 = new Item(itemId, oldAmt + item.getAmount());
                p.getEquipment().set(targetSlot, item2);
            }
        }

    }

	/*public void drop(Player player, int itemId, int interfaceId, int slot) {
    Packets.PacketId_52(InStream Packet, int Size, Player player);
	}*/

	/*public void drop(Player player, int itemId, int interfaceId, int slot, int amount) {
        Item item = player.getInventory().getContainer().get(slot);
		if (item == null)
			return;
		if (item.getId() != itemId)
			return;
		if(item.getId() == 12852) {
		player.getFrames().sendChatMessage((byte) 0, "You can not drop this item.");
		return;
		}
	        if(item.getId() == 4045) {
		player.getMask().setLastChatMessage(new ChatMessage(0, 0, "Owch!"));
		player.getMask().setChatUpdate(true);
		player.getFrames().sendChatMessage((byte) 0, "You drop the expolsive potion and it goes BOOM!");
		player.animate(827);
		player.getInventory().deleteItem(itemId, 1);
			player.hit(100);
			return;
		}
		player.animate(827);
		player.getFrames().sendChatMessage((byte) 0, "You drop the item id - ["+itemId+"].");
		player.getFrames().sendGroundItem(player.getLocation(), player.getInventory().getContainer().get(slot), false);
		player.getInventory().deleteItem(itemId, amount);
	}*/

    public void examine(Player p, int itemId, int slot) {
        if (p == null) {
            return;
        }
        if (itemId < 0 || itemId > 19700) {
            p.getFrames().sendChatMessage(0, "You examine the " + ItemDefinitions.forID(itemId).name + ", ID: " + itemId + ".");
            return;
        }
        if (itemId == 995) {
            int amount = p.getInventory().getContainer().get(slot).getAmount();
            p.getFrames().sendChatMessage(0, "" + amount + " x Coins.");
            return;
        }
        p.getFrames().sendChatMessage(0, "You examine the " + ItemDefinitions.forID(itemId).name + ", ID: " + itemId + ".");
    }

    public void operate(Player p, Item itemId) {
        if (itemId == null)
            return;
        if (itemId.getId() == 12844) {
            p.animate(8990);
        } else if (itemId.getId() == 11284) {
            p.DFSSpecial = true;
        }
    }

    //public void thisonobject(Player player, int onObject) {
    //}

    //public void thisonplayer(Player player, Player onPlayer) {
    //}

}
