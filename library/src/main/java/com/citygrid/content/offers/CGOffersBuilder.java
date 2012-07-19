/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.offers;

import com.citygrid.CGAddress;
import com.citygrid.CGBaseLocation;
import com.citygrid.CGLatLon;
import com.citygrid.CGTag;
import com.citygrid.content.CGContentBuilder;
import org.codehaus.jackson.JsonNode;

import java.net.URI;
import java.util.Date;

import static com.citygrid.CGBuilder.*;
import static com.citygrid.content.CGContentBuilder.processTags;

public class CGOffersBuilder {

    public static CGOffersType[] processOfferTypes(JsonNode parsedTypes) {
        if (parsedTypes == null) {
            return null;
        }

        CGOffersType[] types = null;
        if (parsedTypes != null) {
            String[] typesArray = jsonNodeToStringArray(parsedTypes);
            types = new CGOffersType[typesArray.length];
            int i = 0;
            for (String parsedType : typesArray) {
                CGOffersType type = CGOffersType.fromString(parsedType);
                if (type == null) {
                    type = CGOffersType.Unknown;
                }
                types[i++] = type;
            }
        }

        return types;
    }

    public static CGOffersLocation[] processOffersLocations(JsonNode parsedLocations) {
        if (parsedLocations == null) {
            return null;
        }

        CGOffersLocation[] locations = new CGOffersLocation[parsedLocations.size()];
        int i = 0;
        for (JsonNode parsedLocation : parsedLocations) {
            int locationId = nullSafeGetIntValue(parsedLocation.get("id"));
            String impressionId = nullSafeGetTextValue(parsedLocation.get("impression_id"));
            String name = nullSafeGetTextValue(parsedLocation.get("name"));
            CGAddress address = CGContentBuilder.processLocationAddress(parsedLocation.get("address"), false);
            CGLatLon latlon = new CGLatLon(
                    nullSafeGetDoubleValue(parsedLocation.get("latitude")),
                    nullSafeGetDoubleValue(parsedLocation.get("longitude")));
            URI image = jsonNodeToUri(parsedLocation.get("image_url"));
            String phone = nullSafeGetTextValue(parsedLocation.get("phone_number"));
            int rating = nullSafeGetIntValue(parsedLocation.get("rating"));
            String businessHours = nullSafeGetTextValue(parsedLocation.get("business_hours"));
            CGTag[] tags = processTags(parsedLocation.get("tags"));

            CGBaseLocation baseLocation = new CGBaseLocation.Builder()
                    .locationId(locationId)
                    .impressionId(impressionId)
                    .name(name)
                    .address(address)
                    .latlon(latlon)
                    .image(image)
                    .rating(rating)
                    .phone(phone)
                    .build();
            CGOffersLocation location = new CGOffersLocation.Builder()
                    .baseLocation(baseLocation)
                    .businessHours(businessHours)
                    .tags(tags)
                    .build();
            locations[i++] = location;
        }

        return locations;
    }

    public static CGOffersOffer processOffer(JsonNode parsedOffer) {
        if (parsedOffer == null) {
            return null;
        }

        String offerId = nullSafeGetTextValue(parsedOffer.get("id"));
        int referenceId = nullSafeGetIntValue(parsedOffer.get("reference_id"));
        String impressionId = nullSafeGetTextValue(parsedOffer.get("impression_id"));
        String title = nullSafeGetTextValue(parsedOffer.get("title"));
        String offerDescription = nullSafeGetTextValue(parsedOffer.get("description"));
        int redemptionTypeId = nullSafeGetIntValue(parsedOffer.get("redemption_type_id"));
        String redemptionType = nullSafeGetTextValue(parsedOffer.get("redemption_type"));
        URI redemptionUrl = jsonNodeToUri(parsedOffer.get("redemption_url"));
        String terms = nullSafeGetTextValue(parsedOffer.get("terms"));
        String source = nullSafeGetTextValue(parsedOffer.get("source"));
        CGOffersType[] types = processOfferTypes(parsedOffer.get("offer_types"));
        URI imageUrl = jsonNodeToUri(parsedOffer.get("image_url"));
        Date startDate = jsonNodeToDate(parsedOffer.get("start_date"));
        Date expirationDate = jsonNodeToDate(parsedOffer.get("expiration_date"));
        int popularity = nullSafeGetIntValue(parsedOffer.get("popularity"));
        float faceValue = (float) nullSafeGetDoubleValue(parsedOffer.get("face_value"));
        float discountValue = (float) nullSafeGetDoubleValue(parsedOffer.get("discount_value"));
        CGOffersLocation[] locations = processOffersLocations(parsedOffer.get("locations"));
        String attributionSource = nullSafeGetTextValue(parsedOffer.get("attribution_source"));
        URI attributionLogo = jsonNodeToUri(parsedOffer.get("attribution_logo"));

        CGOffersOffer offer = new CGOffersOffer.Builder()
                .offerId(offerId)
                .referenceId(referenceId)
                .impressionId(impressionId)
                .title(title)
                .offerDescription(offerDescription)
                .redemptionTypeId(redemptionTypeId)
                .redemptionType(redemptionType)
                .redemptionUrl(redemptionUrl)
                .terms(terms)
                .source(source)
                .types(types)
                .imageUrl(imageUrl)
                .startDate(startDate)
                .expirationDate(expirationDate)
                .popularity(popularity)
                .faceValue(faceValue)
                .discountValue(discountValue)
                .locations(locations)
                .attributionSource(attributionSource)
                .attributionLogo(attributionLogo)
                .build();

        return offer;
    }
}
