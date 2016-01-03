package org.to2mbn.basiscommands.teleportrequest;

import cn.nukkit.Player;

import java.util.Objects;

public class TeleportRequest {
    private final Player requestPlayer, targetPlayer;
    private final int time;
    private final RequestMode mode;

    public TeleportRequest(Player requestPlayer, Player targetPlayer, int time, RequestMode mode) {
        this.requestPlayer = requestPlayer;
        this.targetPlayer = targetPlayer;
        this.time = time;
        this.mode = mode;
    }

    public Player getRequestPlayer() {
        return requestPlayer;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }

    public int getTime() {
        return time;
    }

    public RequestMode getMode() {
        return mode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TeleportRequest request = (TeleportRequest) o;
        return Objects.equals(requestPlayer.getName(), request.requestPlayer.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestPlayer.getName());
    }
}
