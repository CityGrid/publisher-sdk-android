/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import java.net.URI;

public interface CGLocation extends HasLocationIdAndImpressionId {
    String getName();
    CGAddress getAddress();
    CGLatLon getLatlon();
    URI getImage();
    String getPhone();
    int getRating();
}
