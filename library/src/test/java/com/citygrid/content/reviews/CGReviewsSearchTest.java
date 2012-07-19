/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.reviews;

import com.citygrid.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CGReviewsSearchTest extends CGBaseTest {

    @Test
    public void testWhereValidation() {
        CGReviewsSearch search1 = new CGReviewsSearch(null);
        try {
            search1.search();
            fail("Empty search, expected 3 errors");
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
            fail("Search with what, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setWhat(null);
        search1.setType(CGReviewType.EditorialReview);
        try {
            search1.search();
            fail("Search with type, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setType(CGReviewType.Unknown);
        search1.setWhat("pizza");
        search1.setPublisher(CGTConstants.CGT_PUBLISHER);
        try {
            search1.search();
            fail("Search with publisher, expected 1 error");
        }
        catch (CGException e) {
            assertEquals(1, e.getErrors().length);
        }

        search1.setPublisher(null);
        search1.setWhere("90034");
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
        search1.setResultsPerPage(0);
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
        search1.setRating(11);
        try {
            search1.search();
            fail("Search with invalid rating, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }


        search1.setRating(CGConstants.INTEGER_UNKNOWN);
        search1.setDays(0);
        try {
            search1.search();
            fail("Search with invalid days, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }
    }

    @Test
    public void testLatLonValidation() {
       CGReviewsSearch search1 = new CGReviewsSearch(null);
        try {
            search1.search();
            fail("Empty search, expected 3 errors");
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
            fail("Search with what, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setWhat(null);
        search1.setType(CGReviewType.EditorialReview);
        try {
            search1.search();
            fail("Search with type, expected 2 errors");
        }
        catch (CGException e) {
            assertEquals(2, e.getErrors().length);
        }

        search1.setType(CGReviewType.Unknown);
        search1.setWhat("pizza");
        search1.setPublisher(CGTConstants.CGT_PUBLISHER);
        try {
            search1.search();
            fail("Search with publisher, expected 1 error");
        }
        catch (CGException e) {
            assertEquals(1, e.getErrors().length);
        }

        search1.setPublisher(null);
        search1.setLatlon(new CGLatLon(30.0d, 30.0d));
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
        search1.setResultsPerPage(0);
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
        try {
            search1.search();
            fail("Search with where, expected 3 errors");
        }
        catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }

        search1.setLatlon(new CGLatLon(30.0d, 30.0d));
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

        search1.setLatlon(null);
        search1.setRadius(CGConstants.FLOAT_UNKNOWN);
        search1.setRating(11);
        try {
            search1.search();
            fail("rch with invalid rating, expected 3 errors");
        }
        catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }

        search1.setRating(CGConstants.INTEGER_UNKNOWN);
        search1.setDays(0);
        try {
            search1.search();
            fail("rch with invalid days, expected 3 errors");
        }
        catch (CGException e) {
            assertEquals(3, e.getErrors().length);
        }

    }
}
