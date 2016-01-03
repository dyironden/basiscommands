package org.to2mbn.basiscommands.util;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import com.google.common.collect.Lists;
import org.to2mbn.basiscommands.BasisCommands;

import java.util.List;

public final class PluginUtils {
    private PluginUtils() {
    }

    public static int getServerTick() {
        return Server.getInstance().getTick();
    }

    public static <T> List<T> arrayToList(T[] array) {
        List<T> list = Lists.newArrayList();
        for (T t : array) {
            list.add(t);
        }
        return list;
    }

    public static void sendMessage(CommandSender commandSender, String msg) {
        commandSender.sendMessage(colorize(msg));
    }

    public static void boardcastMessage(String msg) {
        Server.getInstance().getOnlinePlayers().values().forEach(player -> player.sendMessage(msg));
        BasisCommands.logger().info(msg);
    }

    public static String colorize(String message) {
        if (message == null) {
            return "";
        }
        char[] msg = message.toCharArray();
        boolean prefix = false;
        for (int i = 0; i < msg.length; i++) {
            if (msg[i] == '&' && i < msg.length - 1) {
                prefix = true;
                continue;
            }
            if (prefix) {
                if ("0123456789AaBbCcDdEeFfKkLlMmNnOoRr".contains(msg[i] + "")) {
                    msg[i - 1] = '§';
                }
                prefix = false;
            }
        }
        return new String(msg);
    }
}
