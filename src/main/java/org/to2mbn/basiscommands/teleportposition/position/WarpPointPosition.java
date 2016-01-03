package org.to2mbn.basiscommands.teleportposition.position;

import java.util.Objects;

public class WarpPointPosition extends TeleportPointPosition {
    protected final String name;

    public WarpPointPosition(double x, double y, double z, int level, String name) {
        super(x, y, z, level);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        WarpPointPosition that = (WarpPointPosition) o;
        return super.equals(o) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WarpPosition{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
