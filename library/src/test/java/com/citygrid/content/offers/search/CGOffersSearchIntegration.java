package com.citygrid.content.offers.search;

import com.citygrid.CGException;
import com.citygrid.CGLatLon;
import com.citygrid.CGTConstants;
import com.citygrid.CityGrid;
import com.citygrid.content.offers.CGOffersOffer;
import com.citygrid.content.offers.detail.CGOffersDetail;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CGOffersSearchIntegration extends CGOffersSearchTest {
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
    public void testFindSushiInLosAngeles() throws CGException {
        CGOffersSearch search = CityGrid.offersSearch();
        search.setWhat("sushi");
        search.setWhere("Los Angeles, CA");
        search.setHistograms(true);

        CGOffersSearchResults results = search(search);

        assertResults(results, "testFindSushiInLosAngeles");

        CGOffersOffer offersOffer = results.getOffers()[results.getOffers().length - 1];
        CGOffersDetail detail = offersOffer.offersDetail();
        int locationId = offersOffer.getLocations()[offersOffer.getLocations().length -1].getLocationId();
        detail.setLocationId(locationId);
        CGOffersOffer detailsOffer = detail.detail().getOffer();
        assertEquals(offersOffer.getOfferId(), detailsOffer.getOfferId());
        assertEquals(offersOffer.getImpressionId(), detailsOffer.getImpressionId());

    }

    @Test
    public void testFindSushiIn90025() {
        CGOffersSearch search = CityGrid.offersSearch();
        search.setWhat("sushi");
        search.setWhere("90025");

        CGOffersSearchResults results = search(search);

        assertResults(results, "testFindSushiIn90025");
    }

    @Test
    public void testFindSushiInLatLon() {
        CGOffersSearch search = CityGrid.offersSearch();
        search.setWhat("sushi");
        search.setRadius(10.0f);

        search.setLatlon(new CGLatLon(34.10652d, -118.411509));

        CGOffersSearchResults results = search(search);

        assertResults(results, "testFindSushiInLatLon");
    }




}
