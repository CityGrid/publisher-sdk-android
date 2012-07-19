/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.places.detail;

import com.citygrid.CGTConstants;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

public class CGPlacesDetailTest {
    private ObjectMapper mapper = new ObjectMapper();
    private CGPlacesDetail detail = new CGPlacesDetail("test");

    @Test
    public void testProcessResults() throws Exception {
        CGPlacesDetailResults results = detail.processResults(getTestData());
        assertNotNull(results);
        assertEquals(1, results.getLocations().length);

        CGPlacesDetailLocation location = results.getLocations()[0];
        assertEquals(295889, location.getLocationId());
        //assertEquals(CGConstants.INTEGER_UNKNOWN, location.getInfoUsaId());   // in json this is actually a null.  Should we go with Integer instead?
        assertEquals(0, location.getInfoUsaId());   // in json this is actually a null.  Should we go with Integer instead?
        assertEquals(false, location.isDisplayAd());
        assertEquals("yankee_doodles-santa_monica-2", location.getPublicId());
        assertEquals("000900000012383aaa21e944b3aad20a13c8101a88", location.getImpressionId());

        CGPlacesDetailAddress address = (CGPlacesDetailAddress) location.getAddress();
        assertEquals("1410 Third Street Promenade",address.getStreet());
        assertEquals(null,address.getDeliveryPoint());
        assertEquals("Santa Monica",address.getCity());
        assertEquals("CA",address.getState());
        assertEquals("90401",address.getZip());
        assertEquals("Santa Monica Boulevard",address.getCrossStreet());
        assertEquals(34.0154,location.getLatlon().getLatitude(), 0.001d);
        assertEquals(-118.496, location.getLatlon().getLongitude(), 0.001d);

        assertEquals("http://YANKEEDOODLES.COM", location.getDisplayUrl().toASCIIString());

        assertEquals(1, location.getMarkets().length);
        assertEquals("Los Angeles, CA Metro", location.getMarkets()[0]);

        assertEquals(2, location.getNeighborhoods().length);
        assertEquals("West LA", location.getNeighborhoods()[0]);
        assertEquals("Downtown", location.getNeighborhoods()[1]);

        assertEquals(3, location.getCustomerContent().getBullets().length);
        assertEquals("CUSTOMER", location.getCustomerContent().getMessage().getAttributionSource());
        assertEquals("11 Hi-Def Plasma Flat Screens", location.getCustomerContent().getBullets()[1]);

        assertEquals("http://www.citysearch.com/profile/295889/santa_monica_ca/yankee_doodles_santa_monica_.html?i=000900000012383aaa21e944b3aad20a13c8101a88&publisher=test", location.getUrls().getProfile().toASCIIString());
        assertNull(location.getUrls().getVideo());
        assertEquals("http://www.w3.org/2001/XMLSchema-instance?i=000900000012383aaa21e944b3aad20a13c8101a88&publisher=test", location.getUrls().getOrderUrl().toASCIIString());
        
        assertEquals(0, location.getOffers().length);

        assertEquals(11, location.getCategories().length);
        CGPlacesDetailCategory category = location.getCategories()[0];
        assertEquals(1, category.getGroups().length);
        assertEquals("Payment Methods", category.getGroups()[0].getName());
        assertEquals(152, category.getGroups()[0].getGroupId());

        assertEquals(0, location.getAttributes().length);

        assertEquals("", location.getParking());

        assertEquals(2, location.getTips().length);

        assertEquals(4, location.getImages().length);
        assertEquals(CGPlacesDetailImageType.GENERIC_IMAGE, location.getImages()[0].getType());
        assertEquals(240, location.getImages()[0].getHeight());
        assertEquals(320, location.getImages()[0].getWidth());
        assertEquals("http://images.citysearch.net/assets/imgdb/profile/5a/f7/295889p1.jpg", location.getImages()[0].getUrl().toASCIIString());

        CGPlacesDetailEditorial editorial = location.getEditorials()[0];
        assertEquals(0, editorial.getAttributionSource());
        assertEquals("http://images.citysearch.net/assets/imgdb/custom/ue-357/CS_logo88x31.jpg", editorial.getAttributionLogo().toASCIIString());
        assertEquals("Citysearch", editorial.getAttributionText());
        assertEquals("cg_33125291", editorial.getEditorialId());
        assertEquals("http://losangeles.citysearch.com/review/295889?reviewId=33125291", editorial.getUrl().toASCIIString());
        assertEquals("Extreme Santa Monica sports bar is perfect for watching a game, or playing one.", editorial.getTitle());
        assertEquals("Contributor", editorial.getAuthor());
        assertEquals("", editorial.getPros());
        assertEquals("", editorial.getCons());
        assertEquals(0, editorial.getReviewRating());     //TODO rating is a decimal number in JSON
        assertEquals(0, editorial.getHelpfulness());
        assertEquals(0, editorial.getUnhelpfulness());
        assertNotNull(editorial.getDate());

    }

