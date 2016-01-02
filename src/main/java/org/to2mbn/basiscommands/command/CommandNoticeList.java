package org.to2mbn.basiscommands.command;

import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.utils.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.utils.command.CommandArguments;

import java.util.Iterator;

public class CommandNoticeList implements Command {
    @Override
    public String getName() {
        return "noticelist";
    }

    @Override
    public boolean canExecute(CommandSender sender) {
        return true;
    }

    @Override
    public CommandArgumentTemplet<?>[] getArgumentTemplets() {
        return new CommandArgumentTemplet<?>[0];
    }

    @Override
    public void execute(CommandSender sender, CommandArguments args) {
        sender.sendMessage("Notice List:");
        sender.sendMessage("ID  TEXT");
        Iterator<String> iterator = BasisCommands.getAutoNoticeHandler().getNoticeList().iterator();
        int id = 0;
        while (iterator.hasNext()) {
            sender.sendMessage((id++) + "  " + iterator.next());
        }
    }
}
