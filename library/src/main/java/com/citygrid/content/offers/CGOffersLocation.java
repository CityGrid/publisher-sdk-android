/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.offers;

import com.citygrid.*;
import com.citygrid.content.places.detail.CGPlacesDetail;
import com.citygrid.content.places.detail.CGPlacesDetailLocation;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;

public class CGOffersLocation implements CGLocation, CGLocationDetailProvider, Serializable {
    private CGBaseLocation baseLocation;
    private String businessHours;
    private CGTag[] tags;

    private CGOffersLocation(Builder builder) {
        baseLocation = builder.baseLocation;
        businessHours = builder.businessHours;
        tags = builder.tags;
    }

    public static class Builder {
        private CGBaseLocation baseLocation;
        private String businessHours;
        private CGTag[] tags;

        public CGOffersLocation build() {
            return new CGOffersLocation(this);
        }

        public Builder baseLocation(CGBaseLocation baseLocation) {
            this.baseLocation = baseLocation;
            return this;
        }

        public Builder businessHours(String businessHours) {
            this.businessHours = businessHours;
            return this;
        }

        public Builder tags(CGTag[] tags) {
            this.tags = tags;
            return this;
        }
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public CGTag[] getTags() {
        return tags;
    }

    // start delegate methods
    public int getLocationId() {
        return baseLocation.getLocationId();
    }

    public String getImpressionId() {
        return baseLocation.getImpressionId();
    }

    public String getName() {
        return baseLocation.getName();
    }

    public CGAddress getAddress() {
        return baseLocation.getAddress();
    }

    public CGLatLon getLatlon() {
        return baseLocation.getLatlon();
    }

    public URI getImage() {
        return baseLocation.getImage();
    }

    public String getPhone() {
        return baseLocation.getPhone();
    }

    public int getRating() {
        return baseLocation.getRating();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CGPlacesDetail placesDetail() {
        return baseLocation.placesDetail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CGPlacesDetailLocation placesDetailLocation() throws CGException {
        return baseLocation.placesDetailLocation();
    }
    // end delegate methods


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGOffersLocation)) return false;

        CGOffersLocation that = (CGOffersLocation) o;

        if (businessHours != null ? !businessHours.equals(that.businessHours) : that.businessHours != null)
            return false;
        if (baseLocation != null ? !baseLocation.equals(that.baseLocation) : that.baseLocation != null) return false;
        if (!Arrays.equals(tags, that.tags)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = baseLocation != null ? baseLocation.hashCode() : 0;
        result = 31 * result + (businessHours != null ? businessHours.hashCode() : 0);
        result = 31 * result + (tags != null ? Arrays.hashCode(tags) : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("baseLocation=").append(baseLocation);
        sb.append(",businessHours=").append(businessHours);
        sb.append(",tags=").append(tags == null ? "null" : Arrays.toString(tags));
        sb.append('>');
        return sb.toString();
    }
}
