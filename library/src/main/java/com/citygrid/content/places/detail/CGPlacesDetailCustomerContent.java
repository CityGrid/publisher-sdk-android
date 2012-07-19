package com.citygrid.content.places.detail;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;

public class CGPlacesDetailCustomerContent implements Serializable {
    private CGPlacesDetailCustomerMessage message;
    private String[] bullets;
    private URI url;

    public CGPlacesDetailCustomerContent(CGPlacesDetailCustomerMessage message, String[] bullets, URI url) {
        this.message = message;
        this.bullets = bullets;
        this.url = url;
    }

    public CGPlacesDetailCustomerMessage getMessage() {
        return message;
    }

    public String[] getBullets() {
        return bullets;
    }

    public URI getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetailCustomerContent)) return false;

        CGPlacesDetailCustomerContent that = (CGPlacesDetailCustomerContent) o;

        if (!Arrays.equals(bullets, that.bullets)) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (bullets != null ? Arrays.hashCode(bullets) : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("message=").append(message);
        sb.append(",bullets=").append(bullets == null ? "null" : Arrays.toString(bullets));
        sb.append(",url=").append(url);
        sb.append('>');
        return sb.toString();
    }
}
