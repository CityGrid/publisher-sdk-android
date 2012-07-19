/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.ads.tracker;

import com.citygrid.CGConstants;
import com.citygrid.CGException;
import com.citygrid.CGTConstants;
import com.citygrid.CityGrid;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CGAdsTrackerTest {
    @BeforeClass
    public static void beforeClass() {
        CityGrid.setPublisher(CGTConstants.CGT_PUBLISHER);
        CityGrid.setSimulation(true);
        CityGrid.setMuid(CGTConstants.PLACEHOLDER_MUID);
        CityGrid.setMobileType(CGTConstants.PLACEHOLDER_MOBILE_MODEL);
    }

    @AfterClass
    public static void afterClass() {
        CityGrid.setPublisher(null);
        CityGrid.setMuid(null);
        CityGrid.setMuid(null);
        CityGrid.setMobileType(null);
    }

    @Test
    public void testValidation() {
        CGAdsTracker tracker =  new CGAdsTracker(null);

        try {
            tracker.track();
            fail("Empty track, expected 4 errors");
        } catch (CGException e) {
            assertEquals(4, e.getErrors().length);
        }

        tracker.setPublisher(CGTConstants.CGT_PUBLISHER);
        try {
            tracker.track();
            fail("Added publisher, expected 3 errors");
        } catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }
        tracker.setPublisher(null);
        tracker.setLocationId(1111);
        try {
            tracker.track();
            fail("Added locationId, expected 3 errors");
        } catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }
        tracker.setLocationId(CGConstants.INTEGER_UNKNOWN);
        tracker.setReferenceId(2222);
        try {
            tracker.track();
            fail("Added referenceId, expected 3 errors");
        } catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }
        tracker.setReferenceId(CGConstants.INTEGER_UNKNOWN);
        tracker.setActionTarget(CGAdsTrackerActionTarget.WriteReview);
        try {
            tracker.track();
            fail("Added actionTarget, expected 3 errors");
        } catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }
        tracker.setActionTarget(CGAdsTrackerActionTarget.ClickToCall);
        try {
            tracker.track();
            fail("Added actionTarget as click to call, expected 4 errors");
        } catch (CGException e) {
            assertEquals(4, e.getErrors().length);
        }
        tracker.setDialPhone("3333");
        try {
            tracker.track();
            fail("Added actionTarget as click to call with dialPhone, expected 3 errors");
        } catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }

    }
    @Test
    public void testTrackerSimulation() {
        CGAdsTracker tracker = CityGrid.adsTracker();

        tracker.setLocationId(886038);
        tracker.setReferenceId(2);
        tracker.setImpressionId("123");
        tracker.setActionTarget(CGAdsTrackerActionTarget.ListingProfile);

        try {                                                                                               
            tracker.track();
        }
        catch (CGException e) {
            fail("Ads tracker failed with errors:" + Arrays.toString(e.getErrors()));
        }
    }
}
