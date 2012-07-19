/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.ads.mobile;

import com.citygrid.*;
import org.codehaus.jackson.JsonNode;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.citygrid.CGBuilder.*;

/**
 * The Mobile Ads SDK allows you to show a mobile ad directly in your app that is similar in size to other ad providers.
 * You can then present a detail and track actions similarly to Custom Ads.
 *
 * <p>To use this API, it is necessary to provide the mobile device' MUID.
 * Let's pretend you wanted to present a restaurant banner add for an mobile device based
 * on the latitude and longitude of the device.</p>
 *
<pre> 
{@code
    CityGrid.setPublisher("test");
    CityGrid.setSimulation(false);
    CityGrid.setMuid(YOUR_MUID);

    CGAdsMobile search = CityGrid.adsMobile();
    search.setWhat("restaurant");
    search.setLatlon(new CGLatLon(33.786594d, -118.298662d));
    search.setRadius(50.0f);
    search.setCollection(CGAdsMobileCollection.Collection640x100);
    search.setSize(new CGSize(640.0f, 100.0f));

    CGAdsMobileResults results = search.banner();
    for (CGAdsMobileAd ad : results.getAds()) {
        // do something with the ad
    }
}
</pre>
 *
 * <p>Next, we'll pretend the user clicked on the ad and you wish to show a detail to help you monetize.</p>
 *
<pre>
{@code
    CGAdsMobileAd ad = results.getAd();
    CGPlacesDetailLocation detailLocation = ad.placesDetailLocation();
    // do something with the detail location, maybe even track it CGPlacesDetailLocation.track(muid, mobileType).
}
</pre>
 */
public class CGAdsMobile implements Serializable, Cloneable {
    private static final String CGAdsMobileBannerUri = "ads/mobile/v2/banner";
    private String publisher;
    private CGAdsMobileCollection collection;
    private String ua;
    private String rawWhat;
    private String rawWhere;
    private String what;
    private String where;
    private CGLatLon latlon;
    private float radius;
    private int max;
    private String placement;
    private String impressionId;
    private CGSize size;
    private int connectTimeout;
    private int readTimeout;
    private String muid;

    public CGAdsMobile(String publisher) {
        this.publisher = publisher;
        this.collection = CGAdsMobileCollection.CollectionUnknown;
        this.ua = null;
        this.rawWhat = null;
        this.rawWhere = null;
        this.what = null;
        this.where = null;
        this.placement = null;
        this.impressionId = null;
        this.latlon = null;
        this.radius = CGConstants.FLOAT_UNKNOWN;
        this.max = CGConstants.INTEGER_UNKNOWN;
        this.size = null;
        this.connectTimeout = CGShared.getSharedInstance().getConnectTimeout();
        this.readTimeout = CGShared.getSharedInstance().getReadTimeout();
        this.muid = CGShared.getSharedInstance().getMuid();

    }

    public static CGAdsMobile adsMobile() {
        return adsMobileWithPublisherAndPlacement(null, null);
    }

    public static CGAdsMobile adsMobileWithPublisher(String publisher) {
        return adsMobileWithPublisherAndPlacement(publisher, null);
    }

    public static CGAdsMobile adsMobileWithPlacement(String placement) {
        return adsMobileWithPublisherAndPlacement(null, placement);
    }

    public static CGAdsMobile adsMobileWithPublisherAndPlacement(
            String publisher, String placement) {
        String resolvedPublisher = publisher != null ? publisher : CGShared.getSharedInstance().getPublisher();
        String resolvedPlacement = placement != null ? placement : CGShared.getSharedInstance().getPlacement();

        CGAdsMobile builder = new CGAdsMobile(resolvedPublisher);
        builder.setPlacement(resolvedPlacement);
        return builder;
    }

