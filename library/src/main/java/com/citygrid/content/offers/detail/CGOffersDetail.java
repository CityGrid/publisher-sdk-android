/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.offers.detail;

import com.citygrid.*;
import com.citygrid.content.offers.CGOffersOffer;
import org.codehaus.jackson.JsonNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.citygrid.CGBuilder.isEmpty;
import static com.citygrid.CGBuilder.isNotEmpty;
import static com.citygrid.content.offers.CGOffersBuilder.processOffer;
/**
 * A builder for invoking the CityGrid Places Search API as documented
 * at http://docs.citygridmedia.com/display/citygridv2/Offers+API.
 *
 * <p>Once user selected an offer from the list of offers returned by
 * {@link com.citygrid.content.offers.search.CGOffersSearch}, we can display details about
 * the offer using this API.</p>
 *
<pre>
{@code
    CGOffersDetail builder = CityGrid.offersDetail();
    builder.setOfferId("cg_61413312");

    CGOffersDetailResults results = detail(builder);
    CGOffersOffer offer = results.getOffer();
    // do something with this offer
}
</pre>
 **/
public class CGOffersDetail implements Serializable, Cloneable {
    private static final String CGOffersDetailUri = "content/offers/v2/detail";
    private String publisher;
    private String offerId;
    private int locationId;
    private String impressionId;
    private String placement;
    private int connectTimeout;
    private int readTimeout;

    public CGOffersDetail(String publisher) {
        this.publisher = publisher;
        this.offerId = null;
        this.locationId = CGConstants.INTEGER_UNKNOWN;
        this.impressionId = null;
        this.placement = null;
        this.connectTimeout = CGShared.getSharedInstance().getConnectTimeout();
        this.readTimeout = CGShared.getSharedInstance().getReadTimeout();
    }

    public static CGOffersDetail offersDetail() {
        return offersDetailWithPublisherAndPlacement(null, null);
    }

    public static CGOffersDetail offersDetailWithPublisher(String publisher) {
        return offersDetailWithPublisherAndPlacement(publisher, null);
    }

    public static CGOffersDetail offersDetailWithPlacement(String placement) {
        return offersDetailWithPublisherAndPlacement(null, placement);
    }

    public static CGOffersDetail offersDetailWithPublisherAndPlacement(
            String publisher, String placement) {
        String resolvedPublisher = publisher != null ? publisher : CGShared.getSharedInstance().getPublisher();
        String resolvedPlacement = placement != null ? placement : CGShared.getSharedInstance().getPlacement();

        CGOffersDetail builder = new CGOffersDetail(resolvedPublisher);
        builder.setPlacement(resolvedPlacement);
        return builder;
    }

    public CGOffersDetailResults detail() throws CGException {
        List<CGError> validationErrors = validate();
        if (!validationErrors.isEmpty()) {
            throw new CGException(validationErrors.toArray(new CGError[validationErrors.size()]));
        }

        String url = CGOffersDetailUri;
        Map<String, Object> params = build();
        JsonNode rootNode = CGShared.getSharedInstance().sendSynchronousRequest(url, params, connectTimeout, readTimeout);
        if (rootNode == null) {
            return null;
        }

        CGOffersDetailResults results = processResults(rootNode);
        return results;
    }

    private List<CGError> validate() {
        List<CGError> errors = new ArrayList<CGError>();

        if (isEmpty(this.publisher)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.PublisherRequired));
        }
        if (isEmpty(this.offerId)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.OfferIdRequired));
        }
        if (this.locationId != CGConstants.INTEGER_UNKNOWN && this.locationId < 0) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LocationIdOutOfRange));
        }

        return errors;
    }

    private Map<String, Object> build() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        if (isNotEmpty(this.publisher)) {
            parameters.put("publisher", this.publisher);
        }

        if (isNotEmpty(this.offerId)) {
            parameters.put("id", this.offerId);
        }
        if (this.locationId != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("location_id", String.format("%d", this.locationId));
        }
        if (isNotEmpty(this.impressionId)) {
            parameters.put("i", this.impressionId);
        }
        if (isNotEmpty(this.placement)) {
            parameters.put("placement", this.placement);
        }

        parameters.put("format", "json");

        return parameters;
    }

    private CGOffersDetailResults processResults(JsonNode parsedJson) {
        CGOffersOffer offer = processOffer(parsedJson);
        CGOffersDetailResults results = new CGOffersDetailResults(offer);
        return results;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getImpressionId() {
        return impressionId;
    }

    public void setImpressionId(String impressionId) {
        this.impressionId = impressionId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGOffersDetail)) return false;

        CGOffersDetail that = (CGOffersDetail) o;

        if (connectTimeout != that.connectTimeout) return false;
        if (locationId != that.locationId) return false;
        if (readTimeout != that.readTimeout) return false;
        if (impressionId != null ? !impressionId.equals(that.impressionId) : that.impressionId != null) return false;
        if (offerId != null ? !offerId.equals(that.offerId) : that.offerId != null) return false;
        if (placement != null ? !placement.equals(that.placement) : that.placement != null) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = publisher != null ? publisher.hashCode() : 0;
        result = 31 * result + (offerId != null ? offerId.hashCode() : 0);
        result = 31 * result + locationId;
        result = 31 * result + (impressionId != null ? impressionId.hashCode() : 0);
        result = 31 * result + (placement != null ? placement.hashCode() : 0);
        result = 31 * result + connectTimeout;
        result = 31 * result + readTimeout;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("publisher=").append(publisher);
        sb.append(",offerId=").append(offerId);
        sb.append(",locationId=").append(locationId);
        sb.append(",impressionId=").append(impressionId);
        sb.append(",placement=").append(placement);
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
