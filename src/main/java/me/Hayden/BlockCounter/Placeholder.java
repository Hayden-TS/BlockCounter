package me.Hayden.BlockCounter;

import me.Hayden.BlockCounter.Data.Queries;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


public class Placeholder extends PlaceholderExpansion {

    private final Main plugin;

    public Placeholder(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier() {
        return "blockcounter";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        OfflinePlayer offlineplayer = Bukkit.getOfflinePlayer(player.getUniqueId());
        if (player == null) {
            return "";
        }

        // %someplugin_placeholder1%
        if (identifier.equals("broken")) {
            return String.valueOf(new Queries().getBlocks(player));
        }

        if (identifier.equals("nextreward")) {
            Integer nextReward = Utils.getNextReward(player);
            if (nextReward == 0) {
                return Utils.color(Main.plugin.getConfig().getString("settings.noreward_placeholder"));
            }
            return String.valueOf(nextReward);
        }

        if (identifier.equals("nextglobalreward")) {
            Integer nextReward = Utils.getNextGlobalReward(player);
            if (nextReward == 0) {
                return Utils.color(Main.plugin.getConfig().getString("settings.noreward_placeholder"));
            }
            return String.valueOf(nextReward);
        }

        if (identifier.equals("nextintervalreward")) {
            Integer nextReward = Utils.getNextIntervalReward(player);
            if (nextReward == 0) {
                return Utils.color(Main.plugin.getConfig().getString("settings.noreward_placeholder"));
            }
            return String.valueOf(nextReward);
        }

        return null;
    }
}