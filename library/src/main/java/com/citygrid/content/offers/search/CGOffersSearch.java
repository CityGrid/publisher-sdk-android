/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.offers.search;

import com.citygrid.*;
import com.citygrid.content.offers.CGOffersOffer;
import com.citygrid.content.offers.CGOffersType;
import org.codehaus.jackson.JsonNode;

import java.io.Serializable;
import java.util.*;

import static com.citygrid.CGBuilder.*;
import static com.citygrid.content.CGContentBuilder.processHistograms;
import static com.citygrid.content.CGContentBuilder.processRegions;
import static com.citygrid.content.offers.CGOffersBuilder.processOffer;

/**
 * The Offers SDK will help find any offers in your area as well as help minimize the listings returned for an offer
 * based on where you are.
 * <p>
 *     Let's pretend the user chose to find any offers for sushi in the 90025 zip code for restaurants.
 * </p>
 *
<pre>
<code>
 CGOffersSearch search = CityGrid.offersSearch();
    search.setWhat("sushi");
    search.setWhere("90025");

    CGOffersSearchResults results = search.search();

    for (CGOffersOffer offer : offers) {
        // do something with offer
    }
</code>
</pre>
 *
 * <p>Next, let's pretend the user clicked on an offer for a specific location in your app.
 * This assumes you found the offer item some way in your app, in this case, the last offer.</p>
 *
<pre>
<code>
    // Pretend the user selected the last offer and wants to display the offer information for
    // just the last locationId (an offer may be for more than one location)
    // Usually this is a navigation to a detail view.
    CGOffersOffer offersOffer = results.getOffers()[results.getOffers().length - 1];
    int locationId = offersOffer.getLocations()[offersOffer.getLocations().length -1].getLocationId();

    // Get the detail for only the offer and the last location id (This will populate the offerId, locationId
    // and impressionId for you). You could alternatively use the offerId, locationId and impressionId yourself,
    // but this is easier
    CGOffersDetail detail = offersOffer.offersDetail();
    detail.setLocationId(locationId);

    CGOffersOffer detailsOffer = detail.detail().getOffer();
    // do something with the offer, say maybe showing it to the user.
</code>
</pre>
 */
public class CGOffersSearch implements Serializable, Cloneable {
    private static final String CGOffersSearchWhereUri = "content/offers/v2/search/where";
    private static final String CGOffersSearchLatLonUri = "content/offers/v2/search/latlon";
    private String publisher;
    private CGOffersType type;
    private String what;
    private int tag;
    private String where;
    private CGOffersSearchTagOperation tagOperation;
    private int excludeTag;
    private int page;
    private int resultsPerPage;
    private Date startDate;
    private Date expiresBefore;
    private boolean hasBudget;
    private CGOffersSearchSort sort;
    private String source;
    private int popularity;
    private boolean histograms;
    private String placement;
    private String impressionId;
    private CGLatLon latlon;
    private CGLatLon latlon2;
    private float radius;
    private int connectTimeout;
    private int readTimeout;

    public CGOffersSearch(String publisher) {
        this.publisher = publisher;
        this.type = CGOffersType.Unknown;
        this.what = null;
        this.tag = CGConstants.INTEGER_UNKNOWN;
        this.where = null;
        this.tagOperation = CGOffersSearchTagOperation.Unknown;
        this.excludeTag = CGConstants.INTEGER_UNKNOWN;
        this.page = CGConstants.INTEGER_UNKNOWN;
        this.resultsPerPage = CGConstants.INTEGER_UNKNOWN;
        this.startDate = null;
        this.expiresBefore = null;
        this.hasBudget = false;
        this.sort = CGOffersSearchSort.Unknown;
        this.source = null;
        this.popularity = CGConstants.INTEGER_UNKNOWN;
        this.histograms = false;
        this.placement = null;
        this.impressionId = null;
        this.latlon = null;
        this.latlon2 = null;
        this.radius = CGConstants.FLOAT_UNKNOWN;
        this.connectTimeout = CGShared.getSharedInstance().getConnectTimeout();
        this.readTimeout = CGShared.getSharedInstance().getReadTimeout();
    }

    public static CGOffersSearch offersSearch() {
        return offersSearchWithPublisherAndPlacement(null, null);
    }
    public static CGOffersSearch offersSearchWithPublisher(String publisher) {
        return offersSearchWithPublisherAndPlacement(publisher, null);
    }
    public static CGOffersSearch offersSearchWithPlacement(String placement) {
        return offersSearchWithPublisherAndPlacement(null, placement);
    }

