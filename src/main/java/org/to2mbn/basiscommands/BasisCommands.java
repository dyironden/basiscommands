package org.to2mbn.basiscommands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Logger;
import cn.nukkit.utils.Utils;
import org.to2mbn.basiscommands.autonotice.AutoNoticeHandler;
import org.to2mbn.basiscommands.command.*;
import org.to2mbn.basiscommands.configuration.Configuration;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.teleportposition.TeleportPositionsHandler;
import org.to2mbn.basiscommands.teleportrequest.TeleportRequestsHandler;
import org.to2mbn.basiscommands.util.command.CommandHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class BasisCommands extends PluginBase {
    public static final String VERSION = "@VERSION@";

    private static Logger logger;

    private static File pluginDataDir;

    private static Configuration configuration;

    private static PluginListener pluginListener;

    private static TeleportPositionsHandler teleportPositionsHandler;

    private static CommandHandler commandHandler;

    private static AutoNoticeHandler autoNoticeHandler;

    private static TeleportRequestsHandler teleportRequestsHandler;

    public static Logger logger() {
        return logger;
    }

    public static File getPluginDataDir() {
        return pluginDataDir;
    }

    public static TeleportPositionsHandler getTeleportPositionsHandler() {
        return teleportPositionsHandler;
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

        logger.info("Loading I18N");
        Locale locale = Locale.forLanguageTag(configuration.getString("core.language"));
        if (I18n.DEFAULT_LOCALE.equals(locale)) {
            load18N(locale);
        } else {
            load18N(I18n.DEFAULT_LOCALE);
            load18N(locale);
        }
        logger.info(I18n.translate("lang.loaded_msg"));

        logger.info("Loading auto notices");
        File noticeFile = new File(pluginDataDir, "notices.json");
        if (!noticeFile.exists()) {
            Utils.writeFile(noticeFile, getResource("notices.json"));
        }
        autoNoticeHandler = new AutoNoticeHandler(this, noticeFile, configuration.getInteger("auto_notices.delay"));
        getServer().getScheduler().scheduleRepeatingTask(autoNoticeHandler, 20 * 1); //per second

        logger.info("Loading players' home positions");
        teleportPositionsHandler = new TeleportPositionsHandler(new File(pluginDataDir, "teleport_positions.json"));

        logger.info("Loading teleport requests' handler");
        teleportRequestsHandler = new TeleportRequestsHandler(this, configuration.getInteger("tpa.request_max_wait_time"));
        getServer().getScheduler().scheduleRepeatingTask(teleportRequestsHandler, 1); // per 1/20 second

        logger.info("Loading commands");
        commandHandler = new CommandHandler();
        commandHandler.registerCommand(new CommandHome());
        commandHandler.registerCommand(new CommandHomeList());
        commandHandler.registerCommand(new CommandSetHome());
        commandHandler.registerCommand(new CommandDelHome());
        commandHandler.registerCommand(new CommandTp());
        commandHandler.registerCommand(new CommandTpa());
        commandHandler.registerCommand(new CommandTpaHere());
        commandHandler.registerCommand(new CommandTpAccept());
        commandHandler.registerCommand(new CommandTpAll());
        commandHandler.registerCommand(new CommandAddNotice());
        commandHandler.registerCommand(new CommandDelNotice());
        commandHandler.registerCommand(new CommandNoticeList());
        commandHandler.registerCommand(new CommandWarp());
        commandHandler.registerCommand(new CommandSetWarp());
        commandHandler.registerCommand(new CommandDelWarp());
        commandHandler.registerCommand(new CommandWarpList());
        commandHandler.registerCommand(new CommandBack());
        commandHandler.registerCommand(new CommandSuicide());
        commandHandler.registerCommand(new CommandSpawn());
        commandHandler.registerCommand(new CommandSetSpawn());
    }

    private void shutdown() throws Throwable {
        teleportPositionsHandler.saveData();
        autoNoticeHandler.saveNotices();
    }

    private void load18N(Locale locale) throws IOException {
        InputStream stream = getResource("lang/" + locale.toLanguageTag() + ".lang");
        if (stream == null) {
            logger.warning("I18n file for locale '" + locale.toLanguageTag() + "' does not exists");
        } else {
            logger.info("Loading I18N file for locale '" + locale.toLanguageTag() + "'");
            I18n.loadSourcesFromStream(stream, locale);
        }
    }
}
