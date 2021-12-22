package me.Hayden.BlockCounter;

import me.Hayden.BlockCounter.Data.Queries;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

import static me.badbones69.crazyenchantments.Methods.color;

public class Utils {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static int getNextIntervalReward(Player player) {
        Queries q = new Queries();
        HashMap<String, Integer> rewards = new HashMap<String, Integer>();
        Integer blocksBroken = q.getBlocks(player);

        for (String key : Main.plugin.getConfig().getConfigurationSection("interval-rewards").getKeys(false)) {
            Integer blocksNeeded = Main.plugin.getConfig().getInt("interval-rewards." + key + ".every");
            rewards.put("interval-rewards." + key, blocksBroken + (blocksNeeded - blocksBroken % blocksNeeded));
        }

        for (Map.Entry<String, Integer> entry : sortByValue(rewards).entrySet()) {
            Integer blocksNeeded = entry.getValue();
            if (blocksNeeded > blocksBroken) {
                return blocksNeeded;
            }

        }
        return 0;

    }

    public static int getNextGlobalReward(Player player) {
        Queries q = new Queries();
        HashMap<String, Integer> rewards = new HashMap<String, Integer>();
        Integer blocksBroken = q.getBlocks(player);
        for (String key : Main.plugin.getConfig().getConfigurationSection("rewards").getKeys(false)) {
            Integer blocksNeeded = Main.plugin.getConfig().getInt("rewards." + key + ".blocks");
            rewards.put("rewards." + key, blocksNeeded);
        }


        for (Map.Entry<String, Integer> entry : sortByValue(rewards).entrySet()) {
            Integer blocksNeeded = entry.getValue();
            if (blocksNeeded > blocksBroken) {
                return blocksNeeded;
            }

        }
        return 0;

    }

    public static int getNextReward(Player player) {
        Queries q = new Queries();
        HashMap<String, Integer> rewards = new HashMap<String, Integer>();
        Integer blocksBroken = q.getBlocks(player);
        for (String key : Main.plugin.getConfig().getConfigurationSection("rewards").getKeys(false)) {
            Integer blocksNeeded = Main.plugin.getConfig().getInt("rewards." + key + ".blocks");
            rewards.put("rewards." + key, blocksNeeded);
        }
        for (String key : Main.plugin.getConfig().getConfigurationSection("interval-rewards").getKeys(false)) {
            Integer blocksNeeded = Main.plugin.getConfig().getInt("interval-rewards." + key + ".every");
            rewards.put("interval-rewards." + key, blocksBroken + (blocksNeeded - blocksBroken % blocksNeeded));
        }
        for (Map.Entry<String, Integer> entry : sortByValue(rewards).entrySet()) {
            Integer blocksNeeded = entry.getValue();
            if (blocksNeeded > blocksBroken) {
                return blocksNeeded;
            }

        }
        return 0;

    }

    private static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}
