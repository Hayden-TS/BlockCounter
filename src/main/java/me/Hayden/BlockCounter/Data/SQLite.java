package me.Hayden.BlockCounter.Data;

import me.Hayden.BlockCounter.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite {

    public static Connection connection;
    public static final String tableName = "blockcounter";
    public static final String table = "CREATE TABLE IF NOT EXISTS" + " " + tableName + " "
            + "(UUID VARCHAR(100),BLOCKS INT NOT NULL, PRIMARY KEY (UUID))";

    public static final String sqliteUrl = "jdbc:sqlite:" + Main.plugin.getDataFolder() + "/database.db";


    public static void getConnection() {
        // SQLite connection
        String url = sqliteUrl;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        connection = conn;
    }

    public void startup() {
        connect();
        createNewTable();
        getConnection();
    }


    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Error with JDBC contact BlockCounter plugin developer");
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(sqliteUrl);
            ConsoleCommandSender console = Main.plugin.getServer().getConsoleSender();
            console.sendMessage(ChatColor.GREEN + "SQLite connected successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void createNewTable() {
        String sql = table;

        try (Connection conn = DriverManager.getConnection(sqliteUrl);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



}
