package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Position;
import org.to2mbn.basiscommands.util.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.util.command.CommandArguments;

public class CommandTpAll implements Command {
    @Override
    public String getName() {
        return "tpall";
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
        Player target = (Player) sender;
        Position targetPosition = target.getPosition();

        Server.getInstance().getOnlinePlayers().values()
                .stream()
                .filter(player -> !player.equals(target))
                .forEach(player -> player.teleportImmediate(targetPosition));
    }
}
