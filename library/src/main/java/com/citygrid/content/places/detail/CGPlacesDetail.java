/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.places.detail;

import com.citygrid.*;

import org.codehaus.jackson.JsonNode;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.citygrid.CGBuilder.*;
import static com.citygrid.content.CGContentBuilder.handleUrlFields;
import static com.citygrid.content.CGContentBuilder.processLocationAddress;

/**
 * A builder for invoking the CityGrid Places Detail API as documented
 * at http://docs.citygridmedia.com/display/citygridv2/Places+API.
 *
 * <p>Once user have selected a place from results returned by {@link com.citygrid.content.places.search.CGPlacesSearch},
 * we will display the full location data of this place.  We will use the location ID returned from the search.</p>
 *
<pre>
{@code
    CGPlacesDetail detail = CityGrid.placesDetail();
    detail.setLocationId(295889);
    detail.setPlacement("search_page");

    CGPlacesDetailResults results = detail.detail();
    for (CGPlacesDetailLocation location : results.getLocations()) {
       assertDetailLocation(location, parent);
    }
}
</pre>
 *
 * <p>Once you have the detail location, you can convenient track the location is displayed.
 * See {@link com.citygrid.content.places.search.CGPlacesSearch} for code sample. </p>
 */
public class CGPlacesDetail implements Serializable, Cloneable {
    public static final String CGPlacesDetailUri = "content/places/v2/detail";

    private String publisher;
    private int locationId;
    private int infoUsaId;
    private String phone;
    private boolean customerOnly;
    private boolean allResults;
    private int reviewCount;
    private String placement;
    private int connectTimeout;
    private int readTimeout;
    private String impressionId;
    private String publicId;
    private String id; 
    private String clientIp;
    //private CGPlacesDetailType idType;
    private String idType;

    public CGPlacesDetail(String publisher) {
        this.publisher = publisher;
        this.locationId = CGConstants.INTEGER_UNKNOWN;
        this.infoUsaId = CGConstants.INTEGER_UNKNOWN;
        this.phone = null;
        this.customerOnly = false;
        this.allResults = false;
        this.reviewCount = CGConstants.INTEGER_UNKNOWN;
        this.placement = null;
        this.connectTimeout = CGShared.getSharedInstance().getConnectTimeout();
        this.readTimeout = CGShared.getSharedInstance().getReadTimeout();
        this.impressionId = null;
        this.publicId = null;
        this.clientIp = null;
        this.id = null;
        //this.idType = CGPlacesDetailType.Unknown;
        this.idType = null;
    }

    public static CGPlacesDetail placesDetail() {
        return placesDetailWithPublisherAndPlacement(null, null);
    }

    public static CGPlacesDetail placesDetailWithPublisher(String publisher) {
        return placesDetailWithPublisherAndPlacement(publisher, null);

    }

    public static CGPlacesDetail placesDetailWithPlacement(String placement) {
        return placesDetailWithPublisherAndPlacement(null, placement);

    }

    public static CGPlacesDetail placesDetailWithPublisherAndPlacement(String publisher, String placement) {
        String resolvedPublisher = publisher != null ? publisher : CGShared.getSharedInstance().getPublisher();
        String resolvedPlacement = placement != null ? placement : CGShared.getSharedInstance().getPlacement();

        CGPlacesDetail builder = new CGPlacesDetail(resolvedPublisher);
        builder.setPlacement(resolvedPlacement);
        return builder;

    }

    public CGPlacesDetailResults detail() throws CGException {
        List<CGError> validationErrors = validate();
        if (!validationErrors.isEmpty()) {
            throw new CGException(validationErrors.toArray(new CGError[validationErrors.size()]));
        }

        String url = CGPlacesDetailUri;
        Map<String, Object> params = build();
        JsonNode rootNode = CGShared.getSharedInstance().sendSynchronousRequest(url, params, connectTimeout, readTimeout);
        if (rootNode == null) {
            return null;
        }

        CGPlacesDetailResults results = processResults(rootNode);
        return results;
    }

