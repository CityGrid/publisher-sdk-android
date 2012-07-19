package com.citygrid.content.places.detail;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;

public class CGPlacesDetailOffer implements Serializable {
    private String name;
    private String text;
    private String offerDescription;
    private URI url;
    private Date expirationDate;

    public CGPlacesDetailOffer(String name, String text, String offerDescription, URI url, Date expirationDate) {
        this.name = name;
        this.text = text;
        this.offerDescription = offerDescription;
        this.url = url;

        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public URI getUrl() {
        return url;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetailOffer)) return false;

        CGPlacesDetailOffer that = (CGPlacesDetailOffer) o;

        if (expirationDate != null ? !expirationDate.equals(that.expirationDate) : that.expirationDate != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (offerDescription != null ? !offerDescription.equals(that.offerDescription) : that.offerDescription != null)
            return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (offerDescription != null ? offerDescription.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("name=").append(name);
        sb.append(",text=").append(text);
        sb.append(",offerDescription=").append(offerDescription);
        sb.append(",url=").append(url);
        sb.append(",expirationDate=").append(expirationDate);
        sb.append('>');
        return sb.toString();
    }
}
