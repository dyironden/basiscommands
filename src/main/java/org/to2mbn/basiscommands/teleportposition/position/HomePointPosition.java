package org.to2mbn.basiscommands.teleportposition.position;

import java.util.Objects;

public class HomePointPosition extends WarpPointPosition {
    protected final String ownersName;

    public HomePointPosition(String ownersName, String name, double x, double y, double z, int level) {
        super(x, y, z, level, name);
        this.ownersName = ownersName;
    }

    public String getOwnersName() {
        return ownersName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        HomePointPosition that = (HomePointPosition) o;
        return super.equals(o) && Objects.equals(ownersName, that.ownersName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ownersName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HomePosition{");
        sb.append("ownersName='").append(ownersName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
