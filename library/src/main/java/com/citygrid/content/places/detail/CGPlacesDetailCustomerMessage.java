package com.citygrid.content.places.detail;

import java.io.Serializable;
import java.net.URI;

public class CGPlacesDetailCustomerMessage implements Serializable {
    private String message;
    private String attributionText;
    private URI attributionLogo;
    private String attributionSource;

    public CGPlacesDetailCustomerMessage(String message, String attributionText, URI attributionLogo, String attributionSource) {
        this.message = message;
        this.attributionText = attributionText;
        this.attributionLogo = attributionLogo;
        this.attributionSource = attributionSource;
    }

    public String getMessage() {
        return message;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public URI getAttributionLogo() {
        return attributionLogo;
    }

    public String getAttributionSource() {
        return attributionSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetailCustomerMessage)) return false;

        CGPlacesDetailCustomerMessage that = (CGPlacesDetailCustomerMessage) o;

        if (attributionLogo != null ? !attributionLogo.equals(that.attributionLogo) : that.attributionLogo != null)
            return false;
        if (attributionSource != null ? !attributionSource.equals(that.attributionSource) : that.attributionSource != null)
            return false;
        if (attributionText != null ? !attributionText.equals(that.attributionText) : that.attributionText != null)
            return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (attributionText != null ? attributionText.hashCode() : 0);
        result = 31 * result + (attributionLogo != null ? attributionLogo.hashCode() : 0);
        result = 31 * result + (attributionSource != null ? attributionSource.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("message=").append(message);
        sb.append(",attributionText=").append(attributionText);
        sb.append(",attributionLogo=").append(attributionLogo);
        sb.append(",attributionSource=").append(attributionSource);
        sb.append('>');
        return sb.toString();
    }
}
