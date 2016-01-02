package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.utils.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.utils.command.CommandArguments;

public class CommandDelHome implements Command {
    @Override
    public String getName() {
        return "delhome";
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

        BasisCommands.getHomePositionsHandler().removeHomePosition(player, homeName);
        player.sendMessage(I18n.translate("command.delhome.deleted_msg"));
    }
}
