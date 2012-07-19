package com.citygrid;

import org.codehaus.jackson.JsonNode;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CGSharedTest {

    private CGShared cgShared = CGShared.getSharedInstance();

    @Test
    public void testDictAsUrlEncodedParameters() {

        assertEquals("", cgShared.dictAsUrlEncodedParameters(null));
        assertEquals("", cgShared.dictAsUrlEncodedParameters(Collections.<String, Object>emptyMap()));

        Map<String,Object> params = new HashMap<String, Object>();

        // one scalar parameter
        params.put("a", "A");
        assertEquals("?a=A", cgShared.dictAsUrlEncodedParameters(params));

        // one array parameter
        params.put("a", new String[] {"A1", "A2"});
        assertEquals("?a=A1&a=A2", cgShared.dictAsUrlEncodedParameters(params));

        // two scalar parameters
        params.clear();
        params.put("a", "A");
        params.put("b", "B");
        assertEquals("?b=B&a=A", cgShared.dictAsUrlEncodedParameters(params));

        // two array parameters
        params.put("a", new String[] {"A1", "A2"});
        params.put("b", new String[] {"B1", "B2"});
        assertEquals("?b=B1&b=B2&a=A1&a=A2", cgShared.dictAsUrlEncodedParameters(params));

        // one scalar, one array parameter
        params.put("a", "A");
        params.put("b", new String[] {"B1", "B2"});
        assertEquals("?b=B1&b=B2&a=A", cgShared.dictAsUrlEncodedParameters(params));

        // only String and String[] are allowed
        try {
            params.clear();
            params.put("a", 1);
            cgShared.dictAsUrlEncodedParameters(params);
            fail("Should fail with an Integer");
        }
        catch (Exception e) {
            assertTrue(e.getMessage().contains("Only String and String[] are allowed for URL parameters"));
        }

        // only String and String[] are allowed
        try {
            params.clear();
            params.put("a", new Integer[] {1, 2});
            cgShared.dictAsUrlEncodedParameters(params);
            fail("Should fail with an Integer[]");
        }
        catch (Exception e) {
            assertTrue(e.getMessage().contains("Only String and String[] are allowed for URL parameters"));
        }
    }

    @Test
    public void testBuildSimulationJsonPath() {
        // content/places/v2/detail to content_places_v2_detail
        String apiUrl = "content/places/v2/detail";
        cgShared.setSimulation(true);

        assertEquals("com/citygrid/simulation/content_places_v2_detail.json", cgShared.buildSimulationJsonPath(apiUrl));

        assertEquals("com/citygrid/simulation/content_places_v2_detail.json", cgShared.buildSimulationJsonPath(apiUrl));

        cgShared.setSimulation(false);
    }

    @Test
    public void testSimulation() throws CGException {
        String apiUrl = "content/places/v2/detail";
        cgShared.setSimulation(true);

        JsonNode rootNode = cgShared.sendSynchronousRequest(apiUrl, Collections.<String, Object>emptyMap(), 8000, 15000);

        assertNotNull("Should have valid json at com/citygrid/simulation/content_places_v2_detail.json", rootNode);
        JsonNode locationsNode = rootNode.get("locations");
        assertNotNull(locationsNode);
        JsonNode firstLocation = locationsNode.get(0);
        assertEquals(41735803, firstLocation.get("id").getIntValue());

        apiUrl = "bad/url/to/nowhere";
        try {
            cgShared.sendSynchronousRequest(apiUrl, Collections.<String, Object>emptyMap(), 8000, 15000);
            fail("Expected CGException in simulation mode with bad apiUrl" + apiUrl);
        }
        catch (CGException e) {
            assertEquals(CGErrorNum.ParseError, e.getErrors()[0].getErrorNumber());
        }

        cgShared.setSimulation(false);
    }
}
