package org.to2mbn.basiscommands.command;

import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.util.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.util.command.CommandArguments;

public interface Command {
    String getName();

    boolean canExecute(CommandSender sender);

    CommandArgumentTemplet<?>[] getArgumentTemplets();

    void execute(CommandSender sender, CommandArguments args);
}
