/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.ads.custom;

import com.citygrid.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CGAdsCustomTest extends CGAdsCustomBaseTest {
    @BeforeClass
    public static void beforeClass() {
        CityGrid.setPublisher(CGTConstants.CGT_PUBLISHER);
        CityGrid.setSimulation(true);
    }

    @AfterClass
    public static void afterClass() {
        CityGrid.setPublisher(null);
    }

    @Test
    public void testSearch() {
    	CGAdsCustom search = CityGrid.adsCustom();
        search.setWhat("restaurant");
        search.setWhere("Los Angeles, CA");

        CGAdsCustomResults results = search(search);
        assertResults(results, "testSearch");
    }

    @Test
    public void testWhereValidation() throws CGException {
        CGAdsCustom search1 = new CGAdsCustom(null);

        try {
            search1.search();
            fail("Empty search, expected 3 errors");
        } catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }

        search1.setWhat("pizza");
        try {
            search1.search();
            fail("Search with what, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }
        
        search1.setWhat("pizza sandwich italian milano");
        try {
            search1.search();
            fail("Search with multiple what terms, expected 3 errors");
        } catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }

        search1.setWhat(null);
        search1.setWhat("pizza");
        search1.setPublisher(CGTConstants.CGT_PUBLISHER);
        try {
            search1.search();
            fail("Search with publisher, expected 1 error");
        } catch (CGException e) {
            assertEquals(1, e.getErrors().length);
        }

        search1.setPublisher(null);
        search1.setWhere("90034");
        try {
            search1.search();
            fail("Search with where, expected 1 error");
        } catch (CGException e) {
            assertEquals(1, e.getErrors().length);
        }

        search1.setMax(0);
        try {
            search1.search();
            fail("Search with max < 1, expected 2 error");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setMax(11);
        try {
            search1.search();
            fail("Search with max > 11, expected 2 error");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }
    }

    @Test
    public void testLatLonValidation() throws CGException {
        CGAdsCustom search1 = new CGAdsCustom(null);

        try {
            search1.search();
            fail("Empty search, expected 3 errors");
        } catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }

        search1.setWhat("pizza");
        try {
            search1.search();
            fail("Search with what, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setWhat("pizza");
        search1.setPublisher(CGTConstants.CGT_PUBLISHER);
        try {
            search1.search();
            fail("Search with publisher, expected 1 error");
        } catch (CGException e) {
            assertEquals(1, e.getErrors().length);
        }

        search1.setPublisher(null);
        search1.setLatlon(new CGLatLon(30.0d, 30.0d));
        try {
            search1.search();
            fail("Search with where, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setRadius(3);
        try {
            search1.search();
            fail("Search with radius, expected 1 error");
        } catch (CGException e) {
            assertEquals(1, e.getErrors().length);
        }

        search1.setLatlon(new CGLatLon(181.0d, 181.0d));
        try {
            search1.search();
            fail("Search with latlon > 180, expected 3 errors");
        } catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }

        search1.setLatlon(new CGLatLon(30.0d, 30.0d));
        search1.setRadius(CGConstants.FLOAT_UNKNOWN);
        try {
            search1.search();
            fail("Search with no radius, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setRadius(0);
        try {
            search1.search();
            fail("Search with radius(0.0, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }
    }

}
