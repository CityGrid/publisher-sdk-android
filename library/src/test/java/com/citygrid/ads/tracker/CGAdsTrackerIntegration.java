/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.ads.tracker;

import com.citygrid.CGTConstants;
import com.citygrid.CityGrid;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class CGAdsTrackerIntegration extends CGAdsTrackerTest {
    @BeforeClass
    public static void beforeClass() {
        CityGrid.setPublisher(CGTConstants.CGT_PUBLISHER);
        CityGrid.setSimulation(false);
        CityGrid.setMuid(CGTConstants.PLACEHOLDER_MUID);
        CityGrid.setMobileType(CGTConstants.PLACEHOLDER_MOBILE_MODEL);
    }
    @AfterClass
    public static void afterClass() {
        CityGrid.setPublisher(null);
        CityGrid.setSimulation(true);
        CityGrid.setMuid(null);
        CityGrid.setMobileType(null);
    }

}
