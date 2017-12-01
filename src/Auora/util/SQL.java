package Auora.util;

import Auora.model.player.Player;

import java.sql.*;

public class SQL {
    public static Connection con = null;
    public static Statement stmt;
    private static long lastUsed = System.currentTimeMillis();

    public static void createConnection() {
        try {
            Class.forName("com.spawnscape.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(
                    //	"jdbc:spawnscape.mysql://192.254.189.83/jsirens_donate/jsirens_donate/reckson11");
                    "jdbc:spawnscape.mysql://192.254.189.83/jsirens_donate", "jsirens_donate", "reckson11");
            stmt = con.createStatement();
            System.out.println("Connected");
        } catch (Exception e) {
            System.out.println("Connection Problem");
        }
    }

    public static ResultSet query(String s) throws SQLException {
        try {
            if (s.toLowerCase().startsWith("select")) {
                ResultSet rs = stmt.executeQuery(s);
                return rs;
            } else {
                stmt.executeUpdate(s);
            }
            return null;
        } catch (Exception e) {
            destroyConnection();
            createConnection();
        }
        return null;
    }

    public static void destroyConnection() {
        try {
            stmt.close();
            con.close();
        } catch (Exception e) {
        }
    }

    public static Connection getConnection() throws Exception {
        if (con == null) {
            throw new Exception("connection is null");
        }
        if (System.currentTimeMillis() - lastUsed > 300000) {
            try {
                lastUsed = System.currentTimeMillis();
                con.close();
                createConnection();
            } catch (Exception e) {
                throw new Exception("error refreshing database connection");
            }
        }
        return con;
    }

    public static boolean checkStatus(Player p) {
        try {
            Statement s = getConnection().createStatement();
            ResultSet results = s.executeQuery("SELECT * FROM `status` WHERE `username` = '" + Misc.formatPlayerNameForDisplay(p.getUsername()) + "' AND `given` = '0' LIMIT 10;");
            while (results.next()) {
                p.getInventory().addItem(results.getInt("item"), results.getInt("quantity"));
                System.out.println("someone donated");
                Statement st = getConnection().createStatement();
                st.executeUpdate("UPDATE `status` SET `given` = '10' WHERE `id`='" + results.getInt("id") + "';");
                st.close();
            }
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}