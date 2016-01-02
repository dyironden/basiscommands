package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.teleportrequest.TeleportRequest;
import org.to2mbn.basiscommands.utils.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.utils.command.CommandArguments;
import org.to2mbn.basiscommands.utils.PluginUtils;

public class CommandTpa implements Command {
    @Override
    public String getName() {
        return "tpa";
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
        Player requestPlayer = (Player) sender;
        Player targetPlayer = Server.getInstance().getPlayerExact(args.nextString());

        if (targetPlayer == null) {
            requestPlayer.sendMessage("This player is not online");
        } else {
            BasisCommands.getTeleportRequestsHandler().addRequest(new TeleportRequest(requestPlayer, targetPlayer, PluginUtils.getServerTick()));
            requestPlayer.sendMessage("Sent a tpa request to " + targetPlayer.getName());
            targetPlayer.sendMessage(targetPlayer.getName() + " wants to teleport to you, type /tpaccept to accept");
        }
    }
}
