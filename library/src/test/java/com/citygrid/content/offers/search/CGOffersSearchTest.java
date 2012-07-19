/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.offers.search;

import com.citygrid.*;
import com.citygrid.content.offers.CGOffersLocation;
import com.citygrid.content.offers.CGOffersOffer;
import com.citygrid.content.offers.CGOffersType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static com.citygrid.CGBuilder.isNotEmpty;
import static org.junit.Assert.*;

public class CGOffersSearchTest extends CGBaseTest {
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
        CGOffersSearch search1 = new CGOffersSearch(null);

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

        search1.setWhat(null);
        search1.setType(CGOffersType.Free);
        try {
            search1.search();
            fail("Search with type, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setType(CGOffersType.Unknown);
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

        search1.setTag(0);
        try {
            search1.search();
            fail("Search with invalid tag, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setTag(CGConstants.INTEGER_UNKNOWN);
        search1.setResultsPerPage(0);
        try {
            search1.search();
            fail("Search with invalid resultsPerPage, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setResultsPerPage(CGConstants.INTEGER_UNKNOWN);
        search1.setPage(0);
        try {
            search1.search();
            fail("Search with invalid page, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

    }

    @Test
    public void testLatLonValidation() {
        CGOffersSearch search1 = new CGOffersSearch(null);
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

        search1.setWhat(null);
        search1.setType(CGOffersType.Buy1Get1);
        try {
            search1.search();
            fail("Search with type, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setType(CGOffersType.Unknown);
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

        search1.setRadius(3.0f);
        try {
            search1.search();
            fail("Search with where, expected 1 error");
        } catch (CGException e) {
            assertEquals(1, e.getErrors().length);
        }

        search1.setTag(0);
        try {
            search1.search();
            fail("Search with invalid tag, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setTag(CGConstants.INTEGER_UNKNOWN);
        search1.setResultsPerPage(0);
        try {
            search1.search();
            fail("Search with invalid resultsPerPage, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setResultsPerPage(CGConstants.INTEGER_UNKNOWN);
        search1.setPage(0);
        try {
            search1.search();
            fail("Search with invalid page, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setPage(CGConstants.INTEGER_UNKNOWN);
        search1.setLatlon(new CGLatLon(181.0d, 181.0d));
        search1.setLatlon2(new CGLatLon(181.0d, 181.0d));

        try {
            search1.search();
            fail("Search with where, expected 5 errors");
        } catch (CGException e) {
            assertEquals(5, e.getErrors().length);
        }

        search1.setLatlon(new CGLatLon(30.0d, 30.0d));
        search1.setLatlon2(null);
        search1.setRadius(CGConstants.FLOAT_UNKNOWN);
        try {
            search1.search();
            fail("Search with where, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setRadius(0.0f);
        try {
            search1.search();
            fail("Search with where, expected 2 errors");
        } catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }
    }

    @Test
    public void testSearchSimulation() {
        CGOffersSearch search = CityGrid.offersSearch();
        search.setWhat("sushi");
        search.setWhere("Los Angeles, CA");
        search.setHistograms(true);

        CGOffersSearchResults results = search(search);
        assertResults(results, "testSearchSimulation");
    }

    protected CGOffersSearchResults search(CGOffersSearch search) {
        CGOffersSearchResults results = null;
        try {
            results = search.search();
        } catch (CGException e) {
            fail("Exception querying for offers search results: " + Arrays.toString(e.getErrors()));
        }
        return results;
    }

    private void assertLocations(CGOffersLocation[] locations, String parent) {
        assertTrue(String.format("Expected to have locations for %s was %s", parent, locations.length), locations.length > 0);
        for (CGOffersLocation location : locations) {
            assertLocation(location, parent, true);
            assertTrue(String.format("Expectd to have tags greater than 0 for %s was %s", parent, location.getTags().length), location.getTags().length > 0);
        }
    }

    private void assertOffers(CGOffersOffer[] offers, String parent) {
        assertTrue(String.format("Expected to have offers for %s", parent), offers.length > 0);
        for (CGOffersOffer offer : offers) {
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
    }

    protected void assertResults(CGOffersSearchResults results, String parent) {
        assertNotNull(String.format("Expected to have results for %s", parent), results);
        assertTrue(String.format("Expected firstHit to be 1 for %s was %s", parent, results.getFirstHit()), results.getFirstHit() == 1);
        assertTrue(String.format("Expected lastHit to be greater than 0 for %s was %s", parent, results.getLastHit()), results.getLastHit() > 0);
        assertTrue(String.format("Expected totalHits to be greater than 0 for %s was %s", parent, results.getTotalHits()), results.getTotalHits() > 0);
        assertNotNull(String.format("Expected to have uri for %s", parent), results.getUri());
        if (results.getUri() != null && results.getUri().toASCIIString().indexOf("lat=") < 0) {
            assertRegions(results.getRegions(), parent);
        }
        if (results.getUri() != null && results.getUri().toASCIIString().indexOf("histograms=true") >= 0) {
            assertHistograms(results.getHistograms(), parent);
        }
        assertOffers(results.getOffers(), parent);

    }
}
