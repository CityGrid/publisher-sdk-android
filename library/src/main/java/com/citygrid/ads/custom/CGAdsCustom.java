/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.ads.custom;

import com.citygrid.*;

import org.codehaus.jackson.JsonNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.citygrid.CGBuilder.*;
import static com.citygrid.content.CGContentBuilder.processLocationAddress;

/**
 * The Custom Ads SDK allows you to build custom ads and featured details in your application.
 * It allows you have preferred placement for local advertisers to help you earn money directly on the page.
 *
 * <p>Let's pretend you want to present a restaurant custom ad based on the latitude and longitude of the device.</p>
 *
<pre>
{@code
    CGAdsCustom search = CityGrid.adsCustom();
    search.setWhat("restaurant");
    search.setLatlon(new CGLatLon(34.0522222d, -118.2427778d));
    search.setRadius(20.0f);

    CGAdsCustomResults results = search.search();
    for (CGAdsCustomAd ad : results.getAds()) {
        // do something with the add
    }
}
</pre>
 *
 * <p>Next, we'll pretend the user clicked on the ad and you wish to show a detail to help you monetize.</p>
 *
<pre>
{@code
    CGAdsCustomAd ad = results.getAd();
    CGPlacesDetailLocation detailLocation = ad.placesDetailLocation();
    // do something with the detail location, maybe even track it CGPlacesDetailLocation.track(muid, mobileType).
}
</pre>
 *
 */
public class CGAdsCustom implements Serializable, Cloneable {

    private static final String CGAdsCustomWhereUri = "ads/custom/v2/where";
    private static final String CGAdsCustomLatLonUri = "ads/custom/v2/latlon";
    private String publisher;
    private String ua;
    private String serveUrl; 
    private String rawWhat;
    private String rawWhere;
    private String what;
    private String where;
    private int max;
    private String placement;
    private String impressionId;
    private CGLatLon latlon;
    private float radius;
    private int connectTimeout;
    private int readTimeout;


    public CGAdsCustom(String publisher) {
        this.publisher = publisher;
        this.serveUrl = null;
        this.ua = null;
        this.rawWhat = null;
        this.rawWhere = null;
        this.what = null;
        this.where = null;
        this.max = CGConstants.INTEGER_UNKNOWN;
        this.placement = null;
        this.impressionId = null;
        this.latlon = null;
        this.radius = CGConstants.FLOAT_UNKNOWN;
        this.connectTimeout = CGShared.getSharedInstance().getConnectTimeout();
        this.readTimeout = CGShared.getSharedInstance().getReadTimeout();
    }

    public static CGAdsCustom adsCustom() {
        return adsCustomWithPublisherAndPlacement(null, null);
    }

    public static CGAdsCustom adsCustomWithPublisher(String publisher) {
        return adsCustomWithPublisherAndPlacement(publisher, null);
    }

    public static CGAdsCustom adsCustomWithPlacement(String placement) {
        return adsCustomWithPublisherAndPlacement(null, placement);
    }

    public static CGAdsCustom adsCustomWithPublisherAndPlacement(
            String publisher, String placement) {
        String resolvedPublisher = publisher != null ? publisher : CGShared.getSharedInstance().getPublisher();
        String resolvedPlacement = placement != null ? placement : CGShared.getSharedInstance().getPlacement();

        CGAdsCustom builder = new CGAdsCustom(resolvedPublisher);
        builder.setPlacement(resolvedPlacement);
        return builder;
    }

    public CGAdsCustomResults search() throws CGException {
        List<CGError> validationErrors = validate();
        if (!validationErrors.isEmpty()) {
            throw new CGException(validationErrors.toArray(new CGError[validationErrors.size()]));
        }

        String url = isNotEmpty(this.where) ? CGAdsCustomWhereUri : CGAdsCustomLatLonUri;
        Map<String, Object> params = build();
        JsonNode rootNode = CGShared.getSharedInstance().sendSynchronousRequest(url, params, connectTimeout, readTimeout);
        if (rootNode == null) {
            return null;
        }

        CGAdsCustomResults results = processResults(rootNode);
        return results;
    }

    private CGAdsCustomResults processResults(JsonNode rootNode) {
        CGAdsCustomAd[] ads = processAds(rootNode.get("ads"));
        CGAdsCustomResults results = new CGAdsCustomResults(ads);
        return results;
    }

    private CGAdsCustomAd[] processAds(JsonNode parsedAds) {
        if (parsedAds == null) {
            return null;
        }
        
        CGAdsCustomAd[] ads = new CGAdsCustomAd[parsedAds.size()];
        int i = 0;
        for (JsonNode parsedAd : parsedAds) {

            CGBaseLocation baseLocation = new CGBaseLocation.Builder()
                    .locationId(nullSafeGetIntValue(parsedAd.get("listingId")))
                    .impressionId(nullSafeGetTextValue(parsedAd.get("impressionId")))
                    .name(nullSafeGetTextValue(parsedAd.get("name")))
                    .address(processLocationAddress(parsedAd, true))
                    .latlon(new CGLatLon(
                            nullSafeGetDoubleValue(parsedAd.get("latitude")),
                            nullSafeGetDoubleValue(parsedAd.get("longitude"))))
                    .image(null)
                    .rating(nullSafeGetIntValue(parsedAd.get("overallReviewRating")))
                    .phone(nullSafeGetTextValue(parsedAd.get("phone")))
                    .build();

            CGAdsCustomAd ad = new CGAdsCustomAd.Builder()
                    .baseLocation(baseLocation)
                    .adId(nullSafeGetIntValue(parsedAd.get("id")))
                    .type(nullSafeGetTextValue(parsedAd.get("type")))
                    .tagline(nullSafeGetTextValue(parsedAd.get("tagline")))
                    .businessDescription(nullSafeGetTextValue(parsedAd.get("description")))
                    .destinationUrl(jsonNodeToUri(parsedAd.get("adDestinationUrl")))
                    .displayUrl(jsonNodeToUri(parsedAd.get("adDisplayUrl")))
                    .ppe((float) nullSafeGetDoubleValue(parsedAd.get("net_ppe")))
                    .reviews(nullSafeGetIntValue(parsedAd.get("reviews")))
                    .offers(nullSafeGetTextValue(parsedAd.get("offers")))
                    .distance((float) nullSafeGetDoubleValue(parsedAd.get("distance")))
                    .attributionText(nullSafeGetTextValue(parsedAd.get("attributionText")))
                    .build();
            ads[i++] = ad;
        }
        return ads;
    }

