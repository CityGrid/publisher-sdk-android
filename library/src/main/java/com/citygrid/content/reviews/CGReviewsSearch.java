/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.reviews;

import com.citygrid.*;
import com.citygrid.content.places.search.CGPlacesSearchSort;

import org.codehaus.jackson.JsonNode;

import java.io.Serializable;
import java.net.URI;
import java.util.*;

import static com.citygrid.CGBuilder.*;
import static com.citygrid.content.CGContentBuilder.processHistograms;
import static com.citygrid.content.CGContentBuilder.processRegions;

/**
 * The reviews SDK helps to locate reviews in an area.
 *
 * <p>Let's pretend the user wanted to find reviews for movie theaters in 90045.</p>
 *
<pre>
{@code
    CGReviewsSearch search = CityGrid.reviewsSearch();
    search.setWhere("90025");
    search.setWhat("sushi");

    CGReviewsSearchResults results = search.search();
    for (CGReviewsSearchReview review : results.getReviews()) {
        // do something with the review
    }
}
</pre>
 *
 * <p>Similar to places serch SDK {@link com.citygrid.content.places.search.CGPlacesSearch}, let's
 * pretend that user selected a review and now wishes to view the profile.</p>
 *
<pre>
{@code
    // Pretend the user selected first result and wants to display the full location data
    // Usually this is a navigation to a detail view.
    CGReviewsSearchReview review = results.getReviews()[0];

    // Get the detail (This will populate the locationId and impressionId for you)
    // You could alternatively use the locationId and impressionId yourself, but this is easier
    CGPlacesDetailLocation location = review.placesDetailLocation();
    // do something with the location, say maybe display to your user.
}
</pre>
 **/
public class CGReviewsSearch implements Serializable, Cloneable {
    private static final String CGReviewsSearchWhereUri = "content/reviews/v2/search/where";
    private static final String CGReviewsSearchLatLonUri = "content/reviews/v2/search/latlon";
    private  String publisher;
    private  CGReviewType type;
    private  String what;
    private  int tag;
    private  String where;
    private  float radius;
    private  int page;
    private  int resultsPerPage;
    private CGReviewsSearchSort sort;
    private  String placement;
    private  String impressionId;
    private CGLatLon latlon;

    private  int rating;
    private  int days;
    private  boolean customerOnly;
    private  int locationId;

    private  int connectTimeout;
    private int readTimeout;

    public CGReviewsSearch(String publisher) {
        this.publisher = publisher;
        this.type = CGReviewType.Unknown;
        this.what = null;
        this.tag = CGConstants.INTEGER_UNKNOWN;
        this.where = null;
        this.radius = CGConstants.FLOAT_UNKNOWN;
        this.page = CGConstants.INTEGER_UNKNOWN;
        this.resultsPerPage = CGConstants.INTEGER_UNKNOWN;
        this.sort = null;
        this.placement = null;
        this.impressionId = null;
        this.latlon = null;
        this.rating = CGConstants.INTEGER_UNKNOWN;
        this.days = CGConstants.INTEGER_UNKNOWN;
        this.customerOnly = false;
        this.locationId = CGConstants.INTEGER_UNKNOWN;
        this.connectTimeout = CGShared.getSharedInstance().getConnectTimeout();
        this.readTimeout = CGShared.getSharedInstance().getReadTimeout();
    }

    public static CGReviewsSearch reviewsSearch() {
        return reviewsSearchWithPublisher(null, null);
    }

    public static CGReviewsSearch reviewsSearchWithPublisher(String publisher) {
        return reviewsSearchWithPublisher(publisher, null);
    }

    public static CGReviewsSearch reviewsSearchWithPlacement(String placement) {
        return reviewsSearchWithPublisher(null, placement);
    }

    public static CGReviewsSearch reviewsSearchWithPublisher(String publisher,String placement) {
        String resolvedPublisher = isNotEmpty(publisher) ? publisher : CGShared.getSharedInstance().getPublisher();
        String resolvedPlacement = isNotEmpty(placement) ? placement : CGShared.getSharedInstance().getPlacement();
	
        CGReviewsSearch builder = new CGReviewsSearch(resolvedPublisher);
        builder.setPlacement(resolvedPlacement);
        return builder;
    }

