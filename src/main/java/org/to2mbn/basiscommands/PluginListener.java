package org.to2mbn.basiscommands;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityTeleportEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import org.to2mbn.basiscommands.teleportposition.position.TeleportPointPosition;
import org.to2mbn.basiscommands.util.PluginUtils;

public class PluginListener implements Listener {
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        event.setMessage(PluginUtils.colorize(event.getMessage()));
    }

    @EventHandler
    public void onPlayerDead(PlayerDeathEvent event) {
        BasisCommands.logger().info(event.getEntity().getPosition().toString());
        updatePlayerBackPosition(event.getEntity());
    }

    @EventHandler
    public void onPlayerTeleported(EntityTeleportEvent event) {
        if (event.getEntity() instanceof Player) {
            updatePlayerBackPosition((Player) event.getEntity());
        }
    }

    private void updatePlayerBackPosition(Player player) {
        BasisCommands.getTeleportPositionsHandler().updatePlayerBackPointPosition(player,
                TeleportPointPosition.fromPosition(player.getPosition()));
    }
}
