/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.offers.detail;

import com.citygrid.CGBaseTest;
import com.citygrid.CGException;
import com.citygrid.CGTConstants;
import com.citygrid.CityGrid;
import com.citygrid.content.offers.CGOffersLocation;
import com.citygrid.content.offers.CGOffersOffer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static com.citygrid.CGBuilder.isNotEmpty;
import static org.junit.Assert.*;

public class CGOffersDetailTest extends CGBaseTest {

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
    public void testValidation() {

        CGOffersDetail search1 = new CGOffersDetail(null);

        try {
            search1.detail();
            fail("Empty search, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setOfferId("OFFER_ID");
        try {
            search1.detail();
            fail("Search with what, expected 1 error");
        } catch (CGException e) {
            assertEquals(1, e.getErrors().length);
        }
    }

    @Test
    public void testDetailSimulation() {
        CGOffersDetail search = CityGrid.offersDetail();
        search.setOfferId("cg_61413312");

        CGOffersDetailResults results = detail(search);
        assertResults(results, "testDetailSimulation");
    }

    private void assertLocations(CGOffersLocation[] locations, String parent) {
        assertTrue(String.format("Expected to have locations for %s was %d", parent, locations.length), locations.length > 0);
        for (CGOffersLocation location : locations) {
            assertLocation(location, parent, true);
            assertTrue(String.format("Expected to have tags greater than 0 for %s was %d", parent, location.getTags().length), location.getTags().length > 0);
        }
    }

    private void assertOffer(CGOffersOffer offer, String parent) {
        assertNotNull(String.format("Expected to have offer for %s", parent), offer);
        assertTrue(String.format("Expected to have offerId for %s", parent), isNotEmpty(offer.getOfferId()));
        assertTrue(String.format("Expected to have impressionId for %s", parent), isNotEmpty(offer.getImpressionId()));
        assertTrue(String.format("Expected to have title for %s", parent), isNotEmpty(offer.getTitle()));
        assertTrue(String.format("Expected to have offerDescription for %s", parent), isNotEmpty(offer.getOfferDescription()));
        assertTrue(String.format("Expected to have a redemptionTypeId greater than 0 for %s was %d", parent, offer.getRedemptionTypeId()), offer.getRedemptionTypeId() > 0);
        assertTrue(String.format("Expected to have redemptionType for %s", parent), isNotEmpty(offer.getRedemptionType()));
        assertTrue(String.format("Expected to have terms for %s", parent), isNotEmpty(offer.getTerms()));
        assertTrue(String.format("Expected to have types for %s", parent), offer.getTypes().length > 0);
        assertNotNull(String.format("Expected to have startDate for %s", parent), offer.getStartDate());
        assertTrue(String.format("Expected to have attributionSource for %s", parent), isNotEmpty(offer.getAttributionSource()));
        assertLocations(offer.getLocations(), parent);
    }

    void assertResults(CGOffersDetailResults results, String parent) {
        assertNotNull(String.format("Expected to have results for %s", parent), results);
        assertOffer(results.getOffer(), parent);
    }

    CGOffersDetailResults detail(CGOffersDetail search) {
        CGOffersDetailResults results = null;
        try {
            results = search.detail();
        } catch (CGException e) {
            fail("Exception querying for offers detail results: " + Arrays.toString(e.getErrors()));
        }
        return results;
    }


}
