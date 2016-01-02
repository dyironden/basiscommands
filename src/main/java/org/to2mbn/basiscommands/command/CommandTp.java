package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.utils.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.utils.command.CommandArguments;

public class CommandTp implements Command {
    @Override
    public String getName() {
        return "tp";
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
        Player target = Server.getInstance().getPlayer(args.nextString());

        if (target == null) {
            player.sendMessage("This player is not online");
            return;
        }

        player.teleportImmediate(target.getPosition());

        BasisCommands.logger().info("Teleported player " + player.getName() + " to " + target.getName());
        player.sendMessage("Teleported to " + target.getName());
        if (BasisCommands.getConfiguration().getBoolean("player.notice_when_tp")) {
            target.sendMessage("Player " + player.getName() + " teleported to you");
        }
    }
}
