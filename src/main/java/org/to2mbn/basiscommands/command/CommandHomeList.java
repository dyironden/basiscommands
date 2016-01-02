package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.homeposition.HomePosition;
import org.to2mbn.basiscommands.utils.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.utils.command.CommandArguments;

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

        player.sendMessage(TextFormat.WHITE + "Your Home(s):");
        BasisCommands.getHomePositionsHandler().getPlayerHomePositions(player)
                .forEach(position -> player.sendMessage(toDisplayString(position)));
    }

    private String toDisplayString(HomePosition position) {
        return TextFormat.GOLD + position.getName() + TextFormat.WHITE + " - " +
                String.format("%s(%s,%s,%s)", Server.getInstance().getLevel(position.getLevel()).getName(), (int) position.getX(), (int) position.getY(), (int) position.getZ());
    }
}
