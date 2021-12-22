package me.Hayden.BlockCounter.Events.vanilla;

import me.Hayden.BlockCounter.Handlers.HandleGlobal;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class BlockBreakEvent_LOWEST implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public static void blockbreak(org.bukkit.event.block.BlockBreakEvent event) {
        if (event.isCancelled() == true) {
            return;
        }
        Player player = event.getPlayer();
        if (player.getPlayer().getItemInHand().getType().toString().contains("_PICKAXE")) {
            List<Block> block = new ArrayList<Block>();
            block.add(event.getBlock());
            HandleGlobal.handle(player, block);
        }
    }
}