package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.teleportposition.position.WarpPointPosition;
import org.to2mbn.basiscommands.util.PluginUtils;
import org.to2mbn.basiscommands.util.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.util.command.CommandArguments;

public class CommandSetWarp implements Command {
    @Override
    public String getName() {
        return "setwarp";
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
        String name = args.nextString();
        WarpPointPosition position = new WarpPointPosition(player.getX(), player.getY(), player.getZ(), player.getLevel().getId(), name);

        BasisCommands.getTeleportPositionsHandler().addWarpPointPosition(position);
        PluginUtils.sendMessage(player, I18n.translate("command.setwarp.set_msg"));
    }
}
