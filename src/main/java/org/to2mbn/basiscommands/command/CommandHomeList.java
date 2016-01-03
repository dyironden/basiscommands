package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.teleportposition.position.HomePointPosition;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.util.PluginUtils;
import org.to2mbn.basiscommands.util.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.util.command.CommandArguments;

public class CommandHomeList implements Command {
    @Override
    public String getName() {
        return "homelist";
    }

    @Override
    public boolean canExecute(CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public CommandArgumentTemplet<?>[] getArgumentTemplets() {
        return new CommandArgumentTemplet<?>[0];
    }

    @Override
    public void execute(CommandSender sender, CommandArguments args) {
        Player player = (Player) sender;

        PluginUtils.sendMessage(player, I18n.translate("command.homelist.homes"));
        BasisCommands.getTeleportPositionsHandler().getPlayerHomePositions(player)
                .forEach(position -> PluginUtils.sendMessage(player, toDisplayString(position)));
    }

    private String toDisplayString(HomePointPosition position) {
        return TextFormat.GOLD + position.getName() + TextFormat.WHITE + " - " +
                String.format("%s(%s,%s,%s)", Server.getInstance().getLevel(position.getLevel()).getName(), (int) position.getX(), (int) position.getY(), (int) position.getZ());
    }
}
