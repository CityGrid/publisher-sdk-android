/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.ads.custom;

import com.citygrid.CGException;
import com.citygrid.CGLatLon;
import com.citygrid.CGTConstants;
import com.citygrid.CityGrid;
import com.citygrid.content.places.detail.CGPlacesDetailLocation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CGAdsCustomIntegration extends CGAdsCustomBaseTest {
    @BeforeClass
    public static void beforeClass() {
        CityGrid.setPublisher(CGTConstants.CGT_PUBLISHER);
        CityGrid.setSimulation(false);
    } 

    @AfterClass
    public static void afterClass() {
        CityGrid.setPublisher(null);
        CityGrid.setSimulation(true);
    }

    @Test
    public void testFindRestaurantsInLosAngeles() throws CGException {
        CGAdsCustom search = CityGrid.adsCustom();
        search.setWhat("restaurant");
        search.setWhere("Los Angeles, CA");

        CGAdsCustomResults results = search(search);
        assertResults(results, "testFindRestaurantsInLosAngeles");

        CGAdsCustomAd ad = results.getAd();
        CGPlacesDetailLocation detailLocation = ad.placesDetailLocation();
        assertEquals(ad.getLocationId(), detailLocation.getLocationId());
    }

    @Test
    public void testFindRestaurantsIn90069() {
        CGAdsCustom search = CityGrid.adsCustom();
        search.setWhat("restaurant");
        search.setWhere("90069");

        CGAdsCustomResults results = search(search);
        assertResults(results, "testFindRestaurantsIn90069");
    }

    @Test
    public void testFind5SpasIn90069() {
        CGAdsCustom search = CityGrid.adsCustom();
        search.setWhat("spa");
        search.setWhere("90069");
        search.setMax(5);

        CGAdsCustomResults results = search(search);
        assertResults(results, "testFind5SpasIn90069");

        assertTrue(
                String.format("Expected to have less than 5 spas in 90069 had %d", results.getAds().length),
                results.getAds().length <= 5);
    }

    @Test
    public void testFind10BarsIn90004() {
        CGAdsCustom search = CityGrid.adsCustom();
        search.setWhat("pet");
        search.setWhere("90004");

        CGAdsCustomResults results = search(search);
        assertResults(results, "testFind10BarsIn90004");
    }

    @Test
    public void testLatLon() {
        CGAdsCustom search = CityGrid.adsCustom();
        search.setWhat("restaurant");
        search.setLatlon(new CGLatLon(34.0522222d, -118.2427778d));
        search.setRadius(20.0f);

        CGAdsCustomResults results = search(search);
        assertResults(results, "testFind10BarsIn90004");
    }
    
    @Test
    public void testOptionalsParamaters(){
    	CGAdsCustom search = CityGrid.adsCustom();
        search.setWhat("restaurant");
        search.setWhere("90069");
        search.setMax(10);
        search.setRawWhat("cheap restaurant");
        search.setRawWhere("near 90069");
        search.setUa("Chrome");
        search.setServeUrl("citysearch.com");
        CGAdsCustomResults results = search(search);
        
        assertResults(results, "testOptionalsParamaters");
    }
    
}
