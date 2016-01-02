package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Position;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.homeposition.HomePosition;
import org.to2mbn.basiscommands.homeposition.HomePositionsHandler;
import org.to2mbn.basiscommands.utils.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.utils.command.CommandArguments;

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
        HomePositionsHandler handler = BasisCommands.getHomePositionsHandler();
        int max = BasisCommands.getConfiguration().getInteger("sethome.max_home_count");

        if (handler.getHomeCount(player) > max) {
            player.sendMessage("You can only set " + max + " home(s)");
            return;
        }

        handler.addHomePosition(player, new HomePosition(player.getName(), homeName, position.getX(), position.getY(), position.getZ(), position.getLevel().getId()));
        player.sendMessage("Set home '" + homeName + "' here");
        BasisCommands.logger().info("Player " + player.getName() + " set a home at level " + position.getLevel().getName() + ":" + position.getX() + "," + position.getY() + "," + position.getZ());
    }
}
