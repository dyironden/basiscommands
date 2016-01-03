package org.to2mbn.basiscommands.teleportposition;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import com.google.common.collect.Lists;
import com.google.gson.*;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.teleportposition.position.HomePointPosition;
import org.to2mbn.basiscommands.teleportposition.position.TeleportPointPosition;
import org.to2mbn.basiscommands.teleportposition.position.WarpPointPosition;

import java.io.*;
import java.util.*;

public class TeleportPositionsHandler {
    private final Gson gson;
    private final File positionsFile;

    private final List<WarpPointPosition> warpPositions;
    private final Map<Integer, TeleportPointPosition> spawnPositions;
    private final Map<String, List<HomePointPosition>> homePositions;
    private final Map<String, TeleportPointPosition> backPositions;

    public TeleportPositionsHandler(File _dataFile) throws IOException, ClassNotFoundException {
        gson = new GsonBuilder().setPrettyPrinting().create();
        spawnPositions = new HashMap<>();
        warpPositions = new ArrayList<>();
        homePositions = new HashMap<>();
        backPositions = new HashMap<>();
        positionsFile = _dataFile;

        if (positionsFile.exists()) {
            try {
                loadData();
                return;
            } catch (JsonSyntaxException ex) {
                BasisCommands.logger().error("Invalid positions data file");
                ex.printStackTrace();
                positionsFile.delete();
            }
        } else {
            BasisCommands.logger().info("Positions data file does not exists");
        }

        positionsFile.createNewFile();
        loadLevelSpawnPoints();
        saveData(); // save the spawn point positions
    }

    private void loadLevelSpawnPoints() {
        Server.getInstance().getLevels()
                .forEach((id, level) -> spawnPositions.put(id, TeleportPointPosition.fromPosition(level.getSpawnLocation())));
    }

    public void loadData() throws IOException, JsonSyntaxException {
        try (Reader reader = new FileReader(positionsFile)) {
            JsonObject obj = new JsonParser().parse(reader).getAsJsonObject();
            warpPositions.addAll(gson.fromJson(obj.get("warpPointPositions"), warpPositions.getClass()));
            spawnPositions.putAll(gson.fromJson(obj.get("spawnPointPositions"), spawnPositions.getClass()));
            homePositions.putAll(gson.fromJson(obj.get("homePointPositions"), homePositions.getClass()));
            backPositions.putAll(gson.fromJson(obj.get("backPointPositions"), backPositions.getClass()));
        }
    }

    public void saveData() throws IOException {
        JsonObject obj = new JsonObject();
        obj.add("warpPointPositions", gson.toJsonTree(warpPositions, warpPositions.getClass()));
        obj.add("spawnPointPositions", gson.toJsonTree(spawnPositions, spawnPositions.getClass()));
        obj.add("homePointPositions", gson.toJsonTree(homePositions, homePositions.getClass()));
        obj.add("backPointPositions", gson.toJsonTree(backPositions, backPositions.getClass()));

        try (Writer writer = new FileWriter(positionsFile)) {
            writer.write(obj.toString());
        }
    }

    // ==================Home Point Positions===================

    public void addHomePointPosition(Player player, HomePointPosition position) {
        String playerName = player.getName();
        if (!homePositions.containsKey(playerName)) {
            homePositions.put(playerName, Lists.newArrayList());
        }

        homePositions.get(playerName).add(position);
    }

    public void removeHomePointPosition(Player player, String homeName) {
        HomePointPosition position = getHomePointPositionByName(player, homeName);
        if (position != null) {
            removeHomePointPosition(player, position);
        }
    }

    public void removeHomePointPosition(Player player, HomePointPosition position) {
        getPlayerHomePositions(player).remove(position);
    }

    public boolean hasHomePointPosition(Player player, String homeName) {
        return getPlayerHomePositions(player).stream().anyMatch(position -> position.getName().equalsIgnoreCase(homeName));
    }

    public List<HomePointPosition> getPlayerHomePositions(Player player) {
        List<HomePointPosition> positions = homePositions.get(player.getName());
        return positions != null ? positions : Lists.newArrayList();
    }

    public HomePointPosition getHomePointPositionByName(Player player, String name) {
        Iterator<HomePointPosition> iterator = getPlayerHomePositions(player).iterator();
        while (iterator.hasNext()) {
            HomePointPosition position = iterator.next();
            if (position.getName().equals(name)) {
                return position;
            }
        }

        return null;
    }

    public int getHomePointCount(Player player) {
        return getPlayerHomePositions(player).size();
    }

    // =========================================================

    // ==================Spawn Point Positions==================

    public void setSpawnPointPosition(Level level, Vector3 vector3) {
        level.setSpawnLocation(vector3);
        spawnPositions.put(level.getId(), new TeleportPointPosition(vector3.getX(), vector3.getY(), vector3.getZ(), level.getId()));
    }

    public TeleportPointPosition getSpawnPointPosition(int levelId) {
        return spawnPositions.get(levelId);
    }

    public TeleportPointPosition getSpawnPointPosition(Level level) {
        return getSpawnPointPosition(level.getId());
    }

    // ========================================================

    // ==================Warp Point Positions==================

    public void addWarpPointPosition(WarpPointPosition position) {
        warpPositions.add(position);
    }

    public void removeWarpPointPosition(int id) {
        warpPositions.remove(id);
    }

    public void removeWarpPointPosition(WarpPointPosition position) {
        warpPositions.remove(position);
    }

    public boolean hasWarpPointPosition(String name) {
        return warpPositions.stream().anyMatch(position -> position.getName().equalsIgnoreCase(name));
    }

    public WarpPointPosition getWarpPointPosition(String name) {
        Iterator<WarpPointPosition> iterator = warpPositions.iterator();
        while (iterator.hasNext()) {
            WarpPointPosition position = iterator.next();
            if (position.getName().equalsIgnoreCase(name)) {
                return position;
            }
        }

        return null;
    }

    // ========================================================

    // ==================Back Point Positions==================

    public void updatePlayerBackPointPosition(Player player, TeleportPointPosition position) {
        backPositions.put(player.getName(), position);
    }

    public TeleportPointPosition getPlayerBackPointPosition(Player player) {
        return backPositions.get(player.getName());
    }

    // ========================================================

    public File getPositionsFile() {
        return positionsFile;
    }

    public List<WarpPointPosition> getWarpPositions() {
        return warpPositions;
    }

    public Map<Integer, TeleportPointPosition> getSpawnPositions() {
        return spawnPositions;
    }

    public Map<String, List<HomePointPosition>> getHomePositions() {
        return homePositions;
    }

    public Map<String, TeleportPointPosition> getBackPositions() {
        return backPositions;
    }
}
