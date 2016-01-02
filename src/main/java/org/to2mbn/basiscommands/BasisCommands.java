package org.to2mbn.basiscommands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Logger;
import cn.nukkit.utils.Utils;
import org.to2mbn.basiscommands.autonotice.AutoNoticeHandler;
import org.to2mbn.basiscommands.command.*;
import org.to2mbn.basiscommands.configuration.Configuration;
import org.to2mbn.basiscommands.homeposition.HomePositionsHandler;
import org.to2mbn.basiscommands.teleportrequest.TeleportRequestsHandler;
import org.to2mbn.basiscommands.utils.command.CommandHandler;

import java.io.File;

public class BasisCommands extends PluginBase {
    public static final String VERSION = "0.0.1";

    private static Logger logger;
    private static File pluginDataDir;
    private static Configuration configuration;
    private static PluginListener pluginListener;
    private static HomePositionsHandler homePositionsHandler;
    private static CommandHandler commandHandler;
    private static AutoNoticeHandler autoNoticeHandler;
    private static TeleportRequestsHandler teleportRequestsHandler;

    @Override
    public void onEnable() {
        try {
            init();
        } catch (Throwable t) {
            throw new RuntimeException("Failed to enable the plugin", t);
        }
    }

    @Override
    public void onDisable() {
        try {
            shutdown();
        } catch (Throwable t) {
            throw new RuntimeException("Error while shutting down the plugin", t);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandHandler.pushCommand(sender, label, args);
    }

    private void init() throws Throwable {
        logger = getLogger();
        pluginDataDir = getDataFolder();
        pluginDataDir.mkdirs();

        logger.info("Initializing plugin listener");
        pluginListener = new PluginListener();
        getServer().getPluginManager().registerEvents(pluginListener, this);

        logger.info("Loading configurations' handler");
        File configFile = new File(pluginDataDir, "config.yml");
        if (!configFile.exists()) {
            logger.info("Missing configuration file");
            Utils.writeFile(configFile, getResource("config.yml"));
        }
        configuration = new Configuration(new File(pluginDataDir, "config.yml"));

        logger.info("Loading auto notices");
        File noticeFile = new File(pluginDataDir, "notices.txt");
        if (!noticeFile.exists()) {
            Utils.writeFile(noticeFile, getResource("notices.txt"));
        }
        autoNoticeHandler = new AutoNoticeHandler(this, noticeFile, configuration.getInteger("auto_notices.delay"));
        getServer().getScheduler().scheduleRepeatingTask(autoNoticeHandler, 20 * 1); //per second

        logger.info("Loading players' home positions");
        homePositionsHandler = new HomePositionsHandler(new File(pluginDataDir, "home_positions.obj"));

        logger.info("Loading teleport requests' handler");
        teleportRequestsHandler = new TeleportRequestsHandler(this, configuration.getInteger("tpa.request_max_wait_time"));
        getServer().getScheduler().scheduleRepeatingTask(teleportRequestsHandler, 1); // per 1/20 second

        logger.info("Loading commands' handler");
        commandHandler = new CommandHandler();
        commandHandler.registerCommand(new CommandHome());
        commandHandler.registerCommand(new CommandHomeList());
        commandHandler.registerCommand(new CommandSetHome());
        commandHandler.registerCommand(new CommandDelHome());
        commandHandler.registerCommand(new CommandTp());
        commandHandler.registerCommand(new CommandTpa());
        commandHandler.registerCommand(new CommandTpAccept());
        commandHandler.registerCommand(new CommandAddNotice());
        commandHandler.registerCommand(new CommandDelNotice());
        commandHandler.registerCommand(new CommandNoticeList());
    }

    private void shutdown() throws Throwable {
        homePositionsHandler.saveData();
        autoNoticeHandler.saveNotices();
        configuration.save();
    }

    public static Logger logger() {
        return logger;
    }

    public static File getPluginDataDir() {
        return pluginDataDir;
    }

    public static HomePositionsHandler getHomePositionsHandler() {
        return homePositionsHandler;
    }

    public static PluginListener getPluginListener() {
        return pluginListener;
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public static AutoNoticeHandler getAutoNoticeHandler() {
        return autoNoticeHandler;
    }
    public static TeleportRequestsHandler getTeleportRequestsHandler() {
        return teleportRequestsHandler;
    }
}
