package me.Hayden.BlockCounter.Events;

import me.Hayden.BlockCounter.Handlers.HandleGlobal;
import me.clip.autosell.events.DropsToInventoryEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class AutoSell_BlockBreakEvent implements Listener {

    @EventHandler
    public static void event(DropsToInventoryEvent event) {
        Player player = event.getPlayer();
        if (player.getPlayer().getItemInHand().getType().toString().contains("_PICKAXE")) {
            List<Block> block = new ArrayList<Block>();
            block.add(event.getBlock());
            HandleGlobal.handle(player, block);
        }
    }
}
