package org.to2mbn.basiscommands.autonotice;

import cn.nukkit.Server;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Utils;
import com.google.common.collect.Lists;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.utils.IOUtils;
import org.to2mbn.basiscommands.utils.PluginUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AutoNoticeHandler extends PluginTask<BasisCommands> {
    protected final List<String> noticeList;
    protected final File noticeFile;
    protected final Random rand;
    protected final int delay;
    private int lastTick = 1;

    public AutoNoticeHandler(BasisCommands owner, File _noticeFile, int _delay) throws IOException {
        super(owner);
        rand = new Random(System.currentTimeMillis());
        noticeList = Collections.synchronizedList(Lists.newArrayList());
        noticeFile = _noticeFile;
        delay = _delay;

        loadNotices();
    }

    private void loadNotices() throws IOException {
        IOUtils.readLines(noticeFile)
                .stream()
                .filter(line -> !line.startsWith("#") && !line.isEmpty())
                .forEach(this::addNotice);
    }

    public void addNotice(String notice) {
        noticeList.add(notice);
    }

    public void removeNotice(int index) {
        noticeList.remove(index);
    }

    public void triggerNotice() {
        if (noticeList.size() < 1) {
            return;
        }
        String notice = PluginUtils.colorize(noticeList.get(rand.nextInt(noticeList.size())));
        Server.getInstance().getOnlinePlayers().values().forEach(player -> player.sendMessage(notice));
        BasisCommands.logger().info(notice);
    }

    @Override
    public void onRun(int tick) {
        if (((tick - lastTick) / 20) >= delay) {
            triggerNotice();
            lastTick = tick;
        }
    }

    public void saveNotices() throws IOException {
        StringBuilder builder = new StringBuilder();
        for (Iterator<String> iterator = noticeList.iterator(); iterator.hasNext(); ) {
            builder.append(iterator.next()).append(IOUtils.LINE_SPEARATOR);
        }
        Utils.writeFile(noticeFile, builder.toString());
    }

    public List<String> getNoticeList() {
        return noticeList;
    }
}
