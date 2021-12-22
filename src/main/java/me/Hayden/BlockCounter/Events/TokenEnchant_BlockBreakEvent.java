package me.Hayden.BlockCounter.Events;

import com.vk2gpz.tokenenchant.event.TEBlockExplodeEvent;
import me.Hayden.BlockCounter.Handlers.HandleGlobal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TokenEnchant_BlockBreakEvent implements Listener {

    @EventHandler
    public static void blockBreak(TEBlockExplodeEvent event) {
        Player player = event.getPlayer();
        if (player.getPlayer().getItemInHand().getType().toString().contains("_PICKAXE")) {
            HandleGlobal.handle(player, event.blockList());
        }
    }
}
