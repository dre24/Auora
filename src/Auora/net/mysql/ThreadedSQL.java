package Auora.net.mysql;

import Auora.model.player.Player;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The main class which is used for all of the central operations
 *
 * @author Nikki
 */
public class ThreadedSQL {

    public static String UpdateQuery = "UPDATE `accounts` SET staffrights = ?, donorrights = ?, json = ?, password = ? WHERE username = ? LIMIT 1;";
    /**
     * The service used to execute queries
     */
    public ExecutorService service;
    /**
     * The SQL Connection Pool
     */
    private ConnectionPool<DatabaseConnection> pool;

    /**
     * Create a new threaded sql instance from the specified configuraton
     *
     * @param configuration The configuration to use
     */
    public ThreadedSQL(DatabaseConfiguration configuration) {
        service = Executors.newCachedThreadPool();
        pool = new ConnectionPool<DatabaseConnection>(configuration, 10);
    }

    /**
     * Create a new threaded sql instance with the specified configuration and
     * number of threads
     *
     * @param configuration The configuration to use
     * @param threads       The max number of threads
     */
    public ThreadedSQL(DatabaseConfiguration configuration, int threads) {
        this(configuration, threads, threads);
    }

    /**
     * Create a new threaded sql instance with the specified configuration,
     * number of threads and number of connections
     *
     * @param configuration The configuration to use
     * @param threads       The max number of threads
     * @param connections   The max number of connections
     */
    public ThreadedSQL(DatabaseConfiguration configuration, int threads, int connections) {
        service = Executors.newFixedThreadPool(threads);
        pool = new ConnectionPool<DatabaseConnection>(configuration, threads);
    }

    private static void executeQuery(final ThreadedSQL sql, final String query, final SQLCallback callback) {
        DatabaseConnection conn = null;
        try {
            conn = sql.pool.nextFree();
            sql.query(query, callback, conn);
        } catch (SQLException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.queryError(e);
            }
        } finally {
            if (conn != null)
                conn.returnConnection();
        }
    }

    /**
     * Executed a PreparedStatement query.
     *
     * @param statement The statement to execute
     * @param callback  The callback to inform when the query is successful/fails
     */
    public void executeQuery(final PreparedStatement statement, final SQLCallback callback) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    query(statement, callback);
                } catch (SQLException e) {
                    if (callback != null) {
                        callback.queryError(e);
                    }
                }
            }
        });
    }

    /**
     * Executed a PreparedStatement query.
     *
     * @param stmt     The statement to execute
     * @param callback The callback to inform when the query is successful/fails
     */
    public void executeLoginQuery(final PreparedStatement stmt, final SQLCallback callback, final Player p) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Execute the PreparedStatement
                    query(stmt, callback);
                } catch (SQLException e) {
                    if (callback != null) {
                        callback.queryError(e);
                    }
                }
            }
        });
    }

    /**
     * Executed a PreparedStatement query.
     *
     * @param callback The callback to inform when the query is successful/fails
     */
    public void executeLogoutQuery(final Player player, final SQLCallback callback) {
        service.execute(new Runnable() {
            @Override
            public void run() {
                //  try {
                int index = 1;
                //final PreparedStatement stmt = GameServer.getCharacterPool().prepareStatement(UpdateQuery);
                //stmt.setInt(index++, player.getRights().ordinal());
                //stmt.setInt(index++, player.getDonorRights());
                //stmt.setString(index++, PlayerSaving.toJson(player));
                //stmt.setString(index++, player.getPassword());

                // Final Username
                //stmt.setString(index++, player.getUsername());

                //query(stmt, callback);
//                } catch (SQLException e) {
//                    if (callback != null) {
//                        callback.queryError(e);
//                    }
//                }
            }
        });
    }

    /**
     * Executed a standard sql query.
     *
     * @param query    The statement to execute
     * @param callback The callback to inform when the query is successful/fails
     */
    public void executeQuery(final String query, final SQLCallback callback) {
        final ThreadedSQL sql = this;
        service.execute(new Runnable() {
            @Override
            public void run() {
                executeQuery(sql, query, callback);
            }
        });
    }

    public void executeBlockingQuery(final String query, final SQLCallback callback) {
        executeQuery(this, query, callback);
    }

    /**
     * Create a PreparedStatement from a random pool connection
     *
     * @param string The statement to prepare
     * @return The initialized PreparedStatement
     * @throws SQLException If an error occurred while preparing
     */
    public PreparedStatement prepareStatement(String string) throws SQLException {
        DatabaseConnection conn = pool.nextFree();
        if (conn == null) {
            System.out.println("Connection null: " + string);
            return null;
        }
        Connection c = conn.getConnection();

        try {
            return c.prepareStatement(string);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.returnConnection();
        }
        return null;
    }

    /**
     * Internal method to handle sql calls for PreparedStatements Note: You HAVE
     *
     * @param statement The statement to execute
     * @param callback  The callback to inform
     * @throws SQLException If an error occurs while executing, this is passed to
     *                      callback.queryError(SQLException e)
     */
    private void query(PreparedStatement statement, SQLCallback callback) throws SQLException {
        statement.execute();

        // Prepared statements don't hold a connection, they simply use it
        ResultSet result = statement.getResultSet();
        try {
            if (callback != null) {
                callback.queryComplete(result);
            }
        } finally {
            // Close the result set
            if (result != null) {
                result.close();
            }
            if (statement != null) {
                statement.close();
            }

        }
    }

    /**
     * Internal method to handle sql calls for standard queries
     *
     * @param callback The callback to inform
     * @throws SQLException If an error occurs while executing, this is passed to
     *                      callback.queryError(SQLException e)
     */
    private void query(String query, SQLCallback callback, DatabaseConnection conn) throws SQLException {
        if (conn == null) {
            System.out.println("Connection null: " + query);
            return;
        }
        Connection c = conn.getConnection();

        Statement statement = c.createStatement();
        statement.execute(query);

        ResultSet result = statement.getResultSet();
        try {
            if (callback != null) {
                callback.queryComplete(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the result set
            if (result != null) {
                result.close();
            }
            if (statement != null) {
                statement.close();
            }
            // Return the used connection
            // conn.returnConnection();
        }
    }

    /**
     * Get the connection pool, for use with standard queries :D
     */
    public ConnectionPool<DatabaseConnection> getConnectionPool() {
        return pool;
    }
}
