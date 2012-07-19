/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.ads.custom;

import com.citygrid.*;
import com.citygrid.content.places.detail.CGPlacesDetail;
import com.citygrid.content.places.detail.CGPlacesDetailLocation;

import java.io.Serializable;
import java.net.URI;

public class CGAdsCustomAd implements CGLocation, CGLocationDetailProvider, Serializable {
    private CGBaseLocation baseLocation;
    private int adId;
    private String type;
    private String tagline;
    private String businessDescription;
    private URI destinationUrl;
    private URI displayUrl;
    private float ppe;
    private int reviews;
    private String offers;
    private float distance;
    private String attributionText;

    private CGAdsCustomAd(Builder builder) {
        baseLocation = builder.baseLocation;
        adId = builder.adId;
        type = builder.type;
        tagline = builder.tagline;
        businessDescription = builder.businessDescription;
        destinationUrl = builder.destinationUrl;
        displayUrl = builder.displayUrl;
        ppe = builder.ppe;
        reviews = builder.reviews;
        offers = builder.offers;
        distance = builder.distance;
        attributionText = builder.attributionText;
    }

    public static class Builder {
        private CGBaseLocation baseLocation;
        private int adId;
        private String type;
        private String tagline;
        private String businessDescription;
        private URI destinationUrl;
        private URI displayUrl;
        private float ppe;
        private int reviews;
        private String offers;
        private float distance;
        private String attributionText;

        public CGAdsCustomAd build() {
            return new CGAdsCustomAd(this);
        }

        public Builder baseLocation(CGBaseLocation baseLocation) {
            this.baseLocation = baseLocation;
            return this;
        }

        public Builder adId(int adId) {
            this.adId = adId;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder tagline(String tagline) {
            this.tagline = tagline;
            return this;
        }

        public Builder businessDescription(String businessDescription) {
            this.businessDescription = businessDescription;
            return this;
        }

        public Builder destinationUrl(URI destinationUrl) {
            this.destinationUrl = destinationUrl;
            return this;
        }

        public Builder displayUrl(URI displayUrl) {
            this.displayUrl = displayUrl;
            return this;
        }

        public Builder ppe(float ppe) {
            this.ppe = ppe;
            return this;
        }

        public Builder reviews(int reviews) {
            this.reviews = reviews;
            return this;
        }

        public Builder offers(String offers) {
            this.offers = offers;
            return this;
        }

        public Builder distance(float distance) {
            this.distance = distance;
            return this;
        }
        
        public Builder attributionText(String attributionText) {
            this.attributionText = attributionText;
            return this;
        }
    }

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

    public int getAdId() {
        return adId;
    }

    public String getType() {
        return type;
    }

    public String getTagline() {
        return tagline;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public URI getDestinationUrl() {
        return destinationUrl;
    }

    public URI getDisplayUrl() {
        return displayUrl;
    }

    public float getPpe() {
        return ppe;
    }

    public int getReviews() {
        return reviews;
    }

    public String getOffers() {
        return offers;
    }

    public float getDistance() {
        return distance;
    }
    
    public String getAttributionText() {
        return attributionText;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGAdsCustomAd)) return false;

        CGAdsCustomAd that = (CGAdsCustomAd) o;

        if (adId != that.adId) return false;
        if (Float.compare(that.distance, distance) != 0) return false;
        if (Float.compare(that.ppe, ppe) != 0) return false;
        if (reviews != that.reviews) return false;
        if (baseLocation != null ? !baseLocation.equals(that.baseLocation) : that.baseLocation != null) return false;
        if (businessDescription != null ? !businessDescription.equals(that.businessDescription) : that.businessDescription != null)
            return false;
        if (destinationUrl != null ? !destinationUrl.equals(that.destinationUrl) : that.destinationUrl != null)
            return false;
        if (displayUrl != null ? !displayUrl.equals(that.displayUrl) : that.displayUrl != null) return false;
        if (offers != null ? !offers.equals(that.offers) : that.offers != null) return false;
        if (tagline != null ? !tagline.equals(that.tagline) : that.tagline != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (attributionText != null ? !attributionText.equals(that.attributionText) : that.attributionText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = baseLocation != null ? baseLocation.hashCode() : 0;
        result = 31 * result + adId;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (tagline != null ? tagline.hashCode() : 0);
        result = 31 * result + (businessDescription != null ? businessDescription.hashCode() : 0);
        result = 31 * result + (destinationUrl != null ? destinationUrl.hashCode() : 0);
        result = 31 * result + (displayUrl != null ? displayUrl.hashCode() : 0);
        result = 31 * result + (ppe != +0.0f ? Float.floatToIntBits(ppe) : 0);
        result = 31 * result + reviews;
        result = 31 * result + (offers != null ? offers.hashCode() : 0);
        result = 31 * result + (distance != +0.0f ? Float.floatToIntBits(distance) : 0);
        result = 31 * result + (attributionText != null ? attributionText.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("baseLocation=").append(baseLocation);
        sb.append(",adId=").append(adId);
        sb.append(",type=").append(type);
        sb.append(",tagline=").append(tagline);
        sb.append(",businessDescription=").append(businessDescription);
        sb.append(",destinationUrl=").append(destinationUrl);
        sb.append(",displayUrl=").append(displayUrl);
        sb.append(",ppe=").append(ppe);
        sb.append(",reviews=").append(reviews);
        sb.append(",offers=").append(offers);
        sb.append(",distance=").append(distance);
        sb.append(",attributionText=").append(attributionText);
        sb.append('>');
        return sb.toString();
    }
}