    private List<CGError> validate() {
        List<CGError> errors = new ArrayList<CGError>();

        if (isEmpty(this.publisher)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.PublisherRequired));
        }
        if (this.locationId == CGConstants.INTEGER_UNKNOWN
                && this.infoUsaId == CGConstants.INTEGER_UNKNOWN
                && isEmpty(this.phone)
                && isEmpty(this.id)
                && isEmpty(this.publicId)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.Underspecified));
        }
        if (this.phone != null && this.phone.length() > 10) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.PhoneIllegal));
        }
        if (this.locationId != CGConstants.INTEGER_UNKNOWN && this.locationId < 0) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LocationIdOutOfRange));
        }
        if (this.infoUsaId != CGConstants.INTEGER_UNKNOWN && this.infoUsaId < 0) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.InfoUsaIdOutOfRange));
        }
        if (this.reviewCount != CGConstants.INTEGER_UNKNOWN && (this.reviewCount < 0 || this.reviewCount > 20)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.ReviewCountOutOfRange));
        }
        /*if (this.idType == CGPlacesDetailType.Unknown && isNotEmpty(this.id)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.IdTypeRequired));
        }*/
        if (isEmpty(this.idType) && isNotEmpty(this.id)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.IdTypeRequired));
        }
	
        return errors;
    }

    private Map<String, Object> build() {
        Map<String, Object> parameters = new HashMap<String, Object>(10);
        if (isNotEmpty(this.publisher)) {
            parameters.put("publisher", String.valueOf(this.publisher));
        }
        if (isNotEmpty(this.placement)) {
            parameters.put("placement", this.placement);
        }
        if (isNotEmpty(this.id)){
        	parameters.put("id", this.id);
        }
        /*if (idType != CGPlacesDetailType.Unknown) {
            parameters.put("id_type", idType.getName());
        }*/
        if (isNotEmpty(this.idType)) {
            parameters.put("id_type", this.idType);
        }
        if (isNotEmpty(this.publicId)){
        	parameters.put("public_id", this.publicId);
        }
        if (this.locationId != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("listing_id", String.valueOf(this.locationId));
        }
        if (this.infoUsaId != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("infousa_id", String.valueOf(this.infoUsaId));
        }
        if (isNotEmpty(this.phone)) {
            parameters.put("phone", this.phone);
        }
        parameters.put("customer_only", this.customerOnly ? "true" : "false");
        parameters.put("all_results", this.allResults ? "true" : "false");
        if (this.reviewCount != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("review_count", String.valueOf(reviewCount));
        }
        /*if(isNotEmpty(this.clientIp)) {
        	parameters.put("client_ip", this.clientIp);
        }
        else{
        	parameters.put("client_ip", ipAddress());
        }*/
        parameters.put("client_ip", ipAddress());
        if (isNotEmpty(this.impressionId)) {
            parameters.put("i", this.impressionId);
        }

        parameters.put("format", "json");
        return parameters;
    }

    private CGPlacesDetailLocation processLocation(JsonNode locationNode) {
        JsonNode parsedAddress = locationNode.get("address");

        CGPlacesDetailReviews reviews = processReviewsInfo(locationNode.get("review_info"));

        JsonNode parsedContactInfo = locationNode.get("contact_info");
        String callPhone = nullSafeGetTextValue(parsedContactInfo.get("display_phone"));
        URI displayUrl = jsonNodeToUri(parsedContactInfo.get("display_url"));

        CGPlacesDetailImage[] images = processImages(locationNode.get("images"));
        URI image = null;
        for (CGPlacesDetailImage detailImage : images) {
            if (detailImage.getType() == CGPlacesDetailImageType.GENERIC_IMAGE) {
                image = detailImage.getUrl();
                break;
            }
        }
        CGBaseLocation baseLocation = new CGBaseLocation.Builder()
                .locationId(nullSafeGetIntValue(locationNode.get("id")))
                .impressionId(nullSafeGetTextValue(locationNode.get("impression_id")))
                .name(nullSafeGetTextValue(locationNode.get("name")))
                .address(processLocationAddress(locationNode.get("address"), false))
                .latlon(new CGLatLon(
                        nullSafeGetDoubleValue(parsedAddress.get("latitude")),
                        nullSafeGetDoubleValue(parsedAddress.get("longitude"))))
                .image(image) // ios hardcoded nil.
                .rating(nullSafeGetIntValue(locationNode.get("rating")))
                .phone(nullSafeGetTextValue(locationNode.get("phone_number")))
                .build();

        return new CGPlacesDetailLocation.Builder()
                .baseLocation(baseLocation)
                .referenceId(nullSafeGetIntValue(locationNode.get("reference_id")))
                .displayAd(nullSafeGetBooleanValue(locationNode.get("dipslay_ad")))
                .infoUsaId(nullSafeGetIntValue(locationNode.get("infousa_id")))
                .publicId(nullSafeGetTextValue(locationNode.get("public_id")))
                .teaser(nullSafeGetTextValue(locationNode.get("teaser")))
                .callPhone(callPhone)
                .displayUrl(displayUrl)
                .markets(jsonNodeToStringArray(locationNode.get("markets")))
                .neighborhoods(jsonNodeToStringArray(locationNode.get("neighborhoods")))
                .urls(processUrls(
                        locationNode.get("urls"),
                        nullSafeGetTextValue(locationNode.get("impression_id"))))
                .customerContent(processCustomerContent(locationNode.get("customer_content")))
                .offers(processOffers(locationNode.get("offers")))
                .categories(processCategories(locationNode.get("categories")))
                .attributes(processAttributes(locationNode.get("attributes")))
                .businessHours(nullSafeGetTextValue(locationNode.get("business_hours")))
                .parking(nullSafeGetTextValue(locationNode.get("parking")))
                .tips(processTips(locationNode.get("tips")))
                .images(images)
                .editorials(processEditorials(locationNode.get("editorials")))
                .reviews(reviews)
                .build();
    }

    private CGPlacesDetailImage[] processImages(JsonNode imagesNode) {
        CGPlacesDetailImage[] images = new CGPlacesDetailImage[imagesNode.size()];
        int i = 0;
        for (JsonNode parsedImage : imagesNode) {
            String typeString = nullSafeGetTextValue(parsedImage.get("type"));
            CGPlacesDetailImageType type = CGPlacesDetailImageType.UNKNOWN;
            if (isNotEmpty(typeString)) {
                try {
                    type  = CGPlacesDetailImageType.valueOf(typeString);
                }
                catch(Exception ignore) {}
            }
            if (type == null) {
                type = CGPlacesDetailImageType.UNKNOWN;
            }

            CGPlacesDetailImage image = new CGPlacesDetailImage(type,
                    nullSafeGetIntValue(parsedImage.get("height")),
                    nullSafeGetIntValue(parsedImage.get("width")),
                    jsonNodeToUri(parsedImage.get("image_url")));
            images[i++] = image;
        }
        return images;
    }

    private CGPlacesDetailReviews processReviewsInfo(JsonNode reviewsInfoNode) {

        return new CGPlacesDetailReviews(
                nullSafeGetIntValue(reviewsInfoNode.get("overall_review_rating")),
                nullSafeGetIntValue(reviewsInfoNode.get("total_user_reviews")),
                nullSafeGetIntValue(reviewsInfoNode.get("total_user_reviews_shown")),
                processReviews(reviewsInfoNode.get("reviews")));
    }

    private CGBaseReview[] processReviews(JsonNode parsedReviews) {
        CGBaseReview[] reviews = new CGBaseReview[parsedReviews.size()];
        int i = 0;
        for (JsonNode parsedReview : parsedReviews) {
            int helpfulTotal = nullSafeGetIntValue(parsedReview.get("helpfulness_total_count"));

            CGBaseReview baseReview = new CGBaseReview.Builder()
                    .reviewId(nullSafeGetTextValue(parsedReview.get("review_id")))
                    .url(jsonNodeToUri(parsedReview.get("review_url")))
                    .title(nullSafeGetTextValue(parsedReview.get("review_title")))
                    .author(nullSafeGetTextValue(parsedReview.get("review_author")))
                    .text(nullSafeGetTextValue(parsedReview.get("review_text")))
                    .pros(nullSafeGetTextValue(parsedReview.get("pros")))
                    .cons(nullSafeGetTextValue(parsedReview.get("cons")))
                    .date(jsonNodeToDate(parsedReview.get("review_date")))
                    .rating(nullSafeGetIntValue(parsedReview.get("review_rating")))
                    .helpful(nullSafeGetIntValue(parsedReview.get("helpful_count")))
                    .unhelpful(nullSafeGetIntValue(parsedReview.get("unhelpful_count")))
                    .attributionText(nullSafeGetTextValue(parsedReview.get("attribution_text")))
                    .attributionLogo(jsonNodeToUri(parsedReview.get("attribution_logo")))
                    .attributionSource(nullSafeGetIntValue(parsedReview.get("attribution_source")))
                    .build();
            reviews[i++] = baseReview;
        }
        return reviews;
    }


    private CGPlacesDetailEditorial[] processEditorials(JsonNode editorialsNode) {
        CGPlacesDetailEditorial[] editorials =  new CGPlacesDetailEditorial[editorialsNode.size()];
        int i = 0;
        for (JsonNode parsedEditorial : editorialsNode) {
            CGPlacesDetailEditorial editorial = new CGPlacesDetailEditorial.Builder()
                    .editorialId(nullSafeGetTextValue(parsedEditorial.get("editorial_id")))
                    .url(jsonNodeToUri(parsedEditorial.get("editorial_url")))
                    .title(nullSafeGetTextValue(parsedEditorial.get("editorial_title")))
                    .author(nullSafeGetTextValue(parsedEditorial.get("editorial_author")))
                    .review(nullSafeGetTextValue(parsedEditorial.get("editorial_review")))
                    .pros(nullSafeGetTextValue(parsedEditorial.get("pros")))
                    .cons(nullSafeGetTextValue(parsedEditorial.get("cons")))
                    .date(jsonNodeToDate(parsedEditorial.get("editorial_date")))
                    .reviewRating(nullSafeGetIntValue(parsedEditorial.get("review_rating")))
                    .helpfulnessTotal(nullSafeGetIntValue(parsedEditorial.get("helpfulness_total_count")))
                    .helpfulness(nullSafeGetIntValue(parsedEditorial.get("helpful_count")))
                    .unhelpfulness(nullSafeGetIntValue(parsedEditorial.get("unhelpful_count")))
                    .attributionText(nullSafeGetTextValue(parsedEditorial.get("attribution_text")))
                    .attributionLogo(jsonNodeToUri(parsedEditorial.get("attribution_logo")))
                    .attributionSource(nullSafeGetIntValue(parsedEditorial.get("attribution_source")))
                    .build();
            editorials[i++] = editorial;
        }
        return editorials;
    }

    private CGPlacesDetailTip[] processTips(JsonNode parsedTips) {
        CGPlacesDetailTip[] tips = new CGPlacesDetailTip[parsedTips.size()];
        int i = 0;
        for (JsonNode parsedTip : parsedTips) {
            CGPlacesDetailTip tip = new CGPlacesDetailTip(
                    nullSafeGetTextValue(parsedTip.get("tip_name")),
                    nullSafeGetTextValue(parsedTip.get("tip_text")));
            tips[i++] = tip;
        }
        return tips;
    }

    private CGPlacesDetailAttribute[] processAttributes(JsonNode parsedAttributes) {
        CGPlacesDetailAttribute[] attributes = new CGPlacesDetailAttribute[parsedAttributes.size()];
        int i = 0;
        for (JsonNode parsedAttribute : parsedAttributes) {
            CGPlacesDetailAttribute attribute = new CGPlacesDetailAttribute(
                    nullSafeGetIntValue(parsedAttribute.get("attribute_id")),
                    nullSafeGetTextValue(parsedAttribute.get("name")),
                    nullSafeGetTextValue(parsedAttribute.get("value")));
            attributes[i++] = attribute;
        }
        return attributes;
    }

     private CGPlacesDetailCategory[] processCategories(JsonNode parsedCategories) {
        CGPlacesDetailCategory[] categories = new CGPlacesDetailCategory[parsedCategories.size()];
        int i = 0;
        for (JsonNode parsedCategory : parsedCategories) {
            CGPlacesDetailCategory category = new CGPlacesDetailCategory(
                    nullSafeGetIntValue(parsedCategory.get("name_id")),
                    nullSafeGetTextValue(parsedCategory.get("name")),
                    nullSafeGetIntValue(parsedCategory.get("parent_id")),
                    nullSafeGetTextValue(parsedCategory.get("parent")),
                    processGroups(parsedCategory.get("groups")));
            categories[i++] = category;
        }
        return categories;
    }

    private CGPlacesDetailGroup[] processGroups(JsonNode parsedGroups) {
        CGPlacesDetailGroup[] groups = new CGPlacesDetailGroup[parsedGroups.size()];
        int i = 0;
        for (JsonNode parsedGroup : parsedGroups) {
            CGPlacesDetailGroup group = new CGPlacesDetailGroup(
                   nullSafeGetIntValue(parsedGroup.get("group_id")),
                    nullSafeGetTextValue(parsedGroup.get("name")));
           groups[i++] = group;
        }

        return groups;
    }

    private CGPlacesDetailOffer[] processOffers(JsonNode parsedOffers) {
        CGPlacesDetailOffer[] offers = new CGPlacesDetailOffer[parsedOffers.size()];
        int i = 0;
        for (JsonNode parsedOffer : parsedOffers) {

            CGPlacesDetailOffer offer =new CGPlacesDetailOffer(
                    nullSafeGetTextValue(parsedOffer.get("offer_name")),
                    nullSafeGetTextValue(parsedOffer.get("offer_text")),
                    nullSafeGetTextValue(parsedOffer.get("offer_description")),
                    jsonNodeToUri(parsedOffer.get("offer_url")),
                    jsonNodeToDate(parsedOffer.get("offer_expiration_date")));
            offers[i++] = offer;
        }

        return offers;
    }

    private CGPlacesDetailCustomerContent processCustomerContent(JsonNode parsedCustomerContent) {
        CGPlacesDetailCustomerContent customerContent = new CGPlacesDetailCustomerContent(
                processCustomerMessage(parsedCustomerContent.get("customer_message")),
                jsonNodeToStringArray(parsedCustomerContent.get("bullets")),
                jsonNodeToUri(parsedCustomerContent.get("customer_message_url")));
        return customerContent;
    }

    private CGPlacesDetailCustomerMessage processCustomerMessage(JsonNode parsedCustomerMessage) {

        CGPlacesDetailCustomerMessage customerMessage = new CGPlacesDetailCustomerMessage(
                nullSafeGetTextValue(parsedCustomerMessage.get("value")),
                nullSafeGetTextValue(parsedCustomerMessage.get("attribution_text")),
                jsonNodeToUri(parsedCustomerMessage.get("attribution_logo")),
                nullSafeGetTextValue(parsedCustomerMessage.get("attribution_source")));
        return customerMessage;
    }

    private CGPlacesDetailUrls processUrls(JsonNode parsedUrls, String impressionId) {

        CGPlacesDetailUrls urls = new CGPlacesDetailUrls.Builder()
                .profile(
                        handleUrlFields(
                                jsonNodeToUri(parsedUrls.get("profile_url")), publisher, impressionId, placement))
                .reviews(
                        handleUrlFields(
                                jsonNodeToUri(parsedUrls.get("reviews_url")), publisher , impressionId, placement))
                .video(
                        handleUrlFields(
                                jsonNodeToUri(parsedUrls.get("video_url")), publisher, impressionId, placement))
                .website(
                        handleUrlFields(
                                jsonNodeToUri(parsedUrls.get("website_url")), publisher , impressionId, placement))
                .menu(
                        handleUrlFields(
                                jsonNodeToUri(parsedUrls.get("menu_url")), publisher , impressionId, placement))
                .reservation(
                        handleUrlFields(
                                jsonNodeToUri(parsedUrls.get("reservation_url")), publisher , impressionId, placement))
                .map(
                        handleUrlFields(
                                jsonNodeToUri(parsedUrls.get("map_url")), publisher , impressionId, placement))
                .sendToFriend(
                        handleUrlFields(
                                jsonNodeToUri(parsedUrls.get("send_to_friend_url")), publisher , impressionId, placement))
                .email(
                        handleUrlFields(
                                jsonNodeToUri(parsedUrls.get("email_url")), publisher , impressionId, placement))
                .custom1(
                        handleUrlFields(
                                jsonNodeToUri(parsedUrls.get("custom_link_1")), publisher, impressionId, placement))
                .custom2(
                        handleUrlFields(
                                jsonNodeToUri(parsedUrls.get("custom_link_2")), publisher, impressionId, placement))
                .orderUrl(
                        handleUrlFields(
                                jsonNodeToUri(parsedUrls.get("order_url")), publisher, impressionId, placement))
                .build();
        return urls;
    }

    CGPlacesDetailResults processResults(JsonNode rootNode) {
        JsonNode locationsNode = rootNode.get("locations");
        CGPlacesDetailLocation[] locations = new CGPlacesDetailLocation[locationsNode.size()];
        int i = 0;
        for (JsonNode locationNode : locationsNode) {
            CGPlacesDetailLocation location = processLocation(locationNode);
            locations[i++] = location;
        }

        CGPlacesDetailResults results = new CGPlacesDetailResults(locations);
        return results;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getInfoUsaId() {
        return infoUsaId;
    }

    public void setInfoUsaId(int infoUsaId) {
        this.infoUsaId = infoUsaId;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
    
    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
/*    public CGPlacesDetailType getIdType() {
        return idType;
    }

    public void setIdType(CGPlacesDetailType idType) {
        this.idType = idType;
    }*/
    
    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isCustomerOnly() {
        return customerOnly;
    }

    public void setCustomerOnly(boolean customerOnly) {
        this.customerOnly = customerOnly;
    }

    public boolean isAllResults() {
        return allResults;
    }

    public void setAllResults(boolean allResults) {
        this.allResults = allResults;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public String getImpressionId() {
        return impressionId;
    }

    public void setImpressionId(String impressionId) {
        this.impressionId = impressionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetail)) return false;

        CGPlacesDetail that = (CGPlacesDetail) o;

        if (allResults != that.allResults) return false;
        if (connectTimeout != that.connectTimeout) return false;
        if (customerOnly != that.customerOnly) return false;
        if (infoUsaId != that.infoUsaId) return false;
        if (publicId != null ? !publicId.equals(that.publicId) : that.publicId != null) return false;
        if (locationId != that.locationId) return false;
        if (readTimeout != that.readTimeout) return false;
        if (reviewCount != that.reviewCount) return false;
        if (impressionId != null ? !impressionId.equals(that.impressionId) : that.impressionId != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (placement != null ? !placement.equals(that.placement) : that.placement != null) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        //if (idType != that.idType) return false;
        if (idType != null ? !idType.equals(that.idType) : that.idType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = publisher != null ? publisher.hashCode() : 0;
        result = 31 * result + locationId;
        result = 31 * result + infoUsaId;
        result = 31 * result + (publicId != null ? publicId.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (customerOnly ? 1 : 0);
        result = 31 * result + (allResults ? 1 : 0);
        result = 31 * result + reviewCount;
        result = 31 * result + (placement != null ? placement.hashCode() : 0);
        result = 31 * result + connectTimeout;
        result = 31 * result + readTimeout;
        result = 31 * result + (impressionId != null ? impressionId.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (idType != null ? idType.hashCode() : 0);
        
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("publisher=").append(publisher);
        sb.append(",locationId=").append(locationId);
        sb.append(",infoUsaId=").append(infoUsaId);
        sb.append(",publicId=").append(publicId);
        sb.append(",phone=").append(phone);
        sb.append(",customerOnly=").append(customerOnly);
        sb.append(",allResults=").append(allResults);
        sb.append(",reviewCount=").append(reviewCount);
        sb.append(",placement=").append(placement);
        sb.append(",connectTimeout=").append(connectTimeout);
        sb.append(",readTimeout=").append(readTimeout);
        sb.append(",impressionId=").append(impressionId);
        sb.append(",id=").append(id);
        sb.append(",idType=").append(idType);
        sb.append('>');
        return sb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
