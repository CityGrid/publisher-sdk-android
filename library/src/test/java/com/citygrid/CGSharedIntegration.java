package com.citygrid;

import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class CGSharedIntegration {
    private static Logger logger = Logger.getLogger(CGSharedIntegration.class.getName());
    private CGShared cgShared = CGShared.getSharedInstance();

    @Test
    public void testSendSynchronousRequest() {

//       http://api.citygridmedia.com/content/places/v2/detail?
//      listing_id=605102922&publisher=4214549098&api_key=nd526k7q5x6hkjaz34s7ejq8&review_count=10&placement=where-ios&client_ip=10.0.1.6&format=xml

        cgShared.setSimulation(false);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("listing_id", "605102922");
        params.put("publisher", "4214549098");
        params.put("api_key", "nd526k7q5x6hkjaz34s7ejq8");
        params.put("review_count", "10");
        params.put("placement", "where-ios");
        params.put("client_ip", "10.0.1.6");
        params.put("format", "json");

        try {
            JsonNode rootNode = cgShared.sendSynchronousRequest("content/places/v2/detail", params, 8000, 15000);
            logger.fine(rootNode.toString());
            assertNotNull(rootNode);
        }
        catch (CGException e)  {
            fail("Exception querying for /content/places/v2/detail with error: " + Arrays.toString(e.getErrors()));
        }
    }
}
