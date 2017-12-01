package Auora.net.mysql.impl;

import Auora.GameServer;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.net.mysql.SQLCallback;
import Auora.util.Misc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles Store Connections
 *
 * @Author Jonny
 */
public class Store {

    public static void claimItem(Player player) {
        String name = Misc.formatPlayerNameForDisplay(player.getUsername().replaceAll("_", " "));
        player.claimingStoreItems = true;
        GameServer.getStorePool().executeQuery(
                "Select * from `orders` WHERE `option_selection2` = '" + name.toLowerCase() + "'", new SQLCallback() {
                    @Override
                    public void queryComplete(ResultSet rs) throws SQLException {
                        if (rs == null) {
                            player.sendMessage("You currently don't have anything in your collection box!");
                            return;
                        }
                        boolean hasGrabbed = false;
                        while (rs.next()) {
                            hasGrabbed = true;
                            String item_ids = rs.getString("option_selection1");

                            if (rs.getString("payment_status").equals("Completed")) {

                                if (rs.getString("option_selection1").equals("Premium Rank")) {
                                    player.sm("<col=ff0000><shad=0>U've been given Premium Rank.");
                                    player.sm("<col=ff0000><shad=0>Thank you for donating towards Auora614.");
                                    player.setDonatorRights(DonatorRights.PREMIUM);
                                } else if (rs.getString("option_selection1").equals("Extreme Rank")) {
                                    player.sm("<col=ff0000><shad=0>U've been given Extreme Rank.");
                                    player.sm("<col=ff0000><shad=0>Thank you for donating towards Auora614.");
                                    player.setDonatorRights(DonatorRights.EXTREME);
                                } else if (rs.getString("option_selection1").equals("Legendary Rank")) {
                                    player.sm("<col=ff0000><shad=0>U've been given Legendary Rank.");
                                    player.sm("<col=ff0000><shad=0>Thank you for donating towards Auora614.");
                                    player.setDonatorRights(DonatorRights.LEGENDARY);
                                } else if (rs.getString("option_selection1").equals("Dicer Rank")) {
                                    player.sm("<col=ff0000><shad=0>U've been given Dicer Rank.");
                                    player.sm("<col=ff0000><shad=0>Thank you for donating towards Auora614.");
                                    player.dicerRank = 1;
                                } else if (rs.getString("option_selection1").equals("Double Cash Rank")) {
                                    player.sm("<col=ff0000><shad=0>U've been given Double Cash Rank.");
                                    player.sm("<col=ff0000><shad=0>Thank you for donating towards Auora614.");
                                    player.doublecash = 1;
                                } else if (rs.getString("option_selection1").equals("x10 Mystery Boxes")) {
                                    player.sm("<col=ff0000><shad=0>10 Mystery Boxes have been added to your bank.");
                                    player.sm("<col=ff0000><shad=0>Thank you for donating towards Auora614.");
                                    player.getBank().addItem(6199, 10, 0);
                                } else if (rs.getString("option_selection1").equals("x50 Mystery Boxes")) {
                                    player.sm("<col=ff0000><shad=0>50 Mystery Boxes have been added to your bank.");
                                    player.sm("<col=ff0000><shad=0>Thank you for donating towards Auora614.");
                                    player.getBank().addItem(6199, 50, 0);
                                } else if (rs.getString("option_selection1").equals("x100 Mithril Seeds")) {
                                    player.sm("<col=ff0000><shad=0>100 Mithril Seeds have been added to your bank.");
                                    player.sm("<col=ff0000><shad=0>Thank you for donating towards Auora614.");
                                    player.getBank().addItem(299, 100, 0);
                                } else if (rs.getString("option_selection1").equals("x250 Mithril Seeds")) {
                                    player.sm("<col=ff0000><shad=0>250 Mithril Seeds have been added to your bank.");
                                    player.sm("<col=ff0000><shad=0>Thank you for donating towards Auora614.");
                                    player.getBank().addItem(299, 250, 0);
                                }


                            } else if (rs.getString("payment_status").equals("Pending")) {
                                player.sm("Your payment is Pending, please contact Direct or Mike.");
                            }


                            //for (int i = 0; i < item_ids.length; i++) {
                                /*if (!player.getInventory().hasRoomFor(Integer.parseInt(item_ids[i]),
										Integer.parseInt(amounts[i]))) {
									player.getBank().addItem(Integer.parseInt(item_ids[i]), Integer.parseInt(amounts[i]), 0);
									player.sendMessage("<col=ff0000>" + amounts[i] + "x "
													+  ItemDefinitions.forID(Integer.parseInt(item_ids[i])).name
													+ " has been added to your bank.");
								} else {
									player.getInventory().addItem(Integer.parseInt(item_ids[i]),
											Integer.parseInt(amounts[i]));
									player.sendMessage("<col=ff0000>" + amounts[i] + "x "
													+ ItemDefinitions.forID(Integer.parseInt(item_ids[i])).name
													+ " has been added to your inventory.");
								}*/
								/*Item donationItem = new Item(Integer.parseInt(item_ids[i]), Integer.parseInt(amounts[i]));
								Logs.log(player, "donations",
										new String[] {
												"Ip: "+player.getIpAddress()+"",
												"Computer Address: "+player.getComputerAddress()+"",
												"Mac Address: "+player.getMacAddress()+"",
												"Serial Address: "+player.getSerialAddress()+"",
												"Item: "+donationItem.getDefinition().getName()+" ("+donationItem.getId()+")",
												"Amount: "+donationItem.getAmount()+""
										});*/
                            //}
                            player.save();
                        }
                        if (!hasGrabbed) {
                            player.claimingStoreItems = false;
                            player.sendMessage("You currently don't have anything in your collection box!");
                            return;
                        }
                        GameServer.getStorePool().executeQuery(
                                "DELETE FROM `orders` WHERE `option_selection2` = '" + name.toLowerCase() + "'",
                                new SQLCallback() {
                                    @Override
                                    public void queryComplete(ResultSet rs) throws SQLException {
                                        player.claimingStoreItems = false;
                                    }

                                    @Override
                                    public void queryError(SQLException e) {
                                        e.printStackTrace();
                                    }
                                });
                    }

                    @Override
                    public void queryError(SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

}
