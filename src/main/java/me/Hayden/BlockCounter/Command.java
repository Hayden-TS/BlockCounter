package me.Hayden.BlockCounter;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.Hayden.BlockCounter.Data.Queries;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@CommandAlias("blocks")
public class Command extends BaseCommand {

    @Default
    @Description("Blocks broken counter")
    @CommandPermission("blockcounter.check")
    public static void blocksBroken(Player player, String[] args) {
        Queries q = new Queries();
        Integer blocksBroken = q.getBlocks(player);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.plugin.getConfig().getString("settings.message").replace("%blocks%", String.valueOf(blocksBroken))));
    }

    @Subcommand("reset")
    @CommandPermission("blockcounter.admin")
    public static void reset(Player player, OfflinePlayer target) {
        Queries q = new Queries();
        q.setBlocks(target.getPlayer(), 0);
        player.sendMessage(Utils.color("&c&lADMIN &7- You have reset block data for player " + target.getName()));

    }

}