    private List<CGError> validate() {
        List<CGError> errors = new ArrayList<CGError>();

        if (isEmpty(this.publisher)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.PublisherRequired));
        }
        if (isEmpty(this.what)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.Underspecified));
        }
        else {
        	String[] splitWhat = this.what.split(" ");
        	if(splitWhat.length > 3){
        		errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.MaxWhatOutOfRange));
        	}
        }
        if (this.max != CGConstants.INTEGER_UNKNOWN && (this.max < 1 || this.max > 10)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.MaxOutOfRange));
        }
        if (isEmpty(this.where) && (this.latlon == null || this.radius == CGConstants.FLOAT_UNKNOWN)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.GeographyUnderspecified));
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

        if (isNotEmpty(this.publisher)) {
            parameters.put("publisher", this.publisher);
        }
        if (isNotEmpty(this.ua)) {
            parameters.put("ua", this.ua);
        }
        if (isNotEmpty(this.serveUrl)) {
            parameters.put("serve_url", this.serveUrl);
        }
        if (isNotEmpty(this.rawWhat)) {
            parameters.put("raw_what", this.rawWhat);
        }
        if (isNotEmpty(this.rawWhere)) {
            parameters.put("raw_where", this.rawWhere);
        }
        if (isNotEmpty(this.what)) {
        	String[] splitWhat = this.what.split(" ");
            parameters.put("what", splitWhat);
        }
        if (isNotEmpty(this.where)) {
            parameters.put("where", this.where);
        }
        //parameters.put("client_ip", ipAddress());
        if (this.max != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("max", String.format("%d", this.max));
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
        if (this.radius != CGConstants.FLOAT_UNKNOWN) {
            parameters.put("radius", String.format("%f", this.radius));
        }

        parameters.put("format", "json");

        return parameters;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }
    
    public String getServeUrl() {
        return serveUrl;
    }

    public void setServeUrl(String serveUrl) {
        this.serveUrl = serveUrl;
    }
    
    public String getRawWhat() {
        return rawWhat;
    }

    public void setRawWhat(String rawWhat) {
        this.rawWhat = rawWhat;
    }
    
    public String getRawWhere() {
        return rawWhat;
    }

    public void setRawWhere(String rawWhere) {
        this.rawWhere = rawWhere;
    }
    
    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
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
        if (!(o instanceof CGAdsCustom)) return false;

        CGAdsCustom that = (CGAdsCustom) o;

        if (max != that.max) return false;
        if (Float.compare(that.radius, radius) != 0) return false;
        if (readTimeout != that.readTimeout) return false;
        if (connectTimeout != that.connectTimeout) return false;
        if (impressionId != null ? !impressionId.equals(that.impressionId) : that.impressionId != null) return false;
        if (latlon != null ? !latlon.equals(that.latlon) : that.latlon != null) return false;
        if (placement != null ? !placement.equals(that.placement) : that.placement != null) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;
        if (ua != null ? !ua.equals(that.ua) : that.ua != null) return false;
        if (serveUrl != null ? !serveUrl.equals(that.serveUrl) : that.serveUrl != null) return false;
        if (rawWhat != null ? !rawWhat.equals(that.rawWhat) : that.rawWhat != null) return false;
        if (rawWhere != null ? !rawWhere.equals(that.rawWhere) : that.rawWhere != null) return false;
        if (what != null ? !what.equals(that.what) : that.what != null) return false;
        if (where != null ? !where.equals(that.where) : that.where != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = publisher != null ? publisher.hashCode() : 0;
        result = 31 * result + (ua != null ? ua.hashCode() : 0);
        result = 31 * result + (serveUrl != null ? serveUrl.hashCode() : 0);
        result = 31 * result + (rawWhat != null ? rawWhat.hashCode() : 0);
        result = 31 * result + (rawWhere != null ? rawWhere.hashCode() : 0);
        result = 31 * result + (what != null ? what.hashCode() : 0);
        result = 31 * result + (where != null ? where.hashCode() : 0);
        result = 31 * result + max;
        result = 31 * result + (placement != null ? placement.hashCode() : 0);
        result = 31 * result + (impressionId != null ? impressionId.hashCode() : 0);
        result = 31 * result + (latlon != null ? latlon.hashCode() : 0);
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
        sb.append(",ua=").append(ua);
        sb.append(",serveUrl=").append(serveUrl);
        sb.append(",rawWhat=").append(rawWhat);
        sb.append(",rawWhere=").append(rawWhere);
        sb.append(",what=").append(what);
        sb.append(",where=").append(where);
        sb.append(",max=").append(max);
        sb.append(",placement=").append(placement);
        sb.append(",impressionId=").append(impressionId);
        sb.append(",latlon=").append(latlon);
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