    private List<CGError> validate() {
        List<CGError> errors = new ArrayList<CGError>();

        if (isEmpty(this.publisher)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.PublisherRequired));
        }
        if (isEmpty(this.where) && (this.latlon == null || this.radius == CGConstants.FLOAT_UNKNOWN)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.GeographyUnderspecified));
        }
        if (isEmpty(this.what) && this.type == CGReviewType.Unknown && this.tag == CGConstants.INTEGER_UNKNOWN && this.locationId == CGConstants.INTEGER_UNKNOWN) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.Underspecified));
        }
        if (this.tag != CGConstants.INTEGER_UNKNOWN && this.tag < 1) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.TagIllegal));
        }
        if (this.resultsPerPage != CGConstants.INTEGER_UNKNOWN && (this.resultsPerPage < 1 || this.resultsPerPage > 50)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.ResultsPerPageOutOfRange));
        }
        if (this.page != CGConstants.INTEGER_UNKNOWN && this.page < 1) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.PageOutOfRange));
        }
        if (this.locationId != CGConstants.INTEGER_UNKNOWN && this.locationId < 0) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LocationIdOutOfRange));
        }
        if (this.rating != CGConstants.INTEGER_UNKNOWN && (this.rating < 1 || this.rating > 10)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.ReviewRatingOutOfRange));
        }
        if (this.days != CGConstants.INTEGER_UNKNOWN && this.days < 1) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.ReviewDaysOutOfRange));
        }
        if (this.latlon != null && (this.latlon.getLatitude() > 180.0 || this.latlon.getLatitude() < -180.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LatitudeIllegal));
        }
        if (this.latlon != null && (this.latlon.getLongitude() > 180.0 || this.latlon.getLongitude() < -180.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LongitudeIllegal));
        }
        if (this.radius != CGConstants.FLOAT_UNKNOWN && ! (this.radius >= 1.0 && this.radius <= 50.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.RadiusOutOfRange));
        }

        return errors;
    }

    private Map<String, Object> build() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        String typeString = type.getValue();
        if (isNotEmpty(this.publisher)) {
            parameters.put("publisher", this.publisher);
        }
        if (type != CGReviewType.Unknown) {
            parameters.put("review_type", typeString);
        }
        if (isNotEmpty(this.what)) {
            parameters.put("what", this.what);
        }
        if (this.tag != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("tag", String.format("%d", this.tag));
        }
        if (isNotEmpty(this.where)) {
            parameters.put("where", this.where);
        }
        if (this.radius != CGConstants.FLOAT_UNKNOWN) {
            parameters.put("radius", String.format("%f", this.radius));
        }
        if (this.page != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("page", String.format("%d", this.page));
        }
        if (this.resultsPerPage != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("rpp", String.format("%d", this.resultsPerPage));
        }
        if (this.sort != CGReviewsSearchSort.Unknown) {
            parameters.put("sort", this.sort.getName());
        }
        if (isNotEmpty(this.placement)) {
            parameters.put("placement", this.placement);
        }
        if (isNotEmpty(this.impressionId)) {
            parameters.put("i", this.impressionId);
        }
        if (this.latlon != null) {
            parameters.put("lat", String.format("%f", this.latlon.getLatitude()));
            parameters.put("lon", String.format("%f", this.latlon.getLongitude()));
        }
        if (this.rating != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("rating", String.format("%d", this.rating));
        }
        if (this.days != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("days", String.format("%d", this.rating));
        }
        parameters.put("customerOnly", this.customerOnly ? "true" : "false");
        if (this.locationId != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("listing_id", String.format("%d", this.rating));
        }
        parameters.put("format", "json");

        return parameters;
    }

    private CGReviewType processType(String parsedType) {
        CGReviewType type = null;
        try {
            type = CGReviewType.fromString(parsedType);
        }
        catch (Exception ignore) {}

        if(type == null) {
            type = CGReviewType.Unknown;
        }
        return type;
    }

    private CGReviewsSearchReview[] processReviews(JsonNode parsedReviews) {
        CGReviewsSearchReview[] reviews = new CGReviewsSearchReview[parsedReviews.size()];
        int i = 0;
        for (JsonNode parsedReview : parsedReviews) {
            String reviewId = nullSafeGetTextValue(parsedReview.get("review_id"));
            URI url = jsonNodeToUri(parsedReview.get("review_url"));
            String title = nullSafeGetTextValue(parsedReview.get("review_title"));
            String author = nullSafeGetTextValue(parsedReview.get("review_author"));
            String text = nullSafeGetTextValue(parsedReview.get("review_text"));
            String pros = nullSafeGetTextValue(parsedReview.get("pros"));
            String cons = nullSafeGetTextValue(parsedReview.get("cons"));
            Date date = jsonNodeToDate(parsedReview.get("review_date"));
            int rating = nullSafeGetIntValue(parsedReview.get("review_rating"));
            int helpfulTotal = nullSafeGetIntValue(parsedReview.get("helpfulness_total_count"));
            int helpful = nullSafeGetIntValue(parsedReview.get("helpful_count"));
            int unhelpful = nullSafeGetIntValue(parsedReview.get("unhelpful_count"));
            String attributionText = nullSafeGetTextValue(parsedReview.get("attribution_text"));
            URI attributionLogo = jsonNodeToUri(parsedReview.get("attribution_logo"));
            int attributionSource = nullSafeGetIntValue(parsedReview.get("attribution_source"));
            // URI attributionUrl = jsonNodeToUri(parsedReview.get("attribution_url"));  //TODO no such field in POJOs
            String impressionId = nullSafeGetTextValue(parsedReview.get("impression_id"));
            int referenceId = nullSafeGetIntValue(parsedReview.get("reference_id"));
            int sourceId = nullSafeGetIntValue(parsedReview.get("source_id"));
            int reviewLocationId = nullSafeGetIntValue(parsedReview.get("listing_id"));
            String businessName = nullSafeGetTextValue(parsedReview.get("business_name"));
            URI authorUrl = jsonNodeToUri(parsedReview.get("review_author_url"));
            CGReviewType type = processType(nullSafeGetTextValue(parsedReview.get("type")));

            CGBaseReview baseReview = new CGBaseReview.Builder()
                    .reviewId(reviewId)
                    .url(url)
                    .title(title)
                    .author(author)
                    .text(text)
                    .pros(pros)
                    .cons(cons)
                    .date(date)
                    .rating(rating)
                    .helpful(helpful)
                    .unhelpful(unhelpful)
                    .attributionText(attributionText)
                    .attributionLogo(attributionLogo)
                    .attributionSource(attributionSource)
                    .build();

            CGReviewsSearchReview review = new CGReviewsSearchReview.Builder()
                    .baseReview(baseReview)
                    .impressionId(impressionId)
                    .referenceId(referenceId)
                    .sourceId(sourceId)
                    .locationId(reviewLocationId)
                    .businessName(businessName)
                    .authorUrl(authorUrl)
                    .type(type)
                    .build();

            reviews[i++] = review;
        }
        return reviews;
    }

    private CGReviewsSearchResults processResults(JsonNode parsedJson) {
        JsonNode parsedResults = parsedJson.get("results");

        int firstHit = nullSafeGetIntValue(parsedResults.get("first_hit"));
        int lastHit = nullSafeGetIntValue(parsedResults.get("last_hit"));
        int totalHits = nullSafeGetIntValue(parsedResults.get("total_hits"));
        int page = nullSafeGetIntValue(parsedResults.get("page"));
        int resultsPerPage = nullSafeGetIntValue(parsedResults.get("rpp"));
        String didYouMean = nullSafeGetTextValue(parsedResults.get("did_you_mean"));
        URI uri = jsonNodeToUri(parsedResults.get("uri"));
        CGRegion[] regions = processRegions(parsedResults.get("regions"));
        CGReviewsSearchReview[] reviews = processReviews(parsedResults.get("reviews"));
        CGHistogram[] histograms = processHistograms(parsedResults.get("histograms"));

        CGReviewsSearchResults results = new CGReviewsSearchResults.Builder()
                .firstHit(firstHit)
                .lastHit(lastHit)
                .totalHits(totalHits)
                .page(page)
                .resultsPerPage(resultsPerPage)
                .uri(uri)
                .didYouMean(didYouMean)
                .regions(regions)
                .reviews(reviews)
                .histograms(histograms)
                .build();
        return results;
    }

    public CGReviewsSearchResults search() throws CGException {
        List<CGError> validationErrors = validate();
        if (!validationErrors.isEmpty()) {
            throw new CGException(validationErrors.toArray(new CGError[validationErrors.size()]));
        }

        String url = isNotEmpty(this.where) ? CGReviewsSearchWhereUri : CGReviewsSearchLatLonUri;

        Map<String, Object> params = build();
		JsonNode rootNode = CGShared.getSharedInstance().sendSynchronousRequest(url, params, connectTimeout, readTimeout);
        if (rootNode == null) {
            return null;
        }

        CGReviewsSearchResults results = processResults(rootNode);
        return results;
    }

        public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public CGReviewType getType() {
        return type;
    }

    public void setType(CGReviewType type) {
        this.type = type;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public CGReviewsSearchSort getSort() {
        return sort;
    }

    public void setSort(CGReviewsSearchSort sort) {
        this.sort = sort;
    }
    
    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public String getImpressionId() {
        return impressionId;
    }

    public void setImpressionId(String impressionId) {
        this.impressionId = impressionId;
    }

    public CGLatLon getLatlon() {
        return latlon;
    }

    public void setLatlon(CGLatLon latlon) {
        this.latlon = latlon;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public boolean isCustomerOnly() {
        return customerOnly;
    }

    public void setCustomerOnly(boolean customerOnly) {
        this.customerOnly = customerOnly;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGReviewsSearch)) return false;

        CGReviewsSearch that = (CGReviewsSearch) o;

        if (connectTimeout != that.connectTimeout) return false;
        if (customerOnly != that.customerOnly) return false;
        if (days != that.days) return false;
        if (locationId != that.locationId) return false;
        if (page != that.page) return false;
        if (Float.compare(that.radius, radius) != 0) return false;
        if (rating != that.rating) return false;
        if (readTimeout != that.readTimeout) return false;
        if (resultsPerPage != that.resultsPerPage) return false;
        if (tag != that.tag) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
        if (impressionId != null ? !impressionId.equals(that.impressionId) : that.impressionId != null) return false;
        if (latlon != null ? !latlon.equals(that.latlon) : that.latlon != null) return false;
        if (placement != null ? !placement.equals(that.placement) : that.placement != null) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;
        if (type != that.type) return false;
        if (what != null ? !what.equals(that.what) : that.what != null) return false;
        if (where != null ? !where.equals(that.where) : that.where != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = publisher != null ? publisher.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (what != null ? what.hashCode() : 0);
        result = 31 * result + tag;
        result = 31 * result + (where != null ? where.hashCode() : 0);
        result = 31 * result + (radius != +0.0f ? Float.floatToIntBits(radius) : 0);
        result = 31 * result + page;
        result = 31 * result + resultsPerPage;
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (placement != null ? placement.hashCode() : 0);
        result = 31 * result + (impressionId != null ? impressionId.hashCode() : 0);
        result = 31 * result + (latlon != null ? latlon.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + days;
        result = 31 * result + (customerOnly ? 1 : 0);
        result = 31 * result + locationId;
        result = 31 * result + connectTimeout;
        result = 31 * result + readTimeout;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("publisher=").append(publisher);
        sb.append(",type=").append(type);
        sb.append(",what=").append(what);
        sb.append(",tag=").append(tag);
        sb.append(",where=").append(where);
        sb.append(",radius=").append(radius);
        sb.append(",page=").append(page);
        sb.append(",resultsPerPage=").append(resultsPerPage);
        sb.append(",sort=").append(sort);
        sb.append(",placement=").append(placement);
        sb.append(",impressionId=").append(impressionId);
        sb.append(",latlon=").append(latlon);
        sb.append(",rating=").append(rating);
        sb.append(",days=").append(days);
        sb.append(",customerOnly=").append(customerOnly);
        sb.append(",locationId=").append(locationId);
        sb.append(",connectTimeout=").append(connectTimeout);
        sb.append(",readTimeout=").append(readTimeout);
        sb.append('>');
        return sb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
