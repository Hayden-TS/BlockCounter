package me.Hayden.BlockCounter;

import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import me.Hayden.BlockCounter.Data.SQLite;
import me.Hayden.BlockCounter.Events.AutoSell_BlockBreakEvent;
import me.Hayden.BlockCounter.Events.TokenEnchant_BlockBreakEvent;
import me.Hayden.BlockCounter.Events.vanilla.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main plugin;
    public static boolean autosell_hook;
    public static boolean tokenenchant_hook;
    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        reloadConfig();
        new SQLite().startup();
        //Registering Placeholder with PAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholder(this).register();
        }

        int pluginId = 13666; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        //Setup ACF and register commands
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.setFormat(MessageType.ERROR, ChatColor.RED, ChatColor.RED);
        manager.setFormat(MessageType.HELP, ChatColor.RED, ChatColor.RED);
        manager.setFormat(MessageType.INFO, ChatColor.RED, ChatColor.RED);
        manager.setFormat(MessageType.SYNTAX, ChatColor.RED, ChatColor.RED);
        manager.registerCommand(new Command());

        //Register the correct event for BlockBreak
        if (Bukkit.getPluginManager().getPlugin("TokenEnchant") != null && plugin.getConfig().getBoolean("settings.hooks.tokenenchant") == true) {
            Bukkit.getPluginManager().registerEvents(new TokenEnchant_BlockBreakEvent(), this);
            tokenenchant_hook = true;
        }
        if (Bukkit.getPluginManager().getPlugin("AutoSell") != null && plugin.getConfig().getBoolean("settings.hooks.autosell") == true) {
            Bukkit.getPluginManager().registerEvents(new AutoSell_BlockBreakEvent(), this);
            autosell_hook = true;
        } else {
            String priority = Main.plugin.getConfig().getString("settings.event_priority");
            if (priority.equals("HIGH")) {
                Bukkit.getPluginManager().registerEvents(new BlockBreakEvent_HIGH(), this);
                System.out.println("BlockCounter block break priority set to " + priority);
            }
            if (priority.equals("HIGHEST")) {
                Bukkit.getPluginManager().registerEvents(new BlockBreakEvent_HIGHEST(), this);
                System.out.println("BlockCounter block break priority set to " + priority);
            }
            if (priority.equals("NORMAL")) {
                Bukkit.getPluginManager().registerEvents(new BlockBreakEvent_NORMAL(), this);
                System.out.println("BlockCounter block break priority set to " + priority);
            }
            if (priority.equals("LOW")) {
                Bukkit.getPluginManager().registerEvents(new BlockBreakEvent_LOW(), this);
                System.out.println("BlockCounter block break priority set to " + priority);
            }
            if (priority.equals("LOWEST")) {
                Bukkit.getPluginManager().registerEvents(new BlockBreakEvent_LOWEST(), this);
                System.out.println("BlockCounter block break priority set to " + priority);
            }
            if (priority.equals("MONITOR")) {
                Bukkit.getPluginManager().registerEvents(new BlockBreakEvent_MONITOR(), this);
                System.out.println("BlockCounter block break priority set to " + priority);
            }

        }
    }


}
