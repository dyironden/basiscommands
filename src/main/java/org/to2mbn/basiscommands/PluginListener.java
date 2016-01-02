package org.to2mbn.basiscommands;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import org.to2mbn.basiscommands.utils.PluginUtils;

public class PluginListener implements Listener {
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        event.setMessage(PluginUtils.colorize(event.getMessage()));
    }
}
