package com.citygrid.content.places.detail;

import java.io.Serializable;

public class CGPlacesDetailTip implements Serializable {
    private String name;
    private String text;

    public CGPlacesDetailTip(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetailTip)) return false;

        CGPlacesDetailTip that = (CGPlacesDetailTip) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("name=").append(name);
        sb.append(",text=").append(text);
        sb.append('>');
        return sb.toString();
    }
}
