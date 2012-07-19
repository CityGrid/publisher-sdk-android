/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.ads.mobile;

import com.citygrid.*;
import com.citygrid.content.places.detail.CGPlacesDetailLocation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CGAdsMobileIntegration extends CGAdsMobileTest {
    @BeforeClass
    public static void beforeClass() {
        CityGrid.setPublisher(CGTConstants.CGT_PUBLISHER);
        CityGrid.setSimulation(false);
        CityGrid.setMuid(CGTConstants.PLACEHOLDER_MUID);
    } 

    @AfterClass
    public static void afterClass() {
        CityGrid.setPublisher(null);
        CityGrid.setSimulation(true);
        CityGrid.setMuid(null);
    }

    @Test
    public void testFindRestaurantsInLosAngeles() throws CGException {
        CGAdsMobile search = CityGrid.adsMobile();
        search.setWhat("restaurant");
        search.setWhere("Los Angeles, CA");
        search.setCollection(CGAdsMobileCollection.Collection640x100);
        search.setSize(new CGSize(640.0f, 100.0f));

        CGAdsMobileResults results = banner(search);
        assertResults(results, "testFindRestaurantsInLosAngeles");

        CGAdsMobileAd ad = results.getAd();
        CGPlacesDetailLocation detailLocation = ad.placesDetailLocation();
        assertEquals(ad.getLocationId(), detailLocation.getLocationId());
    }

    @Test
    public void testFindRestaurantsInLatLon() {
        CGAdsMobile search = CityGrid.adsMobile();
        search.setWhat("restaurant");
        search.setLatlon(new CGLatLon(33.786594d, -118.298662d));
        search.setRadius(50.0f);
        search.setCollection(CGAdsMobileCollection.Collection640x100);
        search.setSize(new CGSize(640.0f, 100.0f));

        CGAdsMobileResults results = banner(search);
        assertResults(results, "testFindRestaurantsInLosAngeles");
    }
    
    @Test
    public void testOptionalsParameters(){
    	CGAdsMobile search = CityGrid.adsMobile();
        search.setWhat("restaurant");
        search.setWhere("90025");
        search.setCollection(CGAdsMobileCollection.Collection640x100);
        search.setSize(new CGSize(640.0f, 100.0f));
        search.setUa("Chrome");
        search.setRawWhat("cheap restaurant");
        search.setRawWhere("near 90025");
        
        CGAdsMobileResults results = banner(search);
        assertResults(results, "testOptionalsParameters");
    }
}
