package org.to2mbn.basiscommands.command;

import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.util.PluginUtils;
import org.to2mbn.basiscommands.util.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.util.command.CommandArguments;

public class CommandAddNotice implements Command {
    @Override
    public String getName() {
        return "addnotice";
    }

    @Override
    public boolean canExecute(CommandSender sender) {
        return true;
    }

    @Override
    public CommandArgumentTemplet<?>[] getArgumentTemplets() {
        return new CommandArgumentTemplet<?>[] {
                new CommandArgumentTemplet<>(String.class, true)
        };
    }

    @Override
    public void execute(CommandSender sender, CommandArguments args) {
        String notice = args.nextString();

        BasisCommands.getAutoNoticeHandler().addNotice(notice);
        PluginUtils.sendMessage(sender, I18n.translate("command.add_notice.added_msg"));
    }
}
