package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.teleportrequest.RequestMode;
import org.to2mbn.basiscommands.teleportrequest.TeleportRequest;
import org.to2mbn.basiscommands.util.PluginUtils;
import org.to2mbn.basiscommands.util.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.util.command.CommandArguments;

public class CommandTpaHere implements Command {
    @Override
    public String getName() {
        return "tpahere";
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
            PluginUtils.sendMessage(requestPlayer, I18n.translate("command.tpa.player_not_online_msg"));
        } else {
            BasisCommands.getTeleportRequestsHandler().addRequest(new TeleportRequest(requestPlayer, targetPlayer, PluginUtils.getServerTick(), RequestMode.TPAHERE));
            PluginUtils.sendMessage(requestPlayer, I18n.format("command.tpa.request_sent_msg", targetPlayer.getName()));
            PluginUtils.sendMessage(targetPlayer, I18n.format("command.tpa.tpahere_request_received_msg", requestPlayer.getName()));
        }
    }
}
