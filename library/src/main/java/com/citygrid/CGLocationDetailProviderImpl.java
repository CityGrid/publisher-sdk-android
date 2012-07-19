/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import com.citygrid.content.places.detail.CGPlacesDetail;
import com.citygrid.content.places.detail.CGPlacesDetailLocation;

public class CGLocationDetailProviderImpl implements CGLocationDetailProvider{
    private HasLocationIdAndImpressionId hasLocationIdAndImpressionId;

    public CGLocationDetailProviderImpl(HasLocationIdAndImpressionId hasLocationIdAndImpressionId) {
        this.hasLocationIdAndImpressionId = hasLocationIdAndImpressionId;
    }

    /**
     * Convenience methods that returns a builder {@link CGPlacesDetail} for current location.
     * @return
     */
    @Override
    public CGPlacesDetail placesDetail() {
        CGPlacesDetail detail = CGPlacesDetail.placesDetail();
        detail.setLocationId(hasLocationIdAndImpressionId.getLocationId());
        detail.setImpressionId(hasLocationIdAndImpressionId.getImpressionId());
        return detail;
    }

    /**
     * Convenience methods that returns details of current location.
     * @return
     */
    @Override
    public CGPlacesDetailLocation placesDetailLocation() throws CGException {
        return this.placesDetail().detail().getLocation();
    }
}
