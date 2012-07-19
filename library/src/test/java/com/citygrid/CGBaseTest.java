/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import com.citygrid.content.offers.CGOffersLocation;
import com.citygrid.content.places.detail.CGPlacesDetailAddress;
import org.junit.Ignore;

import static com.citygrid.CGBuilder.isNotEmpty;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Ignore
public class CGBaseTest {
    protected void assertHistograms(CGHistogram[] histograms, String parent) {
        assertTrue(String.format("Expected to have greater than 0 histograms for %s was %s", parent, histograms.length), histograms.length > 0);
        for (CGHistogram histogram : histograms) {
            assertTrue(String.format("Expected name to exist for %s was %s", parent, histogram.getName()), histogram.getName().length() > 0);
            assertTrue(String.format("Expected to have greater than 0 items for %s was %s", parent, histogram.getItems().length), histogram.getItems().length > 0);
            for (CGHistogramItem item : histogram.getItems()) {
                assertTrue(String.format("Exptected item name to exist for %s was %s", parent, item.getName()), item.getName().length() > 0);
                assertTrue(String.format("Expected count to be greater than 0 for %s was %s", parent, item.getCount()), item.getCount() > 0);
                assertNotNull(String.format("Expected uri to exist for %s", parent), item.getUri());
                assertTrue(String.format("Expected tagIds to be greater than 0 for %s was %s", parent, item.getTagIds().length), item.getTagIds().length > 0) ;
            }
        }
    }

    protected void assertRegions(CGRegion regions[], String parent) {
        assertTrue( String.format("Expected regions to be greater than 0 for %s was %s", parent, regions.length), regions.length > 0);
        for (CGRegion region : regions) {
            assertTrue(String.format("Expected type to exist for %s was %s", parent, region.getType()), region.getType().length() > 0);
            assertTrue(String.format("Expected name to exist for %s was %s", parent, region.getName()), region.getName().length() > 0);
            assertTrue(String.format("Expected the latitude to be greater than 30 for %s was %f", parent, region.getLatlon().getLatitude()), region.getLatlon().getLatitude() > 30.0);
            assertTrue(String.format("Expected the longitude to be less than 30 for %s was %f", parent, region.getLatlon().getLongitude()), region.getLatlon().getLongitude() < -30.0);
        }
    }

    protected void assertAddress(CGAddress address, String parent) {
        assertTrue(String.format("Expected street to exist for %s was %s", parent, address.getStreet()), address.getStreet().length() > 0);
        assertTrue(String.format("Expected city to exist for %s was %s", parent, address.getCity()), address.getCity().length() > 0);
        assertTrue(String.format("Expected state to exist for %s was %s", parent, address.getStreet()), address.getState().length() > 0);
        assertTrue(String.format("Expected zip to exist for %s was %s", parent, address.getZip()), address.getZip().length() > 0);
        if (address instanceof CGPlacesDetailAddress) {
            // should have at least one of the two: deliveryPoint, crossStreet
            CGPlacesDetailAddress detailAddress = (CGPlacesDetailAddress) address;
            assertTrue(
                    String.format(
                            "Expected deliveryPoint or crossStreet to exist for %s was %s for deliveryPoint and %s for crossStreet",
                            parent,
                            detailAddress.getDeliveryPoint(),
                            detailAddress.getCrossStreet()),
                    CGBuilder.isNotEmpty(detailAddress.getDeliveryPoint())
                            || CGBuilder.isNotEmpty(detailAddress.getCrossStreet()));
        }
    }

    protected void assertLocation(CGLocation location, String parent, boolean assertAddress) {
        assertNotNull(String.format("Expected to have a location"), location);
        assertTrue(String.format("Expected locationId to exist for %s was %d", parent, location.getLocationId()), location.getLocationId() > 0);
        if (! (location instanceof CGOffersLocation)) {
            assertTrue(String.format("Expected impressionId to exist for %s was %s", parent, location.getImage()), isNotEmpty(location.getImpressionId()));
        }
        assertTrue(String.format("Expected name to exist for %s was %s", parent, location.getName()), isNotEmpty(location.getName()));
        if (assertAddress) {
            assertAddress(location.getAddress(), parent);
        }
    }
}
