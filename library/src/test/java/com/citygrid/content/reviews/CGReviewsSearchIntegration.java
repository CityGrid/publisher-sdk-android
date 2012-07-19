/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.reviews;

import com.citygrid.*;
import com.citygrid.content.places.detail.CGPlacesDetailLocation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.citygrid.CGBuilder.isNotEmpty;
import static org.junit.Assert.*;

public class CGReviewsSearchIntegration extends CGBaseTest {
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
        CGReviewsSearch search = CityGrid.reviewsSearch();
        search.setWhere("los angeles, ca");
        search.setWhat("sushi");
        
        CGReviewsSearchResults results = search.search();

        assertResults(results, "testFindSushiInLosAngeles");

        CGReviewsSearchReview review = results.getReviews()[0];
        CGPlacesDetailLocation location = review.placesDetailLocation();
        assertEquals(review.getLocationId(), location.getLocationId());
    }
    
    @Test public void testFindSushiIn90025() throws CGException {
        CGReviewsSearch search = CityGrid.reviewsSearch();
        search.setWhere("90025");
        search.setWhat("sushi");
        
        CGReviewsSearchResults results = search.search();

        assertResults(results, "testFindSushiIn90025");
    }
    
    @Test public void testFindSushiLatLon() throws CGException {
        CGReviewsSearch search = CityGrid.reviewsSearch();
        search.setLatlon(new CGLatLon(34.10652d, -118.411509d));
        search.setWhat("sushi");
        search.setRadius(10.0f);
        
        CGReviewsSearchResults results = search.search();

        assertResults(results, "testFindSushiLatLon");
    }
    
    @Test public void testFindSushiLatLonSortedDate() throws CGException {
        CGReviewsSearch search = CityGrid.reviewsSearch();
        search.setLatlon(new CGLatLon(34.10652d, -118.411509d));
        search.setWhat("sushi");
        search.setRadius(10.0f);
        search.setSort(CGReviewsSearchSort.CreateDate);
        
        CGReviewsSearchResults results = search.search();

        assertResults(results, "testFindSushiLatLonSortedDate");
    }
    
    @Test public void testFindSushiLatLonSortedRating() throws CGException {
        CGReviewsSearch search = CityGrid.reviewsSearch();
        search.setLatlon(new CGLatLon(34.10652d, -118.411509d));
        search.setWhat("sushi");
        search.setRadius(10.0f);
        search.setSort(CGReviewsSearchSort.ReviewRating);
        
        CGReviewsSearchResults results = search.search();

        assertResults(results, "testFindSushiLatLonSortedRating");
    }
    
    private void assertReview(CGReviewsSearchReview review, String parent) {
        assertNotNull(String.format("Expected to have review for %s", parent), review);
        assertTrue(String.format("Expected to have reviewId for %s", parent), isNotEmpty(review.getReviewId()));
        assertNotNull(String.format("Expected to have url for %s", parent), review.getUrl());
        assertTrue(String.format("Expected to have title for %s", parent), isNotEmpty(review.getTitle()));
        assertTrue(String.format("Expected to have author for %s", parent), isNotEmpty(review.getAuthor()));
        assertTrue(String.format("Expected to have text for %s", parent), isNotEmpty(review.getText()));
        assertNotNull(String.format("Expected to have date for %s", parent), review.getDate());
        assertTrue(
                String.format("Expected to have a rating for %s was %d", parent, review.getRating()),
                review.getRating() != CGConstants.INTEGER_UNKNOWN);
        assertTrue(String.format("Expected to have attributionText for %s", parent), isNotEmpty(review.getAttributionText()));
    }
    
    private void assertReviews(CGReviewsSearchReview[] reviews, String parent) {
        assertTrue(String.format("Expected to have reviews for %s", parent), reviews.length > 0);
        for (CGReviewsSearchReview review : reviews) {
            assertReview(review, parent);
            
            assertTrue(String.format("Expected to have impressionId for %s", parent), isNotEmpty(review.getImpressionId()));
            assertTrue(
                    String.format("Expected to have a referenceId for %s was %d", parent,  review.getReferenceId()),
                    review.getReferenceId() != CGConstants.INTEGER_UNKNOWN);
            // TODO some review does not have business name, e.g. cg_16182361 and cg_16182371
            //assertTrue(String.format("Expected to have businessName for %s", parent), isNotEmpty(review.getBusinessName()));

            assertTrue(String.format("Expected to have locationId for %s", parent), review.getLocationId() != CGConstants.INTEGER_UNKNOWN);

            //TODO some reviews come back without type therefore type was set to Unknown.
            //assertTrue(String.format("Expected to have a type for %s was %s", parent, review.getType()), review.getType() != CGReviewType.Unknown);
        }
    }
    
    private void assertResults(CGReviewsSearchResults results,String parent) {
        assertNotNull(String.format("Expected to have results for %s", parent), results);
        assertTrue(String.format("Expected firstHit to be 1 for %s was %d", parent, results.getFirstHit()), results.getFirstHit() == 1);
        assertTrue(String.format("Expected lastHit to be greater than 0 for %s was %d", parent, results.getLastHit()), results.getLastHit() > 0);
        assertTrue(String.format("Expected totalHits to be greater than 0 for %s was %d", parent, results.getTotalHits()), results.getTotalHits() > 0);
        assertNotNull(String.format("Expected to have uri for %s", parent), results.getUri());

        if (results.getUri() != null && results.getUri().toASCIIString().indexOf("lat=") <0) {
            assertRegions(results.getRegions(), parent);
        }

        if (results.getUri() != null && results.getUri().toASCIIString().indexOf("histograms=true") > 0) {
            assertHistograms(results.getHistograms(),parent);
        }
        assertReviews(results.getReviews(), parent);
    }
}
