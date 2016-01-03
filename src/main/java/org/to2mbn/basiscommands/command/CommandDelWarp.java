package org.to2mbn.basiscommands.command;

import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.util.PluginUtils;
import org.to2mbn.basiscommands.util.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.util.command.CommandArguments;

public class CommandDelWarp implements Command {
    @Override
    public String getName() {
        return "delwarp";
    }

    @Override
    public boolean canExecute(CommandSender sender) {
        return true;
    }

    @Override
    public CommandArgumentTemplet<?>[] getArgumentTemplets() {
        return new CommandArgumentTemplet<?>[]{
                new CommandArgumentTemplet<>(int.class, true)
        };
    }

    @Override
    public void execute(CommandSender sender, CommandArguments args) {
        int id = args.nextInteger();

        BasisCommands.getTeleportPositionsHandler().removeWarpPointPosition(id);
        PluginUtils.sendMessage(sender, I18n.translate("command.delwarp.warp_deleted"));
    }
}
