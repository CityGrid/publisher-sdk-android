package com.citygrid.content;

import com.citygrid.*;
import com.citygrid.content.places.detail.CGPlacesDetailAddress;
import org.codehaus.jackson.JsonNode;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import static com.citygrid.CGBuilder.*;

public class CGContentBuilder {
    private static Logger logger = Logger.getLogger(CGContentBuilder.class.getName());

    public static CGTag[] processTags(JsonNode tagsNode) {
        CGTag[] tags = null;
        if (tagsNode != null) {
            tags = new CGTag[tagsNode.size()];
            int i = 0;
            for (JsonNode tagNode : tagsNode) {
                CGTag tag = new CGTag(
                        nullSafeGetIntValue(tagNode.get("id")),
                        nullSafeGetTextValue(tagNode.get("name"))
                );
                tags[i++] = tag;
            }
        }
        return tags;
    }

    public static CGRegion[] processRegions(JsonNode regionsNode) {
        CGRegion[] regions = null;
        if (regionsNode != null) {
            regions = new CGRegion[regionsNode.size()];
            int i = 0;
            for (JsonNode node : regionsNode) {
                CGRegion region = new CGRegion(
                        nullSafeGetTextValue(node.get("type")),
                        nullSafeGetTextValue(node.get("name")),
                        new CGLatLon(nullSafeGetDoubleValue(node.get("latitude")), nullSafeGetDoubleValue(node.get("longitude"))),
                        (float) nullSafeGetDoubleValue(node.get("default_radius"))
                );
                regions[i++] = region;
            }
        }
        return regions;
    }

    public static CGHistogram[] processHistograms(JsonNode histogramsNode) {
        CGHistogram[] histograms = null;
        if (histogramsNode != null) {
            histograms = new CGHistogram[(histogramsNode.size())];
            int i = 0;
            for (JsonNode node : histogramsNode) {
                CGHistogram histogram = new CGHistogram(
                        nullSafeGetTextValue(node.get("name")),
                        processHistogramItems(node));
                histograms[i++] = histogram;
            }
        }
        return histograms;
    }

    private static CGHistogramItem[] processHistogramItems(JsonNode node) {
        CGHistogramItem[] items = null;
        JsonNode itemNodes = node.get("items");
        if (itemNodes != null) {
            items = new CGHistogramItem[itemNodes.size()];
            int i = 0;
            for (JsonNode itemNode : itemNodes) {
                CGHistogramItem item = new CGHistogramItem(
                        nullSafeGetTextValue(itemNode.get("name")),
                        nullSafeGetIntValue(itemNode.get("count")),
                        jsonNodeToUri(itemNode.get("uri")),
                        processTagIds(itemNode.get("tag_ids")));
                items[i++] = item;
            }
        }
        return items;
    }

    private static String[] processTagIds(JsonNode tagIdNodes) {
        String[] tagIds = null;
        if (tagIdNodes != null) {
            tagIds = new String[tagIdNodes.size()];
            int i = 0;
            for (JsonNode tagIdNode : tagIdNodes) {
                String tagId = nullSafeGetTextValue(tagIdNode.get("tag_id"));
                tagIds[i++] = tagId;
            }
        }
        return tagIds;
    }

    public static CGAddress processLocationAddress(JsonNode parsedAddress, boolean useZip) {
        if (parsedAddress == null) {
            return null;
        }
	
        String street = parsedAddress.get("street").getValueAsText();
        String city = parsedAddress.get("city").getTextValue();
        String state = parsedAddress.get("state").getTextValue();
        String zip = "";
        if (useZip) {
            zip = parsedAddress.get("zip").getValueAsText();
        } else {
            zip = parsedAddress.get("postal_code").getValueAsText();
        }

        JsonNode deliveryPointNode = parsedAddress.get("delivery_point");
        String deliveryPoint = null;
        if (deliveryPointNode != null) {
            deliveryPoint = deliveryPointNode.getTextValue();
        }

        JsonNode crossStreetNode = parsedAddress.get("cross_street");
        String crossStreet = null;
        if (crossStreetNode != null) {
            crossStreet = crossStreetNode.getTextValue();
        }

        CGAddress address = null;
        final boolean hasDeliveryPoint = deliveryPoint != null && deliveryPoint.trim().length() > 0;
        final boolean hasCrossStreet = crossStreet != null && crossStreet.trim().length() > 0;
        if (hasDeliveryPoint || hasCrossStreet) {
            address = new CGPlacesDetailAddress(street, city, state, zip, deliveryPoint, crossStreet);
        } else {
            address = new CGAddress(street, city, state, zip);
        }
	
        return address;
    }

    private static StringBuilder appendParameter(StringBuilder urlString, String key, String value) {
        if (urlString.indexOf("?") < 0) {
            urlString.append("?");
        } else {
            urlString.append("&");
        }

        urlString.append(key).append("=").append(value);
        return urlString;
    }


    public static URI handleUrlFields(URI original, String publisher, String impressionId, String placement) {
        if (original == null) {
            return null;
        }

        StringBuilder urlString = new StringBuilder(original.toASCIIString());
        boolean hasImpressionId = impressionId != null && impressionId.trim().length() > 0;
        boolean hasPublisher = publisher != null && publisher.trim().length() > 0;
        boolean hasPlacement = placement != null && placement.trim().length() > 0;

        if (hasImpressionId
                && (urlString.indexOf("&i=") < 0
                && urlString.indexOf("?i=") < 0)){
            appendParameter(urlString, "i", impressionId);
        }

        if (hasPublisher
                && hasImpressionId
                && urlString.indexOf("&publisher=") < 0
                && urlString.indexOf("?publisher=") < 0) {
            appendParameter(urlString, "publisher", publisher);
        }

        if (hasPlacement && hasImpressionId
                && urlString.indexOf("&placement=") < 0
                && urlString.indexOf("?placement=") < 0) {
            appendParameter(urlString, "placement", placement);
        }

        URI uri = null;
        try {
            uri = new URI(urlString.toString())  ;
        } catch (URISyntaxException e) {
            logger.warning(
                    String.format(
                            "Exception creating URI from %s with publisher=%s, impressionId=%s, placement=%s, error is %s",
                            original, publisher, impressionId, placement, e.getMessage()));
        }
        return uri;
    }
}
