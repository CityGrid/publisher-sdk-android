package com.citygrid;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class CGBuilder {
    private static Logger logger = Logger.getLogger(CGBuilder.class.getName());

    private static SimpleDateFormat ssZZZ_formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");
    private static SimpleDateFormat ssz_formatter =   new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
    private static SimpleDateFormat ssZ_formatter =   new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    private static SimpleDateFormat SSSZ_formatter =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private static SimpleDateFormat zulu_formatter =   new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static ObjectMapper mapper = new ObjectMapper();

    public static String ipAddress() {

        String address = "";
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.warning(
                    String.format("Unable to obtain localhost IP addrss. Exception: %s",
                            e.getMessage()));
        }

        return address;
    }

    public static URI jsonNodeToUri(JsonNode node) {
        URI uri = null;
        try {
            if (node != null) {
                String value = node.getTextValue();
                if (value != null && value.trim().length() > 0) {
                    uri = new URI(node.getTextValue());
                }
            }
        } catch (URISyntaxException e) {
            logger.warning(
                    String.format("Unable to create an URI from JsonNode: %s because of %s"
                            , node.toString(), e.getMessage()));
        }
        return uri;
    }

    public static Date jsonNodeToDate(JsonNode node) {
        if (node == null) {
            return null;
        }
        return parseDate(node.getValueAsText());
    }

    public static String formatDate(Date date) {
        return ssZZZ_formatter.format(date);
    }
    /**
     * CG returned date string has a colon between hours and minutes in timezone offset. Need to strip it before parsing.
     * @param string
     * @return
     */
    public static Date parseDate(String string) {
        //2008-06-16T23:26:25Z     // this is ZULU time, i.e. UTC time
        //2010-06-04T00:00:00-07:00
        //2010-06-04T00:00:00.123-07:00
        Date date = null;
        try {

            if (string.length()  == 20 && string.endsWith("Z")) {
            	// try zulu_formatter first
            	 // explicitly set timezone of input if needed
            	 zulu_formatter.setTimeZone(java.util.TimeZone.getTimeZone("Zulu"));
            	 try {
            		 date = zulu_formatter.parse(string);
            	 }
            	 catch (Exception e) {
            		 logger.warning(
            				 String.format(
            						 "Cannot parse date string %s with zulu format yyyy-MM-dd'T'HH:mm:ss'Z'.  Replace Z with UTC as last resort",
            						 string));
                     String adjusted = string.replaceAll("Z", "UTC");
                     date = ssz_formatter.parse(adjusted);
            	 }
            }

            if (string.length() == 25 || string.length() == 29) {
                // Strip the last ":"
                int indexOfLastColon = string.lastIndexOf(":");
                String adjusted = string.substring(0, indexOfLastColon) + string.substring(indexOfLastColon + 1);
                if (string.length() == 25) {
                        date = ssZ_formatter.parse(adjusted);
                }
                else {
                    date = SSSZ_formatter.parse(adjusted);
                }
            }
        } catch (ParseException e) {
            logger.warning(
                    String.format("Unable to parse Date from: %s because of %s"
                            , string, e.getMessage()));
        }
        return date;

    }

    public static boolean nullSafeGetBooleanValue(JsonNode node) {
        boolean result = false;
        if (node != null) {
            result = node.getBooleanValue();
        }
        return result;
    }
    public static int nullSafeGetIntValue(JsonNode node) {
        int result = CGConstants.INTEGER_UNKNOWN;
        if (node != null) {
            //TODO JsonValue.getValueAsInt and getIntValue all returns 0 for 'null' in JSON, but we want INTEGER_UNKNOWN.
            result = node.getValueAsInt(CGConstants.INTEGER_UNKNOWN);
        }
        return result;
    }
    public static double nullSafeGetDoubleValue(JsonNode node) {
        double result = CGConstants.DOUBLE_UNKNOWN;
        if (node != null) {
            result = node.getValueAsDouble();
        }
        return result;
    }
    public static String nullSafeGetTextValue(JsonNode node) {
        String result = null;
        if (node != null) {
            result = node.getValueAsText();
            // getValuesAsText will do conversion,therefore returns string "null" for JSON null. We want object null.
            if ("null".equals(result)) {
                result = null;
            }
        }
        return result;
    }

    public static String[] jsonNodeToStringArray(JsonNode node) {
        String[] results = null;
        if (node != null) {
            results = new String[node.size()];
            int i = 0;
            for (JsonNode childNode : node ) {
                results[i++] = childNode.getValueAsText();
            }
        }
        return results;
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static boolean isNotEmpty(String value) {
        return value != null && value.trim().length() > 0;
    }

    public static JsonNode parseJsonFile(String jsonFilePath) throws CGException {
        JsonNode rootNode;InputStream inputStream = null;
        try {
            inputStream = CGBuilder.class.getClassLoader().getResourceAsStream(jsonFilePath);
            rootNode = mapper.readTree(inputStream);
        }
        catch (JsonProcessingException e) {
            throw new CGException(
                    new CGError(
                            CGErrorNum.ParseError,
                            String.format(
                                    "Error parsing JSON from file %s with error %s at JsonLocation %s",
                                    jsonFilePath, e.getMessage(), e.getLocation().toString())));
        }
        catch (IOException e) {
            throw new CGException(
                    new CGError(
                            CGErrorNum.ParseError,
                            String.format(
                                    "Error parsing JSON from file %s with error %s",
                                    jsonFilePath, e.getMessage())));
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignore) {}
            }
        }
        return rootNode;
    }
}
