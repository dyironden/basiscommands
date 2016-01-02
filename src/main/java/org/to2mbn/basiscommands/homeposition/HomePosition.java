package org.to2mbn.basiscommands.homeposition;

import cn.nukkit.Server;
import cn.nukkit.level.Position;

import java.io.Serializable;
import java.util.Objects;

public class HomePosition implements Serializable {
    private static final long serialVersionUID = 42L;

    private String ownersName, name;
    private double x, y, z;
    private int level;

    public HomePosition(String ownersName, String name, double x, double y, double z, int level) {
        this.ownersName = ownersName;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.level = level;
    }

    public String getOwnersName() {
        return ownersName;
    }

    public String getName() {
        return name;
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

    public Position toPosition() {
        return new Position(x, y, z, Server.getInstance().getLevel(level));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        HomePosition position = (HomePosition) o;
        return Double.compare(position.x, x) == 0 &&
                Double.compare(position.y, y) == 0 &&
                Double.compare(position.z, z) == 0 &&
                level == position.level &&
                Objects.equals(ownersName, position.ownersName) &&
                Objects.equals(name, position.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownersName, name, x, y, z, level);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HomePosition{");
        sb.append("ownersName='").append(ownersName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", z=").append(z);
        sb.append(", level=").append(level);
        sb.append('}');
        return sb.toString();
    }
}
