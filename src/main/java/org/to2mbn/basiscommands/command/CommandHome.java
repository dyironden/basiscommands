package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Position;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.homeposition.HomePosition;
import org.to2mbn.basiscommands.homeposition.HomePositionsHandler;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.utils.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.utils.command.CommandArguments;

public class CommandHome implements Command {
    @Override
    public String getName() {
        return "home";
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
        String homeName = args.nextString();
        HomePositionsHandler handler = BasisCommands.getHomePositionsHandler();
        HomePosition homePosition = handler.getHomePositionByName(player, homeName);


        if (homePosition != null) {
            Position position = homePosition.toPosition();

            BasisCommands.logger().info("Teleporting player '" + player.getName() + "' to his home:" + position.getX() + "," + position.getY() + "," + position.getZ());
            player.sendMessage(I18n.format("command.home.teleport_msg", homePosition.getName()));
            player.teleport(position);
        } else {
            player.sendMessage(I18n.translate("command.home.home_not_exists_msg"));
        }
    }
}
