package me.Hayden.BlockCounter.Data;

import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Queries {

    final Connection connection = SQLite.connection;
    final String table = SQLite.tableName;

    public int getBlocks(Player player) {
        checkPlayer(player);
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE UUID=? COLLATE NOCASE")) {
            ps.setString(1, player.getUniqueId().toString());
            ResultSet results = ps.executeQuery();
            results.next();
            return results.getInt("BLOCKS");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public void addBlocks(Player player, int amount) {
        checkPlayer(player);
        try (PreparedStatement ps = connection.prepareStatement("UPDATE " + table + " SET 'BLOCKS'=? WHERE UUID=? COLLATE NOCASE")) {
            ps.setLong(1, getBlocks(player) + amount);
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeBlocks(Player player, int amount) {
        checkPlayer(player);
        try (PreparedStatement ps = connection.prepareStatement("UPDATE " + table + " SET 'BLOCKS'=? WHERE UUID=? COLLATE NOCASE")) {
            ps.setLong(1, getBlocks(player) - amount);
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setBlocks(Player player, int amount) {
        checkPlayer(player);
        try (PreparedStatement ps = connection.prepareStatement("UPDATE " + table + " SET 'BLOCKS'=? WHERE UUID=? COLLATE NOCASE")) {
            ps.setLong(1, amount);
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void checkPlayer(Player player) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE UUID=? COLLATE NOCASE")) {
            ps.setString(1, player.getUniqueId().toString());
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Player has no data. Create data

        try (PreparedStatement create = connection.prepareStatement("INSERT INTO " + table + " (UUID, BLOCKS)" + "VALUES (?,?)")) {
            create.setString(1, player.getUniqueId().toString());
            create.setInt(2, 0);
            create.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


}
