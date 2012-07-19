/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import com.citygrid.content.places.detail.CGPlacesDetail;
import com.citygrid.content.places.detail.CGPlacesDetailLocation;

public interface CGLocationDetailProvider {
    /**
     * Convenience methods that returns a builder {@link CGPlacesDetail} for current location.
     * @return
     */
    CGPlacesDetail placesDetail();

    /**
     * Convenience methods that returns details of current location.
     * @return
     */
    CGPlacesDetailLocation placesDetailLocation() throws CGException;
}
