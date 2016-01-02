package org.to2mbn.basiscommands.homeposition;

import cn.nukkit.Player;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.to2mbn.basiscommands.BasisCommands;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HomePositionsHandler {
    private final Gson gson;
    private final File positionsFile;
    private final Map<String, List<HomePosition>> datas;

    public HomePositionsHandler(File _positionsFile) throws IOException, ClassNotFoundException {
        gson = new Gson();
        positionsFile = _positionsFile;
        datas = new HashMap<>();

        if (!positionsFile.exists()) {
            BasisCommands.logger().info("Player home positions data file does not found");
            positionsFile.createNewFile();
        } else {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(positionsFile))) {
                Object obj = inputStream.readObject();
                datas.putAll((Map) obj);
            }
        }
    }

    public List<HomePosition> getPlayerHomePositions(Player player) {
        List<HomePosition> positions = datas.get(player.getName());
        return positions != null ? positions : Lists.newArrayList();
    }

    public HomePosition getHomePositionByName(Player player, String name) {
        Iterator<HomePosition> iterator = getPlayerHomePositions(player).iterator();
        while (iterator.hasNext()) {
            HomePosition position = iterator.next();
            if (position.getName().equals(name)) {
                return position;
            }
        }

        return null;
    }

    public int getHomeCount(Player player) {
        return getPlayerHomePositions(player).size();
    }

    public void addHomePosition(Player player, HomePosition position) {
        String playerName = player.getName();
        if (!datas.containsKey(playerName)) {
            datas.put(playerName, Lists.newArrayList());
        }

        datas.get(playerName).add(position);
    }

    public void removeHomePosition(Player player, String homeName) {
        HomePosition position = getHomePositionByName(player, homeName);
        if (position != null) {
            removeHomePosition(player, position);
        }
    }

    public void removeHomePosition(Player player, HomePosition position) {
        getPlayerHomePositions(player).remove(position);
    }

    public void saveData() throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(positionsFile))) {
            outputStream.writeObject(datas);
        }
    }
}
