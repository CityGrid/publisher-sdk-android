package com.citygrid.content.places.detail;

import java.io.Serializable;
import java.net.URI;

public class CGPlacesDetailImage implements Serializable {
    private CGPlacesDetailImageType type;
    private int height;
    private int width;
    private URI url;

    public CGPlacesDetailImage(CGPlacesDetailImageType type, int height, int width, URI url) {
        this.type = type;
        this.height = height;
        this.width = width;
        this.url = url;
    }

    public CGPlacesDetailImageType getType() {
        return type;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public URI getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetailImage)) return false;

        CGPlacesDetailImage that = (CGPlacesDetailImage) o;

        if (height != that.height) return false;
        if (width != that.width) return false;
        if (type != that.type) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + height;
        result = 31 * result + width;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("type=").append(type);
        sb.append(",height=").append(height);
        sb.append(",width=").append(width);
        sb.append(",url=").append(url);
        sb.append('>');
        return sb.toString();
    }
}
