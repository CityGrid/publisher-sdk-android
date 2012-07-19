package com.citygrid.content.places.detail;

import java.io.Serializable;

public class CGPlacesDetailAttribute implements Serializable {
    private int attributeId;
    private String name;
    private String value;

    public CGPlacesDetailAttribute(int attributeId, String name, String value) {
        this.attributeId = attributeId;
        this.name = name;
        this.value = value;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetailAttribute)) return false;

        CGPlacesDetailAttribute that = (CGPlacesDetailAttribute) o;

        if (attributeId != that.attributeId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = attributeId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("attributeId=").append(attributeId);
        sb.append(",name=").append(name);
        sb.append(",value=").append(value);
        sb.append('>');
        return sb.toString();
    }
}
