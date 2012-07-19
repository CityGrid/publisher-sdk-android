package com.citygrid.content.places.search;

import com.citygrid.*;
import com.citygrid.content.places.detail.CGPlacesDetailLocation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class CGPlacesSearchIntegration extends CGBaseTest {
    private static Logger logger = Logger.getLogger(CGPlacesSearchIntegration.class.getName());
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
    public void testFindMovieTheatersInZipCode90045() {
        CGPlacesSearch search = CityGrid.placesSearch();
        search.setType(CGPlacesSearchType.MovieTheater);
        search.setWhere("90045");
        search.setHistograms(true);

        CGPlacesSearchResults results = search(search);

        assertNotNull(results);
        assertResults(results, "testFindMovieTheatersInZipCode90045");
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Got CGPlacesSearchResults: " + results.toString());
        }
    }
    
    @Test
    public void testFindItalianRestaurantsInChicago() throws CGException {
        CGPlacesSearch search = CityGrid.placesSearch();;
        search.setWhat("restaurant");
        search.setTag(11279);
        search.setWhere("chicago,IL");
        search.setPlacement("sec-5");

        CGPlacesSearchResults results = search(search);
        
        assertNotNull(results);
        assertResults(results, "testFindItalianRestaurantsInChicago");

        CGPlacesSearchLocation searchLocation = results.getLocations()[0];
        // now get its detail
        CGPlacesDetailLocation detailLocation = searchLocation.placesDetailLocation();
        assertEquals(searchLocation.getLocationId(), detailLocation.getLocationId());
        assertEquals(searchLocation.getImpressionId(), detailLocation.getImpressionId());
    }
    
    @Test
    public void testFindHotelsInBostonViewingResultsAlphabetical() {
        CGPlacesSearch search = CityGrid.placesSearch();;
        search.setWhat("hotels");
        search.setWhere("boston,ma");
        search.setPage(1);
        search.setResultsPerPage(5);
        search.setSort(CGPlacesSearchSort.Alphabetical);

        CGPlacesSearchResults results = search(search);
        
        assertNotNull(results);
        assertResults(results, "testFindHotelsInBostonViewingResultsAlphabetical");
    }
    
    @Test
    public void testFindPharmaciesNearLACountyMusicCenter() {
        CGPlacesSearch search = CityGrid.placesSearch();;
        search.setWhat("pharmacy");
        search.setWhere("135 N Grand,Los Angeles,ca");
        search.setSort(CGPlacesSearchSort.Distance);

        CGPlacesSearchResults results = search(search);

        assertNotNull(results);
        assertResults(results, "testFindPharmaciesNearLACountyMusicCenter");
    }
    
    @Test
    public void testFindMovieTheatersRadius() {
        CGPlacesSearch search = CityGrid.placesSearch();
        search.setType(CGPlacesSearchType.MovieTheater);
        search.setLatlon(new CGLatLon(34.03d, -118.28d));
        search.setRadius(5.0f);

        CGPlacesSearchResults results = search(search);
        
        assertNotNull(results);
        assertResults(results, "testFindMovieTheatersRadius");
    }

    @Test
    public void testFindMovieTheatersBox() {
        CGPlacesSearch search = CityGrid.placesSearch();;
        search.setType(CGPlacesSearchType.MovieTheater);
        search.setLatlon(new CGLatLon(34.03d, -118.28d));
        search.setLatlon2(new CGLatLon(34.08d, -118.23d));

        CGPlacesSearchResults results = search(search);

        assertNotNull(results);
        assertResults(results, "testFindMovieTheatersRadius");
    }

    private CGPlacesSearchResults search(CGPlacesSearch search) {
        CGPlacesSearchResults results = null;
        try {
            results = search.search();
        } catch (CGException e) {
            fail("Exception querying for place search results: " + Arrays.toString(e.getErrors()));
        }
        return results;
    }

    private void assertLocation(CGPlacesSearchLocation location, String parent, boolean assertAddress) {
        assertNotNull(String.format("Expected to have a location"), location);
        assertTrue(String.format("Expected locationId to exist for %s was %s", parent, location.getLocationId()), location.getLocationId() > 0);
        assertTrue(String.format("Expected name to exist for %s was %s", parent, location.getName()), location.getName().length() > 0);
        if (assertAddress) {
            assertAddress(location.getAddress(), parent);
        }
    }
    
    private void assertLocations(CGPlacesSearchLocation[] locations, String parent) {
        assertTrue(String.format("Expected to have locations for %s was %s", parent, locations.length), locations.length > 0);
        for (CGPlacesSearchLocation location : locations) {
            assertLocation(location, parent, true);
            assertNotNull("Expected to have a profile", location.getProfile());
            assertTrue(String.format("Expected to have categories greater than 0 for %s was %s", parent, location.getCategories().length), location.getCategories().length > 0);
            assertTrue(String.format("Expectd to have tags greater than 0 for %s was %s", parent, location.getTags().length), location.getTags().length > 0);
        }
    }

    private void assertResults(CGPlacesSearchResults results, String parent) {
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
        assertLocations(results.getLocations(), parent);
    }


}
