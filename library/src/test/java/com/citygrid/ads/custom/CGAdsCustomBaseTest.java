/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.ads.custom;

import com.citygrid.CGBaseTest;
import com.citygrid.CGException;
import org.junit.Ignore;

import java.util.Arrays;

import static com.citygrid.CGBuilder.isNotEmpty;
import static org.junit.Assert.*;

@Ignore
public class CGAdsCustomBaseTest extends CGBaseTest {

    protected void assertAds(CGAdsCustomAd[] ads, String parent) {
        assertTrue(String.format("Expected to have ads for %s", parent), ads.length > 0);
        for (CGAdsCustomAd ad : ads) {
            assertLocation(ad, parent, false);
            assertTrue(String.format("Expected to have adId for %s", parent), ad.getAdId() > 0);
            assertTrue(String.format("Expected to have type for %s", parent), isNotEmpty(ad.getType()));
            assertNotNull(String.format("Expected to have destinationUrl for %s", parent), ad.getDestinationUrl());
            assertNotNull(String.format("Expected to have displayUrl for %s", parent), ad.getDisplayUrl());
            assertNotNull(String.format("Expected to have attributionText for %s", parent),ad.getAttributionText());
        }
    }

    protected void assertResults(CGAdsCustomResults results, String parent) {
        assertNotNull(String.format("Expected to have results for %s", parent), results);
        assertAds(results.getAds(), parent);
    }

    protected CGAdsCustomResults search(CGAdsCustom search) {
        CGAdsCustomResults results = null;
        try {
            results = search.search();
        } catch (CGException e) {
            fail("Exception querying for ads custom search results: " + Arrays.toString(e.getErrors()));
        }
        return results;
    }
}
