package org.to2mbn.basiscommands.teleportposition.position;

import cn.nukkit.Server;
import cn.nukkit.level.Position;

import java.util.Objects;

public class TeleportPointPosition {
    protected final double x, y, z;
    protected final int level;

    public TeleportPointPosition(double x, double y, double z, int level) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.level = level;
    }

    public static TeleportPointPosition fromPosition(Position position) {
        return new TeleportPointPosition(position.getX(), position.getY(), position.getZ(), position.getLevel().getId());
    }

    public Position toPosition() {
        return new Position(x, y, z, Server.getInstance().getLevel(level));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TeleportPointPosition that = (TeleportPointPosition) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                Double.compare(that.z, z) == 0 &&
                level == that.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, level);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeleportPosition{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", z=").append(z);
        sb.append(", level=").append(level);
        sb.append('}');
        return sb.toString();
    }
}
