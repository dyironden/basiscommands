package org.to2mbn.basiscommands.utils.command;

import cn.nukkit.command.CommandSender;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.command.Command;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class CommandHandler {
    private final Set<Command> commands;

    public CommandHandler() {
        commands = Sets.newHashSet();
    }

    public void registerCommand(Command command) {
        commands.add(command);
    }

    public Command getCommandByName(String name) {
        for (Iterator<Command> iterator = commands.iterator(); iterator.hasNext(); ) {
            Command command = iterator.next();
            if (command.getName().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return null;
    }

    public boolean pushCommand(CommandSender sender, String label, String[] args) {
        Command command = getCommandByName(label);
        CommandArgumentTemplet[] templets = command.getArgumentTemplets();
        if (command != null) {
            CommandArgumentTemplet templet = checkInvalidArgument(templets, args);
            if (templet != null) {
                sender.sendMessage(templet.getInfo());
                return false;
            }

            if (command.canExecute(sender)) {
                command.execute(sender, toCommandArguments(templets, args));
            } else {
                sender.sendMessage("You are not allowed to execute this command");
            }
            return true;
        }
        return false;
    }

    private CommandArguments toCommandArguments(CommandArgumentTemplet[] templets, String[] args) {
        List<Object> list = Lists.newArrayList();

        for (int i = 0; i < templets.length; i++) {
            String arg = args[i];
            CommandArgumentTemplet templet = templets[i];

            switch (templet.getType().getSimpleName().toLowerCase()) {
                case "int":
                case "integer":
                    list.add(Integer.parseInt(arg));
                    break;
                case "double":
                    list.add(Double.parseDouble(arg));
                    break;
                case "boolean":
                    list.add(Boolean.parseBoolean(arg));
                    break;
                case "string":
                    list.add(arg);
                    break;
            }
        }

        return new CommandArguments(list.iterator());
    }

    private CommandArgumentTemplet checkInvalidArgument(CommandArgumentTemplet[] templets, String[] args) {
        for (int i = 0; i < templets.length; i++) {
            CommandArgumentTemplet templet = templets[i];
            if (i >= args.length) {
                if (templet.isRequired()) {
                    return templet;
                } else {
                    continue;
                }
            }
            String arg = args[i];
            Function<Object, Boolean> isValidFunc = templet.getIsValid();

            switch (templet.getType().getSimpleName().toLowerCase()) {
                case "int":
                case "integer":
                    if (!canConvertIntoInteger(arg) || !isValidFunc.apply(Integer.parseInt(arg))) {
                        BasisCommands.logger().info(isValidFunc.apply(Integer.parseInt(arg)) + "");
                        return templet;
                    }
                    break;
                case "double":
                    if (!canConvertIntoDouble(arg) || !isValidFunc.apply(Double.parseDouble(arg))) {
                        return templet;
                    }
                    break;
                case "boolean":
                    if (!arg.equalsIgnoreCase("true") && !arg.equalsIgnoreCase("false")) {
                        return templet;
                    }
                    break;
                case "string":
                    if (!isValidFunc.apply(arg)) {
                        return templet;
                    }
                    break;
                default:
                    return templet;
            }
        }
        return null;
    }

    private boolean canConvertIntoInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean canConvertIntoDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