    private List<CGError> validate() {
        List<CGError> errors = new ArrayList<CGError>();

        if (isEmpty(this.publisher)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.PublisherRequired));
        }
        if (this.collection == CGAdsMobileCollection.CollectionUnknown) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.CollectionRequired));
        }
        if (isEmpty(this.where) && (this.latlon == null || this.radius == CGConstants.FLOAT_UNKNOWN)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.GeographyUnderspecified));
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
        if (this.latlon != null && (this.latlon.getLatitude() > 180.0 || this.latlon.getLatitude() < -180.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LatitudeIllegal));
        }
        if (this.latlon != null && (this.latlon.getLongitude() > 180.0 || this.latlon.getLongitude() < -180.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LongitudeIllegal));
        }
        if (this.radius != CGConstants.FLOAT_UNKNOWN && !(this.radius >= 1.0 && this.radius <= 50.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.RadiusOutOfRange));
        }
        if (this.max != CGConstants.INTEGER_UNKNOWN && (this.max < 1 || this.max > 10)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.MaxOutOfRange));
        }
        if (this.size == null) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.SizeRequired));
        }
        if (isEmpty(this.muid)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.MuidRequired));
        }

        return errors;
    }

    private Map<String, Object> build() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        if (isNotEmpty(this.publisher)) {
            parameters.put("publisher", this.publisher);
        }
        if (this.collection != CGAdsMobileCollection.CollectionUnknown) {
            parameters.put("collection_id", this.collection.toString());
        }
        if (isNotEmpty(this.ua)) {
            parameters.put("ua", this.ua);
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
        if (this.latlon != null) {
            parameters.put("lat", String.format("%f", this.latlon.getLatitude()));
            parameters.put("lon", String.format("%f", this.latlon.getLongitude()));
        }
        if (this.radius != CGConstants.FLOAT_UNKNOWN) {
            parameters.put("radius", String.format("%f", this.radius));
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
        if (this.size != null) {
            parameters.put("width", String.format("%d", (int) this.size.getWidth()));
            parameters.put("height", String.format("%d", (int) this.size.getHeight()));
        }

        //muid is mandatory
        parameters.put("muid", this.muid);

        parameters.put("format", "json");

        return parameters;
    }

    private CGAdsMobileAd[] processAds(JsonNode parsedAds) {
        CGAdsMobileAd[] ads = null;
        if (parsedAds != null) {
            ads = new CGAdsMobileAd[parsedAds.size()];
            int i = 0;
            for (JsonNode parsedAd : parsedAds) {
                int locationId = nullSafeGetIntValue(parsedAd.get("listingId"));
                String impressionId = nullSafeGetTextValue(parsedAd.get("impressionId"));
                int adId = nullSafeGetIntValue(parsedAd.get("id"));
                URI destinationUrl = jsonNodeToUri(parsedAd.get("adDestinationUrl"));
                URI image = jsonNodeToUri(parsedAd.get("adImageUrl"));

                CGAdsMobileAd ad = new CGAdsMobileAd(locationId, impressionId, adId, destinationUrl, image);
                ads[i++] = ad;
            }
        }
        return ads;
    }

    private CGAdsMobileResults processResults(JsonNode parsedJson) {
        CGAdsMobileAd[] ads = processAds(parsedJson.get("ads"));

        CGAdsMobileResults results = new CGAdsMobileResults(ads);
        return results;
    }

    public CGAdsMobileResults banner() throws CGException {
        List<CGError> validationErrors = validate();
        if (!validationErrors.isEmpty()) {
            throw new CGException(validationErrors.toArray(new CGError[validationErrors.size()]));
        }

        String url = CGAdsMobileBannerUri;
        Map<String, Object> params = build();
        JsonNode rootNode = CGShared.getSharedInstance().sendSynchronousRequest(url, params, connectTimeout, readTimeout);
        if (rootNode == null) {
            return null;
        }

        CGAdsMobileResults results = processResults(rootNode);
        return results;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public CGAdsMobileCollection getCollection() {
        return collection;
    }

    public void setCollection(CGAdsMobileCollection collection) {
        this.collection = collection;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }
    
    public String getUa() {
        return ua;
    }
    
    public String getRawWhat() {
        return rawWhat;
    }
    
    public void setRawWhat(String rawWhat) {
        this.rawWhat = rawWhat;
    }
    
    public String getRawWhere() {
        return rawWhere;
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

    public CGSize getSize() {
        return size;
    }

    public void setSize(CGSize size) {
        this.size = size;
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

    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGAdsMobile)) return false;

        CGAdsMobile that = (CGAdsMobile) o;

        if (connectTimeout != that.connectTimeout) return false;
        if (max != that.max) return false;
        if (Float.compare(that.radius, radius) != 0) return false;
        if (readTimeout != that.readTimeout) return false;
        if (collection != that.collection) return false;
        if (impressionId != null ? !impressionId.equals(that.impressionId) : that.impressionId != null) return false;
        if (latlon != null ? !latlon.equals(that.latlon) : that.latlon != null) return false;
        if (muid != null ? !muid.equals(that.muid) : that.muid != null) return false;
        if (placement != null ? !placement.equals(that.placement) : that.placement != null) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (ua != null ? !ua.equals(that.ua) : that.ua != null) return false;
        if (rawWhat != null ? !rawWhat.equals(that.rawWhat) : that.rawWhat != null) return false;
        if (rawWhere != null ? !rawWhere.equals(that.rawWhere) : that.rawWhere != null) return false;
        if (what != null ? !what.equals(that.what) : that.what != null) return false;
        if (where != null ? !where.equals(that.where) : that.where != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = publisher != null ? publisher.hashCode() : 0;
        result = 31 * result + (collection != null ? collection.hashCode() : 0);
        result = 31 * result + (ua != null ? ua.hashCode() : 0);
        result = 31 * result + (rawWhat != null ? rawWhat.hashCode() : 0);
        result = 31 * result + (rawWhere != null ? rawWhere.hashCode() : 0);
        result = 31 * result + (what != null ? what.hashCode() : 0);
        result = 31 * result + (where != null ? where.hashCode() : 0);
        result = 31 * result + (latlon != null ? latlon.hashCode() : 0);
        result = 31 * result + (radius != +0.0f ? Float.floatToIntBits(radius) : 0);
        result = 31 * result + max;
        result = 31 * result + (placement != null ? placement.hashCode() : 0);
        result = 31 * result + (impressionId != null ? impressionId.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + connectTimeout;
        result = 31 * result + readTimeout;
        result = 31 * result + (muid != null ? muid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("publisher=").append(publisher);
        sb.append(",collection=").append(collection);
        sb.append(",ua=").append(ua);
        sb.append(",rawWhat=").append(rawWhat);
        sb.append(",rawWhere=").append(rawWhere);
        sb.append(",what=").append(what);
        sb.append(",where=").append(where);
        sb.append(",latlon=").append(latlon);
        sb.append(",radius=").append(radius);
        sb.append(",max=").append(max);
        sb.append(",placement=").append(placement);
        sb.append(",impressionId=").append(impressionId);
        sb.append(",size=").append(size);
        sb.append(",connectTimeout=").append(connectTimeout);
        sb.append(",readTimeout=").append(readTimeout);
        sb.append(",muid=").append(muid);
        sb.append('>');
        return sb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