    private JsonNode getTestData() throws IOException {

        InputStream inputStream = null;
        JsonNode root = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream("citygrid-places-detail.json");
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
    public void testClone() throws CloneNotSupportedException {
        CGPlacesDetail original = new CGPlacesDetail("test");
        original.setPlacement("placement");
        original.setInfoUsaId(1);
        original.setPublicId("publicID");
        original.setAllResults(false);

        CGPlacesDetail clone = (CGPlacesDetail) original.clone();

        assertEquals(original, clone);
        assertTrue(original != clone);
    }

    @Test
    public void testToString() {
        CGPlacesDetail builder = new CGPlacesDetail(CGTConstants.CGT_PUBLISHER);
        builder.setLocationId(1111);
        builder.setInfoUsaId(2222);
        builder.setPublicId("PUBLIC_ID");
        builder.setPhone("3333");
        builder.setCustomerOnly(true);
        builder.setAllResults(true);
        builder.setReviewCount(4444);
        builder.setPlacement("PLACEMENT");
        builder.setConnectTimeout(10);
        builder.setReadTimeout(10);
        builder.setImpressionId("IMPRESSION_ID");
        builder.setId("ID");
        //builder.setIdType(CGPlacesDetailType.CityGridPublic);
        builder.setIdType("cg");
        
        String description = builder.toString();

        assertTrue(String.format("Couldn't find publisher in %s", description), description.indexOf("publisher=test") > 0);
        assertTrue(String.format("Couldn't find locationId in %s", description), description.indexOf("locationId=1111") > 0);
        assertTrue(String.format("Couldn't find infoUsaId in %s", description), description.indexOf("infoUsaId=2222") > 0);
        assertTrue(String.format("Couldn't find publicId in %s", description), description.indexOf("publicId=PUBLIC_ID") > 0);
        assertTrue(String.format("Couldn't find phone in %s", description), description.indexOf("phone=3333") > 0);
        assertTrue(String.format("Couldn't find customerOnly in %s", description), description.indexOf("customerOnly=true") > 0);
        assertTrue(String.format("Couldn't find allResults in %s", description), description.indexOf("allResults=true") > 0);
        assertTrue(String.format("Couldn't find reviewCount in %s", description), description.indexOf("reviewCount=4444") > 0);
        assertTrue(String.format("Couldn't find placement in %s", description), description.indexOf("placement=PLACEMENT") > 0);
        assertTrue(String.format("Couldn't find connectTimeout in %s", description), description.indexOf("connectTimeout=10") > 0);
        assertTrue(String.format("Couldn't find readTimeout in %s", description), description.indexOf("readTimeout=10") > 0);
        assertTrue(String.format("Couldn't 2222find impressionId in %s", description), description.indexOf("impressionId=IMPRESSION_ID") > 0);
        assertTrue(String.format("Couldn't find id in %s", description), description.indexOf("id=ID") > 0);
        assertTrue(String.format("Couldn't find idType in %s", description), description.indexOf("idType=cg") >0);
    }
    
}
