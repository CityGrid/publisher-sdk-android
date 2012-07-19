package com.citygrid;

import com.citygrid.content.places.detail.CGPlacesDetail;
import com.citygrid.content.places.detail.CGPlacesDetailLocation;

import java.net.URI;

public class CGBaseLocation implements CGLocation, CGLocationDetailProvider, HasLocationIdAndImpressionId {
    private int locationId;
    private String impressionId;
    private String name;
    private CGAddress address;
    private CGLatLon latlon;
    private URI image;
    private String phone;
    private int rating;

    private CGLocationDetailProvider detailProvider;

    private CGBaseLocation(Builder builder) {
        locationId = builder.locationId;
        impressionId = builder.impressionId;
        name = builder.name;
        address = builder.address;
        latlon = builder.latlon;
        image = builder.image;
        phone = builder.phone;
        rating = builder.rating;

        detailProvider = new CGLocationDetailProviderImpl(this);
    }


    public static class Builder {
        private int locationId;
        private String impressionId;
        private String name;
        private CGAddress address;
        private CGLatLon latlon;
        private URI image;
        private String phone;
        private int rating;

        public Builder locationId(int locationId) {
            this.locationId = locationId;
            return this;
        }

        public Builder impressionId(String impressionId) {
            this.impressionId = impressionId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(CGAddress address) {
            this.address = address;
            return this;
        }

        public Builder latlon(CGLatLon latlon) {
            this.latlon = latlon;
            return this;
        }

        public Builder image(URI image) {
            this.image = image;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder rating(int rating) {
            this.rating = rating;
            return this;
        }

        public CGBaseLocation build() {
            return new CGBaseLocation(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CGPlacesDetail placesDetail() {
        return detailProvider.placesDetail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CGPlacesDetailLocation placesDetailLocation() throws CGException {
        return detailProvider.placesDetailLocation();
    }

    public int getLocationId() {
        return locationId;
    }

    public String getImpressionId() {
        return impressionId;
    }

    public String getName() {
        return name;
    }

    public CGAddress getAddress() {
        return address;
    }

    public CGLatLon getLatlon() {
        return latlon;
    }

    public URI getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGBaseLocation)) return false;

        CGBaseLocation that = (CGBaseLocation) o;

        if (locationId != that.locationId) return false;
        if (rating != that.rating) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (impressionId != null ? !impressionId.equals(that.impressionId) : that.impressionId != null) return false;
        if (latlon != null ? !latlon.equals(that.latlon) : that.latlon != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locationId;
        result = 31 * result + (impressionId != null ? impressionId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (latlon != null ? latlon.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + rating;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("locationId=").append(locationId);
        sb.append(",impressionId=").append(impressionId);
        sb.append(",name=").append(name);
        sb.append(",address=").append(address);
        sb.append(",latlon=").append(latlon);
        sb.append(",image=").append(image);
        sb.append(",phone=").append(phone);
        sb.append(",rating=").append(rating);
        sb.append('>');
        return sb.toString();
    }
}
