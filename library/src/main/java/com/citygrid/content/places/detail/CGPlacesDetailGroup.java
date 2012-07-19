package com.citygrid.content.places.detail;

import java.io.Serializable;

public class CGPlacesDetailGroup implements Serializable {
    private int groupId;
    private String name;

    public CGPlacesDetailGroup(int groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetailGroup)) return false;

        CGPlacesDetailGroup that = (CGPlacesDetailGroup) o;

        if (groupId != that.groupId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("groupId=").append(groupId);
        sb.append(",name=").append(name);
        sb.append('>');
        return sb.toString();
    }
}