    public static CGOffersSearch offersSearchWithPublisherAndPlacement(
            String publisher, String placement) {
        String resolvedPublisher = publisher != null ? publisher : CGShared.getSharedInstance().getPublisher();
        String resolvedPlacement = placement != null ? placement : CGShared.getSharedInstance().getPlacement();

        CGOffersSearch builder = new CGOffersSearch(resolvedPublisher);
        builder.setPlacement(resolvedPlacement);
        return builder;
    }

    public CGOffersSearchResults search() throws CGException {
        List<CGError> validationErrors = validate();
        if (!validationErrors.isEmpty()) {
            throw new CGException(validationErrors.toArray(new CGError[validationErrors.size()]));
        }

        String url = isNotEmpty(this.where) ? CGOffersSearchWhereUri : CGOffersSearchLatLonUri;
        Map<String, Object> params = build();
        JsonNode rootNode = CGShared.getSharedInstance().sendSynchronousRequest(url, params, connectTimeout, readTimeout);
        if (rootNode == null) {
            return null;
        }

        CGOffersSearchResults results = processResults(rootNode.get("results"));
        return results;
    }
    
    private List<CGError> validate() {
        List<CGError> errors = new ArrayList<CGError>();
        
        if (isEmpty(this.publisher)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.PublisherRequired));
        }
        if (isEmpty(this.where) && (this.latlon == null || (this.radius == CGConstants.FLOAT_UNKNOWN && this.latlon2 == null))) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.GeographyUnderspecified));
        }
        if (isEmpty(this.what) && this.type == CGOffersType.Unknown && this.tag == CGConstants.INTEGER_UNKNOWN) {
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
        if (this.latlon != null && (this.latlon.getLatitude() > 180.0 || this.latlon.getLatitude() < -180.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LatitudeIllegal));
        }
        if (this.latlon != null && (this.latlon.getLongitude() > 180.0 || this.latlon.getLongitude() < -180.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LongitudeIllegal));
        }
        if (this.latlon2 != null && (this.latlon2.getLatitude() > 180.0 || this.latlon2.getLatitude() < -180.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LatitudeIllegal));
        }
        if (this.latlon2 != null && (this.latlon2.getLongitude() > 180.0 || this.latlon2.getLongitude() < -180.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LongitudeIllegal));
        }
        if (this.radius != CGConstants.FLOAT_UNKNOWN && ! (this.radius >= 1.0 && this.radius <= 50.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.RadiusOutOfRange));
        }

        return errors;
    }

    private Map<String, Object> build() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        if (isNotEmpty(this.publisher)) {
            parameters.put("publisher", this.publisher);
        }
        if (type != CGOffersType.Unknown) {
            parameters.put("type", type.toString());
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
        if (tagOperation != CGOffersSearchTagOperation.Unknown) {
            parameters.put("tag_op", tagOperation.toString());
        }
        if (this.excludeTag != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("exclude_tag", String.format("%d", this.excludeTag));
        }
        if (this.page != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("page", String.format("%d", this.page));
        }
        if (this.resultsPerPage != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("rpp", String.format("%d", this.resultsPerPage));
        }
        if (this.startDate != null) {
            parameters.put("start_date", formatDate(this.startDate));
        }
        if (this.expiresBefore != null) {
            parameters.put("expires_before", formatDate(this.expiresBefore));
        }
        parameters.put("has_budget", this.hasBudget ? "true" : "false");
        if (this.sort != CGOffersSearchSort.Unknown) {
            parameters.put("sort", sort.toString());
        }
        if (isNotEmpty(this.source)) {
            parameters.put("source", this.source);
        }
        if (this.popularity != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("popularity", String.format("%d", this.popularity));
        }
        parameters.put("histograms", this.histograms ? "true" : "false");
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
        if (this.latlon2 != null) {
            parameters.put("lat2", String.format("%f", this.latlon2.getLatitude()));
            parameters.put("lon2", String.format("%f", this.latlon2.getLongitude()));
        }
        if (this.radius != CGConstants.FLOAT_UNKNOWN) {
            parameters.put("radius", String.format("%f", this.radius));
        }
        parameters.put("format", "json");
        
        return parameters;
    }

    CGOffersSearchResults processResults(JsonNode rootNode) {
        CGOffersSearchResults results = new CGOffersSearchResults.Builder()
                .firstHit(nullSafeGetIntValue(rootNode.get("first_hit")))
                .latHit(nullSafeGetIntValue(rootNode.get("last_hit")))
                .totalHits(nullSafeGetIntValue(rootNode.get("total_hits")))
                .page(nullSafeGetIntValue(rootNode.get("page")))
                .resultsPerPage(nullSafeGetIntValue(rootNode.get("rpp")))
                .didYouMean(nullSafeGetTextValue(rootNode.get("did_you_mean")))
                .uri(jsonNodeToUri(rootNode.get("uri")))
                .regions(processRegions(rootNode.get("regions")))
                .histograms(processHistograms(rootNode.get("histograms")))
                .offers(processOffers(rootNode.get("offers")))
                .build();
        return results;
    }

    private CGOffersOffer[] processOffers(JsonNode parsedOffers) {
        if (parsedOffers == null) {
            return null;
        }

        CGOffersOffer[] offers = new CGOffersOffer[parsedOffers.size()];
        int i = 0;
        for (JsonNode parsedOffer : parsedOffers) {
            CGOffersOffer offer = processOffer(parsedOffer);
            offers[i++] = offer;
        }

        return offers;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public CGOffersType getType() {
        return type;
    }

    public void setType(CGOffersType type) {
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

    public CGOffersSearchTagOperation getTagOperation() {
        return tagOperation;
    }

    public void setTagOperation(CGOffersSearchTagOperation tagOperation) {
        this.tagOperation = tagOperation;
    }

    public int getExcludeTag() {
        return excludeTag;
    }

    public void setExcludeTag(int excludeTag) {
        this.excludeTag = excludeTag;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpiresBefore() {
        return expiresBefore;
    }

    public void setExpiresBefore(Date expiresBefore) {
        this.expiresBefore = expiresBefore;
    }

    public boolean isHasBudget() {
        return hasBudget;
    }

    public void setHasBudget(boolean hasBudget) {
        this.hasBudget = hasBudget;
    }

    public CGOffersSearchSort getSort() {
        return sort;
    }

    public void setSort(CGOffersSearchSort sort) {
        this.sort = sort;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public boolean isHistograms() {
        return histograms;
    }

    public void setHistograms(boolean histograms) {
        this.histograms = histograms;
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

    public CGLatLon getLatlon2() {
        return latlon2;
    }

    public void setLatlon2(CGLatLon latlon2) {
        this.latlon2 = latlon2;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
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
        if (!(o instanceof CGOffersSearch)) return false;

        CGOffersSearch that = (CGOffersSearch) o;

        if (connectTimeout != that.connectTimeout) return false;
        if (excludeTag != that.excludeTag) return false;
        if (hasBudget != that.hasBudget) return false;
        if (histograms != that.histograms) return false;
        if (page != that.page) return false;
        if (popularity != that.popularity) return false;
        if (Float.compare(that.radius, radius) != 0) return false;
        if (readTimeout != that.readTimeout) return false;
        if (resultsPerPage != that.resultsPerPage) return false;
        if (tag != that.tag) return false;
        if (expiresBefore != null ? !expiresBefore.equals(that.expiresBefore) : that.expiresBefore != null)
            return false;
        if (impressionId != null ? !impressionId.equals(that.impressionId) : that.impressionId != null) return false;
        if (latlon != null ? !latlon.equals(that.latlon) : that.latlon != null) return false;
        if (latlon2 != null ? !latlon2.equals(that.latlon2) : that.latlon2 != null) return false;
        if (placement != null ? !placement.equals(that.placement) : that.placement != null) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;
        if (sort != that.sort) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (tagOperation != that.tagOperation) return false;
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
        result = 31 * result + (tagOperation != null ? tagOperation.hashCode() : 0);
        result = 31 * result + excludeTag;
        result = 31 * result + page;
        result = 31 * result + resultsPerPage;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (expiresBefore != null ? expiresBefore.hashCode() : 0);
        result = 31 * result + (hasBudget ? 1 : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + popularity;
        result = 31 * result + (histograms ? 1 : 0);
        result = 31 * result + (placement != null ? placement.hashCode() : 0);
        result = 31 * result + (impressionId != null ? impressionId.hashCode() : 0);
        result = 31 * result + (latlon != null ? latlon.hashCode() : 0);
        result = 31 * result + (latlon2 != null ? latlon2.hashCode() : 0);
        result = 31 * result + (radius != +0.0f ? Float.floatToIntBits(radius) : 0);
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
        sb.append(",tagOperation=").append(tagOperation);
        sb.append(",excludeTag=").append(excludeTag);
        sb.append(",page=").append(page);
        sb.append(",resultsPerPage=").append(resultsPerPage);
        sb.append(",startDate=").append(startDate);
        sb.append(",expiresBefore=").append(expiresBefore);
        sb.append(",hasBudget=").append(hasBudget);
        sb.append(",sort=").append(sort);
        sb.append(",source=").append(source);
        sb.append(",popularity=").append(popularity);
        sb.append(",histograms=").append(histograms);
        sb.append(",placement=").append(placement);
        sb.append(",impressionId=").append(impressionId);
        sb.append(",latlon=").append(latlon);
        sb.append(",latlon2=").append(latlon2);
        sb.append(",radius=").append(radius);
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
