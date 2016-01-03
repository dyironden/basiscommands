package org.to2mbn.basiscommands.command;

import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.teleportposition.position.WarpPointPosition;
import org.to2mbn.basiscommands.util.PluginUtils;
import org.to2mbn.basiscommands.util.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.util.command.CommandArguments;

import java.util.Iterator;

public class CommandWarpList implements Command {
    @Override
    public String getName() {
        return "warplist";
    }

    @Override
    public boolean canExecute(CommandSender sender) {
        return true;
    }

    @Override
    public CommandArgumentTemplet<?>[] getArgumentTemplets() {
        return new CommandArgumentTemplet<?>[0];
    }

    @Override
    public void execute(CommandSender sender, CommandArguments args) {
        PluginUtils.sendMessage(sender, I18n.translate("command.warplist.warp_points"));
        PluginUtils.sendMessage(sender, I18n.translate("command.warplist.warp_points_header"));
        Iterator<WarpPointPosition> iterator = BasisCommands.getTeleportPositionsHandler().getWarpPositions().iterator();

        int id = 0;
        while (iterator.hasNext()) {
            PluginUtils.sendMessage(sender, (id++) + "  " + iterator.next().getName());
        }
    }
}
