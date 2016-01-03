package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.teleportrequest.TeleportRequest;
import org.to2mbn.basiscommands.util.PluginUtils;
import org.to2mbn.basiscommands.util.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.util.command.CommandArguments;

public class CommandTpAccept implements Command {
    @Override
    public String getName() {
        return "tpaccept";
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
        TeleportRequest request = BasisCommands.getTeleportRequestsHandler().pickRequest(player);

        if (request != null) {
            Player from = null, to = null;

            switch (request.getMode()) {
                case TPA:
                    from = request.getRequestPlayer();
                    to = player;
                    PluginUtils.sendMessage(from, I18n.format("command.tpaccept.target_accepted_msg", to.getName()));
                    PluginUtils.sendMessage(to, I18n.format("command.tpaccept.accepted_msg", from.getName()));
                    break;
                case TPAHERE:
                    from = player;
                    to = request.getRequestPlayer();
                    PluginUtils.sendMessage(from, I18n.format("command.tpaccept.target_accepted_msg", to.getName()));
                    PluginUtils.sendMessage(to, I18n.format("command.tpaccept.tpahere_accepted_msg", from.getName()));
                    break;
            }

            from.teleportImmediate(to.getPosition());
        }
    }
}
