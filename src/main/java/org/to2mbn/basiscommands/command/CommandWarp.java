package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.teleportposition.position.WarpPointPosition;
import org.to2mbn.basiscommands.util.PluginUtils;
import org.to2mbn.basiscommands.util.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.util.command.CommandArguments;

public class CommandWarp implements Command {
    @Override
    public String getName() {
        return "warp";
    }

    @Override
    public boolean canExecute(CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public CommandArgumentTemplet<?>[] getArgumentTemplets() {
        return new CommandArgumentTemplet<?>[]{
                new CommandArgumentTemplet<>(String.class, true)
        };
    }

    @Override
    public void execute(CommandSender sender, CommandArguments args) {
        Player player = (Player) sender;
        String warpPointName = args.nextString();

        WarpPointPosition position = BasisCommands.getTeleportPositionsHandler().getWarpPointPosition(warpPointName);
        if (position == null) {
            PluginUtils.sendMessage(player, I18n.translate("command.warp.warp_point_not_exists_msg"));
        } else {
            player.teleportImmediate(position.toPosition());
        }
    }
}
