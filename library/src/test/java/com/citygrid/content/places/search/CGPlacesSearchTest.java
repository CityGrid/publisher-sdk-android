/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.places.search;

import com.citygrid.*;
import com.citygrid.content.places.detail.CGPlacesDetail;
import com.citygrid.content.places.detail.CGPlacesDetailLocation;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class CGPlacesSearchTest {
    private ObjectMapper mapper = new ObjectMapper();
    private CGPlacesSearch search = new CGPlacesSearch("test");

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
    public void testProcessResults() throws IOException {
        JsonNode rootNode = getTestData();

        CGPlacesSearchResults results = search.processResults(rootNode.get("results"));
        assertNotNull(results);

        assertEquals(1, results.getFirstHit());
        assertNull(results.getDidYouMean());
        assertEquals("http://api.citygridmedia.com/search/places/v2/search/where?has_offers=false&type=movietheater&format=json&page=1&rpp=20&histograms=true&where=90045&publisher=test&region_type=circle",
                results.getUri().toASCIIString());

        assertEquals(1, results.getRegions().length);
        assertEquals("postal_code", results.getRegions()[0].getType());
        assertEquals("90045", results.getRegions()[0].getName());
        assertEquals(new CGLatLon(33.953182d, -118.400197d), results.getRegions()[0].getLatlon());
        assertEquals(3.28, results.getRegions()[0].getRadius(), 0.1);

        assertEquals(12, results.getLocations().length);
        CGPlacesSearchLocation location = results.getLocations()[0];
        assertEquals(11538789, location.getLocationId());
        assertEquals(false, location.isFeatured());
        assertEquals("West LA, Westchester", location.getNeighborhood());
        assertEquals(null, location.getImage());
        assertEquals(null, location.getFax());
        assertEquals(null, location.getWebsite());
        assertEquals(null, location.getOffers());
        assertEquals(null, location.getTagline());
        assertEquals(8, location.getRating());
        assertEquals(33.9781, location.getLatlon().getLatitude(), 0.0001);
        assertEquals(-118.3928, location.getLatlon().getLongitude(), 0.0001);

        assertArrayEquals(
                new String[]{"Movie Theaters", "IMAX Theaters", "Gift Certificate", "Visa", "MasterCard"},
                location.getCategories());

        assertEquals(
                new CGAddress("6081 Center Drive", "Los Angeles", "CA", "90045"),
                location.getAddress());

        assertEquals(5, location.getTags().length);
        assertEquals(new CGTag(157, "Movie Theaters"), location.getTags()[0]);

        assertEquals(5, results.getHistograms().length);
        CGHistogram histogram = results.getHistograms()[0];
        assertEquals("Payment Methods", histogram.getName());
        assertEquals(3, histogram.getItems().length);
        assertEquals(1, histogram.getItems()[0].getCount());
        assertEquals("Gift Certificate", histogram.getItems()[0].getName());
        assertEquals("http://api.citygridmedia.com/search/places/v2/search/where?tag=11352&where=90045&publisher=test&histograms=true",
                histogram.getItems()[0].getUri().toASCIIString());
        assertArrayEquals(new String[]{"11352"}, histogram.getItems()[0].getTagIds());


    }

    private JsonNode getTestData() throws IOException {

        InputStream inputStream = null;
        JsonNode root = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream("citygrid-places-search.json");
            root = mapper.readTree(inputStream);
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return root;
    }

    @Test
    public void testPlacesDetailLocation() throws CGException {
        CGPlacesSearch search = CityGrid.placesSearch();
        search.setType(CGPlacesSearchType.Restaurant);
        search.setWhere("90046");
        search.setHistograms(true);
        CGPlacesSearchResults searchResults = search.search();
        CGPlacesSearchLocation searchLocation = searchResults.getLocations()[0];

        CGPlacesDetail detail = searchLocation.placesDetail();
        assertEquals(searchLocation.getLocationId(), detail.getLocationId());
        assertEquals(searchLocation.getImpressionId(), detail.getImpressionId());
       
        CGPlacesDetailLocation detailLocation1 = detail.detail().getLocation();
        assertNotNull(detailLocation1);

        CGPlacesDetailLocation detailLocation2 = searchLocation.placesDetailLocation();
        assertNotNull(detailLocation2);

        assertEquals(detailLocation1, detailLocation2);
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        CGPlacesSearch original = new CGPlacesSearch("test");
        original.setPlacement("placement");
        original.setPage(1);
        original.setLatlon(new CGLatLon(90d, 90d));
        original.setHistograms(false);
        original.setSort(CGPlacesSearchSort.Distance);
        original.setRadius(1f);

        CGPlacesSearch clone = (CGPlacesSearch) original.clone();

        assertEquals(original, clone);
        assertTrue(original != clone);
    }

    @Test
    public void testWhereValidation() {
        CGPlacesSearch search1 = new CGPlacesSearch(null);
        try {
            search1.search();
            fail("Empty search, expected CGException");
        }
        catch (CGException e) {
            assertEquals(3, e.getErrors().length);
            assertEquals(CGErrorNum.PublisherRequired, e.getErrors()[0].getErrorNumber());
            assertEquals(CGErrorNum.GeographyUnderspecified, e.getErrors()[1].getErrorNumber());
            assertEquals(CGErrorNum.Underspecified, e.getErrors()[2].getErrorNumber());
        }

        search1.setWhat("pizza");
        try {
            search1.search();
            fail("Search with what, expected CGException");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setWhat(null);
        search1.setType(CGPlacesSearchType.Hotel);
        try {
            search1.search();
            fail("Search with type, expected CGException");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setType(CGPlacesSearchType.Unknown);
        search1.setChain(1);
        try {
            search1.search();
            fail("Search with type, expected CGException");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setChain(CGConstants.INTEGER_UNKNOWN);
        search1.setWhat("pizza");
        search1.setPublisher(CGTConstants.CGT_PUBLISHER);
        try {
            search1.search();
            fail("Search with publisher, expected 1 error");
        }
        catch (CGException e) {
            assertEquals(1, e.getErrors().length);
        }

        search1.setWhere("90034");
        search1.setPublisher(null);
        try {
            search1.search();
            fail("Search with where, expected 1 error");
        }
        catch (CGException e) {
            assertEquals(1, e.getErrors().length);
        }

        search1.setTag(0);
        try {
            search1.search();
            fail("Search with invalid tag, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setTag(CGConstants.INTEGER_UNKNOWN);
        search1.setChain(0);
        try {
            search1.search();
            fail("Search with invalid chain, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setResultsPerPage(0);
        search1.setChain(CGConstants.INTEGER_UNKNOWN);
        try {
            search1.search();
            fail("Search with invalid resultsPerPage, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setResultsPerPage(CGConstants.INTEGER_UNKNOWN);
        search1.setPage(0);
        try {
            search1.search();
            fail("Search with invalid page, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }
    }

    @Test
    public void testLatLonValidation() {
       CGPlacesSearch search1 = new CGPlacesSearch(null);
        try {
            search1.search();
            fail("Empty search, expected CGException");
        }
        catch (CGException e) {
            assertEquals(3, e.getErrors().length);
            assertEquals(CGErrorNum.PublisherRequired, e.getErrors()[0].getErrorNumber());
            assertEquals(CGErrorNum.GeographyUnderspecified, e.getErrors()[1].getErrorNumber());
            assertEquals(CGErrorNum.Underspecified, e.getErrors()[2].getErrorNumber());
        }

        search1.setWhat("pizza");
        try {
            search1.search();
            fail("Search with what, expected CGException");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setWhat(null);
        search1.setType(CGPlacesSearchType.Hotel);
        try {
            search1.search();
            fail("Search with type, expected CGException");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setType(CGPlacesSearchType.Unknown);
        search1.setChain(1);
        try {
            search1.search();
            fail("Search with type, expected CGException");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setChain(CGConstants.INTEGER_UNKNOWN);
        search1.setWhat("pizza");
        search1.setPublisher(CGTConstants.CGT_PUBLISHER);
        try {
            search1.search();
            fail("Search with publisher, expected 1 error");
        }
        catch (CGException e) {
            assertEquals(1, e.getErrors().length);
        }

        search1.setLatlon(new CGLatLon(30.0d, 30.0d));
        search1.setPublisher(null);
        try {
            search1.search();
            fail("Search with where, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setRadius(3.0f);
        try {
            search1.search();
            fail("Search with where, expected 1 errors");
        }
        catch (CGException e) {
            assertEquals(1, e.getErrors().length);
        }

        search1.setTag(0);
        try {
            search1.search();
            fail("Search with invalid tag, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setTag(CGConstants.INTEGER_UNKNOWN);
        search1.setChain(0);
        try {
            search1.search();
            fail("Search with invalid chain, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setResultsPerPage(0);
        search1.setChain(CGConstants.INTEGER_UNKNOWN);
        try {
            search1.search();
            fail("Search with invalid resultsPerPage, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setResultsPerPage(CGConstants.INTEGER_UNKNOWN);
        search1.setPage(0);
        try {
            search1.search();
            fail("Search with invalid page, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setPage(CGConstants.INTEGER_UNKNOWN);
        search1.setLatlon(new CGLatLon(181.0d, 181.0d));
        search1.setLatlon2(new CGLatLon(181.0d, 181.0d));
        try {
            search1.search();
            fail("Search with where, expected 5 errors");
        }
        catch (CGException e) {
            assertEquals(5, e.getErrors().length);
        }

        search1.setLatlon(new CGLatLon(30.0d, 30.0d));
        search1.setLatlon2(null);
        search1.setRadius(CGConstants.FLOAT_UNKNOWN);
        try {
            search1.search();
            fail("rch with where, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setRadius(0.0f);
        try {
            search1.search();
            fail("rch with where, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }
    }
    
    @Test
    public void testToString() {
        CGPlacesSearch search = new CGPlacesSearch(CGTConstants.CGT_PUBLISHER);
        search.setType(CGPlacesSearchType.Hotel);
        search.setWhat("WHAT");
        search.setTag(2222);
        search.setChain(3333);
        search.setFirst("z");
        search.setWhere("44444");
        search.setRadius(1.0f);
        search.setPage(5555);
        search.setResultsPerPage(6666);
        search.setSort(CGPlacesSearchSort.Distance);
        search.setPlacement("PLACEMENT");
        search.setOffers(true);
        search.setHistograms(true);
        search.setLatlon(new CGLatLon(2.0d, 3.0d));
        search.setLatlon2(new CGLatLon(4.0d, 5.0d));
        search.setConnectTimeout(10);
        search.setReadTimeout(10);
        search.setImpressionId("IMPRESSION_ID");
        String description = search.toString();
        assertTrue(String.format("Couldn't find type in %s", description), description.indexOf("type=Hotel") >0);
        assertTrue(String.format("Couldn't find what in %s", description), description.indexOf("what=WHAT") >0);
        assertTrue(String.format("Couldn't find tag in %s", description), description.indexOf("tag=2222") >0);
        assertTrue(String.format("Couldn't find chain in %s", description), description.indexOf("chain=3333") >0);
        assertTrue(String.format("Couldn't find z in %s", description), description.indexOf("first=z") >0);
        assertTrue(String.format("Couldn't find where in %s", description), description.indexOf("where=44444") >0);
        assertTrue(String.format("Couldn't find radius in %s", description), description.indexOf("radius=1.0") >0);
        assertTrue(String.format("Couldn't find page in %s", description), description.indexOf("page=5555") >0);
        assertTrue(String.format("Couldn't find resultsPerPage in %s", description), description.indexOf("resultsPerPage=6666") >0);
        assertTrue(String.format("Couldn't find sort in %s", description), description.indexOf("sort=Distance") >0);
        assertTrue(String.format("Couldn't find placement in %s", description), description.indexOf("placement=PLACEMENT") >0);
        assertTrue(String.format("Couldn't find offers in %s", description), description.indexOf("offers=true") >0);
        assertTrue(String.format("Couldn't find histograms in %s", description), description.indexOf("histograms=true") >0);
        assertTrue(String.format("Couldn't find latlon in %s", description), description.indexOf("CGLatLon latitude=2.0,longitude=3.0>") >0);
        assertTrue(String.format("Couldn't find latlon2 in %s", description), description.indexOf("CGLatLon latitude=4.0,longitude=5.0>") >0);
        assertTrue(String.format("Couldn't find connectTimeout in %s", description), description.indexOf("connectTimeout=10") >0);
        assertTrue(String.format("Couldn't find readTimeout in %s", description), description.indexOf("readTimeout=10") >0);
        assertTrue(String.format("Couldn't find impressionId in %s", description), description.indexOf("impressionId=IMPRESSION_ID") >0);
    }
}
