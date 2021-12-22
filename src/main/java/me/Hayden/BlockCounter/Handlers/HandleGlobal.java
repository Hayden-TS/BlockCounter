package me.Hayden.BlockCounter.Handlers;

import me.Hayden.BlockCounter.Data.Queries;
import me.Hayden.BlockCounter.Main;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

import static me.badbones69.crazyenchantments.Methods.color;

public class HandleGlobal {

    //Main method for handling broken blocks. Decides what to do.
    public static void handle(Player player, List<Block> blocks) {
        Queries q = new Queries();
        Integer blocksBroken = q.getBlocks(player);
        q.addBlocks(player, blocks.size());
        Integer totalBlocks = blocksBroken + blocks.size();
        // Method for handling interval rewards with token enchants block explode feature
        if (blocksBroken - totalBlocks > 1) {
            for (int i = blocksBroken + 1; i < totalBlocks; i++) {
                intervalRewardHandler(player, i);
                normalRewardHandler(player, i);
            }
            return;
        }
        intervalRewardHandler(player, blocksBroken);
        normalRewardHandler(player, blocksBroken);
    }

    //Method for handling interval rewards
    private static void intervalRewardHandler(Player player, Integer blocksBroken) {
        for (String key : Main.plugin.getConfig().getConfigurationSection("interval-rewards").getKeys(false)) {
            Integer blocksNeeded = Main.plugin.getConfig().getInt("interval-rewards." + key + ".every");
            if (blocksBroken % blocksNeeded == 0 && blocksBroken != 0) {
                for (String s : Main.plugin.getConfig().getStringList("interval-rewards." + key + ".commands")) {
                    String[] splits = s.split(" ", 2);
                    String prefix = splits[0];
                    if (prefix.equalsIgnoreCase("[cmd]")) {
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), splits[1]
                                .replace("%player%", player.getName()));
                    }
                    if (prefix.equalsIgnoreCase("[message]")) {
                        player.sendMessage(color(splits[1].replace("%player%", player.getName())));
                    }
                }
            }
        }
    }

    //Method for handling normal rewards for set amounts
    private static void normalRewardHandler(Player player, Integer blocksBroken) {
        for (String key : Main.plugin.getConfig().getConfigurationSection("rewards").getKeys(false)) {
            Integer blocksNeeded = Main.plugin.getConfig().getInt("rewards." + key + ".blocks");
            if (blocksBroken.equals(blocksNeeded)) {
                for (String s : Main.plugin.getConfig().getStringList("rewards." + key + ".commands")) {
                    String[] splits = s.split(" ", 2);
                    String prefix = splits[0];
                    if (prefix.equalsIgnoreCase("[cmd]")) {
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), splits[1]
                                .replace("%player%", player.getName()));
                    }
                    if (prefix.equalsIgnoreCase("[message]")) {
                        player.sendMessage(color(splits[1].replace("%player%", player.getName())));
                    }
                }
            }
        }
    }

}
