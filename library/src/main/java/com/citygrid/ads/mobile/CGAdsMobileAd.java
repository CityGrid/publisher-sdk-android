/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.ads.mobile;

import com.citygrid.CGException;
import com.citygrid.CGLocationDetailProvider;
import com.citygrid.CGLocationDetailProviderImpl;
import com.citygrid.HasLocationIdAndImpressionId;
import com.citygrid.content.places.detail.CGPlacesDetail;
import com.citygrid.content.places.detail.CGPlacesDetailLocation;

import java.net.URI;

public class CGAdsMobileAd implements CGLocationDetailProvider, HasLocationIdAndImpressionId {
    private int locationId;
    private String impressionId;
    private int adId;
    private URI destinationUrl;
    private URI image;
    private CGLocationDetailProvider detailProvider;

    public CGAdsMobileAd(int locationId, String impressionId, int adId, URI destinationUrl, URI image) {
        this.locationId = locationId;
        this.impressionId = impressionId;
        this.adId = adId;
        this.destinationUrl = destinationUrl;
        this.image = image;

        detailProvider = new CGLocationDetailProviderImpl(this);
    }

    public int getLocationId() {
        return locationId;
    }

    public String getImpressionId() {
        return impressionId;
    }

    public int getAdId() {
        return adId;
    }

    public URI getDestinationUrl() {
        return destinationUrl;
    }

    public URI getImage() {
        return image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CGPlacesDetail placesDetail() {
        return detailProvider.placesDetail();
    }

    /**
     * Convenience methods that returns details of current location.
     * @return
     */
    @Override
    public CGPlacesDetailLocation placesDetailLocation() throws CGException {
        return detailProvider.placesDetailLocation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGAdsMobileAd)) return false;

        CGAdsMobileAd that = (CGAdsMobileAd) o;

        if (adId != that.adId) return false;
        if (locationId != that.locationId) return false;
        if (destinationUrl != null ? !destinationUrl.equals(that.destinationUrl) : that.destinationUrl != null)
            return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (impressionId != null ? !impressionId.equals(that.impressionId) : that.impressionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locationId;
        result = 31 * result + (impressionId != null ? impressionId.hashCode() : 0);
        result = 31 * result + adId;
        result = 31 * result + (destinationUrl != null ? destinationUrl.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("locationId=").append(locationId);
        sb.append(",impressionId=").append(impressionId);
        sb.append(",adId=").append(adId);
        sb.append(",destinationUrl=").append(destinationUrl);
        sb.append(",image=").append(image);
        sb.append('>');
        return sb.toString();
    }
}
