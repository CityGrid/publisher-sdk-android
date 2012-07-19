/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.ads.mobile;

import com.citygrid.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static com.citygrid.CGBuilder.isNotEmpty;
import static org.junit.Assert.*;

public class CGAdsMobileTest extends CGBaseTest {
    @BeforeClass
    public static void beforeClass() {
        CityGrid.setPublisher(CGTConstants.CGT_PUBLISHER);
        CityGrid.setSimulation(false);
        CityGrid.setMuid(CGTConstants.PLACEHOLDER_MUID);
    }

    @AfterClass
    public static void afterClass() {
        CityGrid.setPublisher(null);
        CityGrid.setMuid(null);
    }

    @Test
    public void testValidation() {
        CGAdsMobile search1 = new CGAdsMobile(null);

        try {
            search1.banner();
            fail("Empty search, expected 5 errors");
        } catch (CGException e) {
            assertEquals(5, e.getErrors().length);
        }
        search1.setWhat("pizza");
        try {
            search1.banner();
            fail("Search with what, expected 4 errors");
        } catch (CGException e) {
            assertEquals(4, e.getErrors().length);
        }
        search1.setWhat("pizza sandwich italian milano");
        try {
            search1.banner();
            fail("Search with multiple what terms (more than 3), expected 5 errors");
        } catch (CGException e) {
            assertEquals(5, e.getErrors().length);
        }
        search1.setWhat("pizza");
        search1.setPublisher(CGTConstants.CGT_PUBLISHER);
        try {
            search1.banner();
            fail("Search with publisher, expected 3 error");
        } catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }
        search1.setPublisher(null);
        search1.setWhere("90034");
        try {
            search1.banner();
            fail("Search with where, expected 3 error");
        } catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }
        search1.setMax(0);
        try {
            search1.banner();
            fail("Search with max < 1, expected 4 error");
        } catch (CGException e) {
            assertEquals(4, e.getErrors().length);
        }
        search1.setMax(11);
        try {
            search1.banner();
            fail("Search with max > 11, expected 4 error");
        } catch (CGException e) {
            assertEquals(4, e.getErrors().length);
        }
        search1.setWhere(null);
        search1.setLatlon(new CGLatLon(30.0d, 30.0d));
        try {
            search1.banner();
            fail("Search with latlon, expected 5 errors");
        } catch (CGException e) {
            assertEquals(5, e.getErrors().length);
        }
        search1.setRadius(3.0f);
        try {
            search1.banner();
            fail("Search with radius, expected 4 errors");
        } catch (CGException e) {
            assertEquals(4, e.getErrors().length);
        }
        search1.setLatlon(new CGLatLon(181.0d, 181.0d));
        try {
            search1.banner();
            fail("Search with latlon > 180, expected 6 errors");
        } catch (CGException e) {
            assertEquals(6, e.getErrors().length);
        }
        search1.setLatlon(new CGLatLon(30.0d, 30.0d));
        search1.setRadius(CGConstants.FLOAT_UNKNOWN);
        try {
            search1.banner();
            fail("Search with no radius, expected 5 errors");
        } catch (CGException e) {
            assertEquals(5, e.getErrors().length);
        }
        search1.setRadius(0.0f);
        try {
            search1.banner();
            fail("Search with radius(0.0, expected 5 errors");
        } catch (CGException e) {
            assertEquals(5, e.getErrors().length);
        }
    }

    protected void assertAds(CGAdsMobileAd[] ads, String parent) {
        assertTrue(String.format("Expected to have ads for %s", parent), ads.length > 0);
        for (CGAdsMobileAd ad : ads) {
            // TODO Some data come back without locationId and adId.  Comment out for now.
            // assertTrue(String.format("Expected locationId to exist for %s was %d", parent, ad.getLocationId()), ad.getLocationId() > 0);
            // assertTrue(String.format("Expected to have adId for %s", parent), ad.getAdId() > 0);
            assertTrue(String.format("Expected impressionId to exist for %s was %s", parent, ad.getImpressionId()), isNotEmpty(ad.getImpressionId()));
            assertNotNull(String.format("Expected to have destinationurl for %s", parent), ad.getDestinationUrl());
            assertNotNull(String.format("Expected to have image for %s", parent), ad.getImage());
        }
    }

    protected void assertResults(CGAdsMobileResults results, String parent) {
        assertNotNull(String.format("Expected to have results for %s", parent), results);
        assertAds(results.getAds(), parent);
    }

    protected CGAdsMobileResults banner(CGAdsMobile search) {
        CGAdsMobileResults results = null;
        try {
            results = search.banner();
        } catch (CGException e) {
            fail("Exception querying for ads custom search results: " + Arrays.toString(e.getErrors()));
        }
        return results;
    }

    @Test
    public void testSearch() {
        CGAdsMobile search = CityGrid.adsMobile();
        search.setWhat("restaurant");
        search.setWhere("Los Angeles, CA");
        search.setCollection(CGAdsMobileCollection.Collection640x100);
        search.setSize(new CGSize(640.0f, 100.0f));

        CGAdsMobileResults results = banner(search);
        assertResults(results, "testSearch");

    }
    
    @Test
    public void testMultipleSearch() {
        CGAdsMobile search = CityGrid.adsMobile();
        search.setWhat("restaurant pizza italian");
        search.setWhere("Los Angeles, CA");
        search.setCollection(CGAdsMobileCollection.Collection640x100);
        search.setSize(new CGSize(640.0f, 100.0f));

        CGAdsMobileResults results = banner(search);
        assertResults(results, "testMultipleSearch");

    }

}
