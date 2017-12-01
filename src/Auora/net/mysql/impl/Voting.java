package Auora.net.mysql.impl;

import Auora.GameServer;
import Auora.GameSettings;
import Auora.model.player.Player;
import Auora.model.player.account.PlayerSaving;
import Auora.net.mysql.SQLCallback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Voting {

    public static void useAuth(Player player, String a) {
        final String authCode = a.replaceAll("[\"\\\'/]", "");
        if (authCode.length() > 10) {
            player.sendMessage("Your auth code can only be under 10 characters.");
            return;
        }
        GameServer.getVotingPool().executeQuery(
                "SELECT * FROM `auth_codes` WHERE auth = '" + authCode + "' LIMIT 1",
                new SQLCallback() {
                    @Override
                    public void queryComplete(ResultSet rs) throws SQLException {

                        while (rs.next()) {
                            GameServer.getVotingPool().executeQuery(
                                    "DELETE FROM `auth_codes` WHERE auth = '" + authCode + "' LIMIT 1",
                                    new SQLCallback() {
                                        @Override
                                        public void queryComplete(ResultSet rs) throws SQLException {


                                            if (GameSettings.DOUBLE_VOTING) {
                                                player.getInventory().addItem(995, 4000000);
                                                player.votePoints += 4;

                                            } else if (GameSettings.TRIPLE_VOTING) {
                                                player.getInventory().addItem(995, 6000000);
                                                player.votePoints += 6;
                                            } else {
                                                player.getInventory().addItem(995, 2000000);
                                                player.votePoints += 2;
                                            }
                                            player.sendMessage("Your auth code has been claimed!");
                                            PlayerSaving.save(player, false);
                                        }


                                        @Override
                                        public void queryError(SQLException e) {
                                            e.printStackTrace();
                                        }
                                    });
                        }

                    }

                    @Override
                    public void queryError(SQLException e) {
                        e.printStackTrace();
                    }
                });

    }
}