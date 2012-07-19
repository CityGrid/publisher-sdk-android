/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.reviews;

import com.citygrid.*;
import com.citygrid.content.places.detail.CGPlacesDetail;
import com.citygrid.content.places.detail.CGPlacesDetailLocation;

import java.net.URI;
import java.util.Date;

public class CGReviewsSearchReview implements CGReview, CGLocationDetailProvider, HasLocationIdAndImpressionId {

    private CGBaseReview baseReview;
    private String impressionId;
    private int referenceId;
    private int sourceId;
    private int locationId;
    private String businessName;
    private URI authorUrl;
    private CGReviewType type;

    private CGLocationDetailProvider deatailProvider;

    private CGReviewsSearchReview(Builder builder) {
        baseReview = builder.baseReview;
        impressionId = builder.impressionId;
        referenceId = builder.referenceId;
        sourceId = builder.sourceId;
        locationId = builder.locationId;
        businessName = builder.businessName;
        authorUrl = builder.authorUrl;
        type = builder.type;

        deatailProvider = new CGLocationDetailProviderImpl(this);
    }


    public static class Builder {

        private CGBaseReview baseReview;
        private String impressionId;
        private int referenceId;
        private int sourceId;
        private int locationId;
        private String businessName;
        private URI authorUrl;
        private CGReviewType type;

        public CGReviewsSearchReview build() {
            return new CGReviewsSearchReview(this);
        }

        public Builder baseReview(CGBaseReview baseReview) {
            this.baseReview = baseReview;
            return this;
        }

        public Builder impressionId(String impressionId) {
            this.impressionId = impressionId;
            return this;
        }

        public Builder referenceId(int referenceId) {
            this.referenceId = referenceId;
            return this;
        }

        public Builder sourceId(int sourceId) {
            this.sourceId = sourceId;
            return this;
        }

        public Builder locationId(int locationId) {
            this.locationId = locationId;
            return this;
        }

        public Builder businessName(String businessName) {
            this.businessName = businessName;
            return this;
        }

        public Builder authorUrl(URI authorUrl) {
            this.authorUrl = authorUrl;
            return this;
        }

        public Builder type(CGReviewType type) {
            this.type = type;
            return this;
        }
    }

    public String getImpressionId() {
        return impressionId;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public URI getAuthorUrl() {
        return authorUrl;
    }

    public CGReviewType getType() {
        return type;
    }


    public String getReviewId() {
        return baseReview.getReviewId();
    }

    public URI getUrl() {
        return baseReview.getUrl();
    }

    public String getTitle() {
        return baseReview.getTitle();
    }

    public String getAuthor() {
        return baseReview.getAuthor();
    }

    public String getText() {
        return baseReview.getText();
    }

    public String getPros() {
        return baseReview.getPros();
    }

    public String getCons() {
        return baseReview.getCons();
    }

    public Date getDate() {
        return baseReview.getDate();
    }

    public int getRating() {
        return baseReview.getRating();
    }

    public int getHelpful() {
        return baseReview.getHelpful();
    }

    public int getUnhelpful() {
        return baseReview.getUnhelpful();
    }

    public String getAttributionText() {
        return baseReview.getAttributionText();
    }

    public URI getAttributionLogo() {
        return baseReview.getAttributionLogo();
    }

    public int getAttributionSource() {
        return baseReview.getAttributionSource();
    }

    @Override
    public CGPlacesDetail placesDetail() {
        return deatailProvider.placesDetail();
    }

    @Override
    public CGPlacesDetailLocation placesDetailLocation() throws CGException {
        return deatailProvider.placesDetailLocation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGReviewsSearchReview)) return false;

        CGReviewsSearchReview that = (CGReviewsSearchReview) o;

        if (locationId != that.locationId) return false;
        if (referenceId != that.referenceId) return false;
        if (sourceId != that.sourceId) return false;
        if (authorUrl != null ? !authorUrl.equals(that.authorUrl) : that.authorUrl != null) return false;
        if (businessName != null ? !businessName.equals(that.businessName) : that.businessName != null) return false;
        if (impressionId != null ? !impressionId.equals(that.impressionId) : that.impressionId != null) return false;
        if (baseReview != null ? !baseReview.equals(that.baseReview) : that.baseReview != null) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = baseReview != null ? baseReview.hashCode() : 0;
        result = 31 * result + (impressionId != null ? impressionId.hashCode() : 0);
        result = 31 * result + referenceId;
        result = 31 * result + sourceId;
        result = 31 * result + locationId;
        result = 31 * result + (businessName != null ? businessName.hashCode() : 0);
        result = 31 * result + (authorUrl != null ? authorUrl.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("review=").append(baseReview);
        sb.append(",impressionId=").append(impressionId);
        sb.append(",referenceId=").append(referenceId);
        sb.append(",sourceId=").append(sourceId);
        sb.append(",locationId=").append(locationId);
        sb.append(",businessName=").append(businessName);
        sb.append(",authorUrl=").append(authorUrl);
        sb.append(",type=").append(type);
        sb.append('>');
        return sb.toString();
    }
}
