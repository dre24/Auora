package Auora.util;

import Auora.GameServer;
import Auora.model.World;

import java.sql.*;

//import spawnscape.model.player.Player;

public class MYSQL {

    /**
     * Define MySQL connection info.
     */

    //left my information here so it's easy for new people to understand the spawnscape.mysql stuffs easier :3

    static final String host = "spawnscape614.org";   //website ip or domain
    static final String db = "spawnsca_online";        //website voting databse
    static final String user = "spawnsca_online";         //website voting user
    static final String pass = "wecameasromans";                //website voting user's password
    static final String port = "3306";                   //keep this at 3306 (spawnscape.mysql port)

    //if your server doesn't connect to the webhost and you know it is right your webhost might not allow spawnscape.mysql!

    //private Player player;

    /**
     * The database connection in use
     */
    private static Connection con;
    /**
     * A statement for running queries on
     */
    private static Statement statement;

    static {
        testForDriver();
    }

    /**
     * The last query being executed
     */
    private String lastQuery;

    /**
     * Instantiates a new database connection
     */
    public MYSQL() {
        if (!createConnection()) {
            System.out.println("Unable to connect to MySQL");
            GameServer.voteDisabled = 0;
            System.out.println("Loading the server without MYSQL.");
        }
    }

    /**
     * Tests we have a spawnscape.mysql Driver
     */
    private static void testForDriver() {
        try {
            Class.forName("com.spawnscape.mysql.jdbc.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class not found exception");
        }
    }

    public static ResultSet query(String s) throws SQLException {
        try {
            if (GameServer.voteDisabled == 0) {
                System.out.println("Aborted conncetion.");
                return null;
            }
            if (s.toLowerCase().startsWith("select")) {
                return statement.executeQuery(s);
            } else {
                statement.executeUpdate(s);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            con = null;
            statement = null;
        }
        return null;
    }

    public static void updatePlayers() {
        try {
            if (GameServer.voteDisabled == 0) {
                System.out.println("Aborted conncetion.");
                return;
            }
            String query = "DELETE FROM `online` WHERE id = 1;";
            String query2 = "INSERT INTO `online` (id, currentlyonline) VALUES('1','" + World.getPlayers().size() + "');";
            System.out.println("Players Online Successfully updated on the website to: " + World.getPlayers().size() + "");
            statement.executeUpdate(query);
            statement.executeUpdate(query2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getPlayersOnline() {
        return World.getPlayers().size();
    }

    public boolean createConnection() {
        try {
            if (GameServer.voteDisabled == 0) {
                System.out.println("Aborted conncetion.");
                return true;
            }
            con = DriverManager.getConnection("jdbc:spawnscape.mysql://" + host + ":"
                    + port + "/" + db, user, pass);
            statement = con.createStatement();
            statement.setEscapeProcessing(true);
            System.out.println("Connected to voting databse");
            return isConnected();
        } catch (SQLException e) {
            GameServer.voteDisabled = 0;
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean isConnected() {
        try {
            statement.executeQuery("SELECT CURRENT_DATE");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Runs a select query on the current database connection
     * <p>
     * <p>
     * The query to be ran
     */
    public ResultSet getQuery(String q) throws SQLException {
        try {
            lastQuery = q;
            return statement.executeQuery(q);
        } catch (SQLException e) {
            if (!isConnected() && createConnection()) {
                return getQuery(q);
            }
            throw new SQLException(e.getMessage() + ": '" + lastQuery + "'", e
                    .getSQLState(), e.getErrorCode());
        }
    }

    /**
     * Runs a update/insert/replace query on the current database connection
     * <p>
     * <p>
     * The query to be ran
     *
     * @param q
     * @return
     * @throws java.sql.SQLException
     */
    public int updateQuery(String q) throws SQLException {
        try {
            lastQuery = q;
            return statement.executeUpdate(q);
        } catch (SQLException e) {
            if (!isConnected() && createConnection()) {
                return updateQuery(q);
            }
            throw new SQLException(e.getMessage() + ": '" + lastQuery + "'", e.getSQLState(), e.getErrorCode());
        }
    }

    /**
     * Closes the database conection.
     *
     * @throws SQLException if there was an error when closing the connection
     */
    public void close() throws SQLException {
        con.close();
        con = null;
    }

}