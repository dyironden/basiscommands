package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Position;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.teleportposition.position.HomePointPosition;
import org.to2mbn.basiscommands.teleportposition.TeleportPositionsHandler;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.util.PluginUtils;
import org.to2mbn.basiscommands.util.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.util.command.CommandArguments;

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
        TeleportPositionsHandler handler = BasisCommands.getTeleportPositionsHandler();
        HomePointPosition homePosition = handler.getHomePointPositionByName(player, homeName);


        if (homePosition != null) {
            Position position = homePosition.toPosition();

            BasisCommands.logger().info("Teleporting player '" + player.getName() + "' to his home:" + position.getX() + "," + position.getY() + "," + position.getZ());
            PluginUtils.sendMessage(player, I18n.format("command.home.teleport_msg", homePosition.getName()));
            player.teleport(position);
        } else {
            PluginUtils.sendMessage(player, I18n.translate("command.home.home_not_exists_msg"));
        }
    }
}
