package com.citygrid.content.places.detail;

import com.citygrid.CGBaseTest;
import com.citygrid.CGException;
import com.citygrid.CGTConstants;
import com.citygrid.CityGrid;
import com.citygrid.ads.tracker.CGAdsTrackerActionTarget;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CGPlacesDetailIntegration extends CGBaseTest {
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
    public void testListingId295889() {
        CGPlacesDetail builder = CityGrid.placesDetail();
        builder.setLocationId(295889);
        builder.setPlacement("search_page");

        CGPlacesDetailResults results = null;
        try {
            results = builder.detail();
        } catch (CGException e) {
            fail("Exception during querying for places detail: " + Arrays.toString(e.getErrors()));
        }

        assertResults(results, "testListingId295889");

        // do an Ad tracking pretending
        CGPlacesDetailLocation location = results.getLocation();
        try {
            location.track(
                    CGTConstants.PLACEHOLDER_MUID,
                    CGTConstants.PLACEHOLDER_MOBILE_MODEL,
                    CGAdsTrackerActionTarget.ClickToCall);
        } catch (CGException e) {
            e.printStackTrace();
            fail("Exception doing Ad tracking from CGPlacesDetailLocation with locationId: "
                    + location.getLocationId()
                    + " because of " + e.getMessage());
        }
    }

    @Test
    public void testInfoUsaId() {
        CGPlacesDetail builder = CityGrid.placesDetail();
        builder.setInfoUsaId(382016681);

        CGPlacesDetailResults results = null;
        try {
            results = builder.detail();
        } catch (CGException e) {
            fail("Exception during querying for places detail: " + Arrays.toString(e.getErrors()));
        }

        assertResults(results, "testListingId295889");
    }

    @Test
    public void testPublicId() {
        CGPlacesDetail builder = CityGrid.placesDetail();
        
        if(CityGrid.getSimulation()) {
        	builder.setPublicId("yankee_doodles-santa_monica-2");
        }
        else
        {
        	builder.setPublicId("hr-block-san-antonio-70");
        }

        CGPlacesDetailResults results = null;
        try {
            results = builder.detail();
            assertNotNull(results.getLocation().getPublicId());
        } catch (CGException e) {
            fail("Exception during querying for places detail: " + Arrays.toString(e.getErrors()));
        }

        assertResults(results, "testPublicId");
    }
    
    @Test
    public void testId() {
        CGPlacesDetail builder = CityGrid.placesDetail();
               
        if(!CityGrid.getSimulation())
        {
        	builder.setId("hr-block-san-antonio-70");
        	//builder.setIdType(CGPlacesDetailType.CityGridPublic);
        	builder.setIdType("cg");
        	
        	CGPlacesDetailResults results = null;
            try {
                results = builder.detail();
                assertNotNull(results.getLocation().getPublicId());
            } catch (CGException e) {
                fail("Exception during querying for places detail: " + Arrays.toString(e.getErrors()));
            }
      
            assertResults(results, "testId");
        }
 
    }
    
    @Test
    public void testPhone3232569617() {
        CGPlacesDetail builder = CityGrid.placesDetail();
        builder.setPhone("3232569617");

        CGPlacesDetailResults results = null;
        try {
            results = builder.detail();
        } catch (CGException e) {
            fail("Exception during querying for places detail: " + Arrays.toString(e.getErrors()));
        }
        
        assertResults(results, "testPhone3232569617");

    }

    @Test
    public void testPhone3232569617AllResults() {
        CGPlacesDetail builder = CityGrid.placesDetail();
        builder.setPhone("3232569617");
        builder.setPlacement("search_page");
        builder.setAllResults(true);

        CGPlacesDetailResults results = null;
        try {
            results = builder.detail();
        } catch (CGException e) {
            fail("Exception during querying for places detail: " + Arrays.toString(e.getErrors()));
        }

        assertResults(results, "testPhone3232569617AllResults");
    }

    @Test
    public void testPhone3232569617ReviewCount() {
        CGPlacesDetail builder = CityGrid.placesDetail();
        builder.setPhone("3232569617");
        builder.setPlacement("search_page");
        builder.setReviewCount(10);

        CGPlacesDetailResults results = null;
        try {
            results = builder.detail();
        } catch (CGException e) {
            fail("Exception during querying for places detail: " + Arrays.toString(e.getErrors()));
        }

        assertResults(results, "testPhone3232569617ReviewCount");
    }

    private void assertResults(CGPlacesDetailResults results, String parent) {
        assertNotNull(results);
        assertTrue(String.format("Expected to have locations for %s", parent), results.getLocations().length > 0);
        for (CGPlacesDetailLocation location : results.getLocations()) {
            assertDetailLocation(location, parent);
        }
    }

    private void assertDetailLocation(CGPlacesDetailLocation location, String parent) {
        assertLocation(location, parent, true);

        assertTrue(String.format("Expected referenceId to not be 0 for %s was %s", parent, location.getReferenceId()), location.getReferenceId() != 0);
        assertTrue(String.format("Expected to have markets for %s was %s", parent, location.getMarkets().length), location.getMarkets().length > 0);
        assertTrue(String.format("Expected to have neighborhoods for %s was %s", parent, location.getName().length()), location.getNeighborhoods().length > 0);
        assertNotNull(String.format("Expected to have urls for %s", parent), location.getUrls());
        assertNotNull(String.format("Expected to have customerContent for %s", parent), location.getCustomerContent());
        assertTrue(String.format("Expected to have categories for %s was %s", parent, location.getCategories().length), location.getCategories().length > 0);
    }

    private void assertLocation(CGPlacesDetailLocation location, String parent, boolean assertAddress) {
        assertNotNull(String.format("Expected to have a location"), location);
        assertTrue(String.format("Expected locationId to exist for %s was %s", parent, location.getLocationId()), location.getLocationId() > 0);
        assertTrue(String.format("Expected name to exist for %s was %s", parent, location.getName()), location.getName().length() > 0);
        if (assertAddress) {
            assertAddress(location.getAddress(), parent);
        }
    }


}
