package org.to2mbn.basiscommands.command;

import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.utils.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.utils.command.CommandArguments;

public class CommandDelNotice implements Command {
    @Override
    public String getName() {
        return "delnotice";
    }

    @Override
    public boolean canExecute(CommandSender sender) {
        return true;
    }

    @Override
    public CommandArgumentTemplet<?>[] getArgumentTemplets() {
        return new CommandArgumentTemplet<?>[] {
                new CommandArgumentTemplet<>(int.class, true, id -> id >= 0)
        };
    }

    @Override
    public void execute(CommandSender sender, CommandArguments args) {
        int id = args.nextInteger();
        BasisCommands.getAutoNoticeHandler().removeNotice(id);
        sender.sendMessage(I18n.translate("command.delnotice.deleted_msg"));
    }
}
