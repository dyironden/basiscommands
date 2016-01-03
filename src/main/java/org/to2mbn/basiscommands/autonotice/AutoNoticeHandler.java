package org.to2mbn.basiscommands.autonotice;

import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Utils;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.util.PluginUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AutoNoticeHandler extends PluginTask<BasisCommands> {
    protected final List<String> noticeList;
    protected final File noticeFile;
    protected final Random rand;
    protected final int delay;
    private int lastTick = 1;

    public AutoNoticeHandler(BasisCommands owner, File _noticeFile, int _delay) {
        super(owner);
        rand = new Random(System.currentTimeMillis());
        noticeList = Collections.synchronizedList(Lists.newArrayList());
        noticeFile = _noticeFile;
        delay = _delay;

        try {
            loadNotices();
        } catch (IOException | JsonSyntaxException e) {
            BasisCommands.logger().error("Failed to load notices");
            e.printStackTrace();
        }
    }

    public void loadNotices() throws IOException, JsonSyntaxException {
        try (JsonReader reader = new JsonReader(new FileReader(noticeFile))) {
            reader.setLenient(true);
            JsonArray notices = new JsonParser().parse(reader).getAsJsonArray();
            for (JsonElement notice : notices) {
                addNotice(notice.getAsString());
            }
        }
    }

    public void saveNotices() throws IOException {
        JsonArray array = new JsonArray();
        noticeList.forEach(array::add);
        Utils.writeFile(noticeFile, array.toString());
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
        PluginUtils.boardcastMessage(notice);
    }

    @Override
    public void onRun(int tick) {
        if (((tick - lastTick) / 20) >= delay) {
            triggerNotice();
            lastTick = tick;
        }
    }

    public List<String> getNoticeList() {
        return noticeList;
    }
}
