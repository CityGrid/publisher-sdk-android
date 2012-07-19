/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.offers.detail;

import com.citygrid.CGTConstants;
import com.citygrid.CityGrid;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CGOffersDetailIntegration extends CGOffersDetailTest {
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
    public void testOffer() {
        CGOffersDetail builder = CityGrid.offersDetail();
        builder.setOfferId("cg_61413312");

        CGOffersDetailResults results = detail(builder);

        assertResults(results, "testFindSushiIn90025");
    }

    @Test
    public void testOfferLocationId() {
        CGOffersDetail builder = CityGrid.offersDetail();
        builder.setOfferId("cg_61413312");
        builder.setLocationId(11579059);

        CGOffersDetailResults results = detail(builder);
        assertResults(results, "testFindSushiIn90025");
    }
}
