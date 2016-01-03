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

public class CommandSetHome implements Command {
    @Override
    public String getName() {
        return "sethome";
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
        Position position = player.getPosition();
        TeleportPositionsHandler handler = BasisCommands.getTeleportPositionsHandler();
        int max = BasisCommands.getConfiguration().getInteger("sethome.max_home_count");

        if (handler.getHomePointCount(player) > max) {
            PluginUtils.sendMessage(player, I18n.translate("command.sethome.too_many_homes_error"));
            return;
        }

        handler.addHomePointPosition(player, new HomePointPosition(player.getName(), homeName, position.getX(), position.getY(), position.getZ(), position.getLevel().getId()));
        PluginUtils.sendMessage(sender, I18n.format("command.sethome.set_msg", homeName));
        BasisCommands.logger().info("Player " + player.getName() + " set a home at level " + position.getLevel().getName() + ":" + position.getX() + "," + position.getY() + "," + position.getZ());
    }
}
