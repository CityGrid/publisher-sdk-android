/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.offers;

import com.citygrid.CGException;
import com.citygrid.CityGrid;
import com.citygrid.content.offers.detail.CGOffersDetail;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;

public class CGOffersOffer {
    private String offerId;
    private int referenceId;
    private String impressionId;
    private String title;
    private String offerDescription;
    private int redemptionTypeId;
    private String redemptionType;
    private URI redemptionUrl;
    private String terms;
    private String source;
    private CGOffersType[] types;
    private URI imageUrl;
    private Date startDate;
    private Date expirationDate;
    private int popularity;
    private float faceValue;
    private float discountValue;
    private CGOffersLocation[] locations; // CGOffersLocation
    private String attributionSource;
    private URI attributionLogo;

    public CGOffersOffer(Builder builder) {
        offerId = builder.offerId;
        referenceId = builder.referenceId;
        impressionId = builder.impressionId;
        title = builder.title;
        offerDescription = builder.offerDescription;
        redemptionTypeId = builder.redemptionTypeId;
        redemptionType = builder.redemptionType;
        redemptionUrl = builder.redemptionUrl;
        terms = builder.terms;
        source = builder.source;
        types = builder.types;
        imageUrl = builder.imageUrl;
        startDate = builder.startDate;
        expirationDate = builder.expirationDate;
        popularity = builder.popularity;
        faceValue = builder.faceValue;
        discountValue = builder.discountValue;
        locations = builder.locations;
        attributionSource = builder.attributionSource;
        attributionLogo = builder.attributionLogo;
    }

    public static class Builder {
        private String offerId;
        private int referenceId;
        private String impressionId;
        private String title;
        private String offerDescription;
        private int redemptionTypeId;
        private String redemptionType;
        private URI redemptionUrl;
        private String terms;
        private String source;
        private CGOffersType[] types; // NSNumber of CGOffersType
        private URI imageUrl;
        private Date startDate;
        private Date expirationDate;
        private int popularity;
        private float faceValue;
        private float discountValue;
        private CGOffersLocation[] locations; // CGOffersLocation
        private String attributionSource;
        private URI attributionLogo;

        public CGOffersOffer build() {
            return new CGOffersOffer(this);
        }

        public Builder offerId(String offerId) {
            this.offerId = offerId;
            return this;
        }

        public Builder referenceId(int referenceId) {
            this.referenceId = referenceId;
            return this;
        }

        public Builder impressionId(String impressionId) {
            this.impressionId = impressionId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder offerDescription(String offerDescription) {
            this.offerDescription = offerDescription;
            return this;
        }

        public Builder redemptionTypeId(int redemptionTypeId) {
            this.redemptionTypeId = redemptionTypeId;
            return this;
        }

        public Builder redemptionType(String redemptionType) {
            this.redemptionType = redemptionType;
            return this;
        }

        public Builder redemptionUrl(URI redemptionUrl) {
            this.redemptionUrl = redemptionUrl;
            return this;
        }

        public Builder terms(String terms) {
            this.terms = terms;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder types(CGOffersType[] types) {
            this.types = types;
            return this;
        }

        public Builder imageUrl(URI imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder expirationDate(Date expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder popularity(int popularity) {
            this.popularity = popularity;
            return this;
        }

        public Builder faceValue(float faceValue) {
            this.faceValue = faceValue;
            return this;
        }

        public Builder discountValue(float discountValue) {
            this.discountValue = discountValue;
            return this;
        }

        public Builder locations(CGOffersLocation[] locations) {
            this.locations = locations;
            return this;
        }

        public Builder attributionSource(String attributionSource) {
            this.attributionSource = attributionSource;
            return this;
        }

        public Builder attributionLogo(URI attributionLogo) {
            this.attributionLogo = attributionLogo;
            return this;
        }
    }

    public String getOfferId() {
        return offerId;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public String getImpressionId() {
        return impressionId;
    }

    public String getTitle() {
        return title;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public int getRedemptionTypeId() {
        return redemptionTypeId;
    }

    public String getRedemptionType() {
        return redemptionType;
    }

    public URI getRedemptionUrl() {
        return redemptionUrl;
    }

    public String getTerms() {
        return terms;
    }

    public String getSource() {
        return source;
    }

    public CGOffersType[] getTypes() {
        return types;
    }

    public URI getImageUrl() {
        return imageUrl;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public int getPopularity() {
        return popularity;
    }

    public float getFaceValue() {
        return faceValue;
    }

    public float getDiscountValue() {
        return discountValue;
    }

    public CGOffersLocation[] getLocations() {
        return locations;
    }

    public String getAttributionSource() {
        return attributionSource;
    }

    public URI getAttributionLogo() {
        return attributionLogo;
    }

    /**
     * Convenience method that provides a builder {@link CGOffersDetail} for current offer.
     */
    public CGOffersDetail offersDetail() {
        CGOffersDetail detail = CityGrid.offersDetail();
        detail.setOfferId(getOfferId());
        detail.setImpressionId(getImpressionId());
        return detail;
    }

    /**
     * Convenience method that retrieves details of current offer.
     */
    public CGOffersOffer offersDetailOffer() throws CGException {
        return this.offersDetail().detail().getOffer();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGOffersOffer)) return false;

        CGOffersOffer that = (CGOffersOffer) o;

        if (Float.compare(that.discountValue, discountValue) != 0) return false;
        if (Float.compare(that.faceValue, faceValue) != 0) return false;
        if (popularity != that.popularity) return false;
        if (redemptionTypeId != that.redemptionTypeId) return false;
        if (referenceId != that.referenceId) return false;
        if (attributionLogo != null ? !attributionLogo.equals(that.attributionLogo) : that.attributionLogo != null)
            return false;
        if (attributionSource != null ? !attributionSource.equals(that.attributionSource) : that.attributionSource != null)
            return false;
        if (expirationDate != null ? !expirationDate.equals(that.expirationDate) : that.expirationDate != null)
            return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null) return false;
        if (impressionId != null ? !impressionId.equals(that.impressionId) : that.impressionId != null) return false;
        if (!Arrays.equals(locations, that.locations)) return false;
        if (offerDescription != null ? !offerDescription.equals(that.offerDescription) : that.offerDescription != null)
            return false;
        if (offerId != null ? !offerId.equals(that.offerId) : that.offerId != null) return false;
        if (redemptionType != null ? !redemptionType.equals(that.redemptionType) : that.redemptionType != null)
            return false;
        if (redemptionUrl != null ? !redemptionUrl.equals(that.redemptionUrl) : that.redemptionUrl != null)
            return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (terms != null ? !terms.equals(that.terms) : that.terms != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (!Arrays.equals(types, that.types)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = offerId != null ? offerId.hashCode() : 0;
        result = 31 * result + referenceId;
        result = 31 * result + (impressionId != null ? impressionId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (offerDescription != null ? offerDescription.hashCode() : 0);
        result = 31 * result + redemptionTypeId;
        result = 31 * result + (redemptionType != null ? redemptionType.hashCode() : 0);
        result = 31 * result + (redemptionUrl != null ? redemptionUrl.hashCode() : 0);
        result = 31 * result + (terms != null ? terms.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (types != null ? Arrays.hashCode(types) : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = 31 * result + popularity;
        result = 31 * result + (faceValue != +0.0f ? Float.floatToIntBits(faceValue) : 0);
        result = 31 * result + (discountValue != +0.0f ? Float.floatToIntBits(discountValue) : 0);
        result = 31 * result + (locations != null ? Arrays.hashCode(locations) : 0);
        result = 31 * result + (attributionSource != null ? attributionSource.hashCode() : 0);
        result = 31 * result + (attributionLogo != null ? attributionLogo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("offerId=").append(offerId);
        sb.append(",referenceId=").append(referenceId);
        sb.append(",impressionId=").append(impressionId);
        sb.append(",title=").append(title);
        sb.append(",offerDescription=").append(offerDescription);
        sb.append(",redemptionTypeId=").append(redemptionTypeId);
        sb.append(",redemptionType=").append(redemptionType);
        sb.append(",redemptionUrl=").append(redemptionUrl);
        sb.append(",terms=").append(terms);
        sb.append(",source=").append(source);
        sb.append(",types=").append(types == null ? "null" : Arrays.toString(types));
        sb.append(",imageUrl=").append(imageUrl);
        sb.append(",startDate=").append(startDate);
        sb.append(",expirationDate=").append(expirationDate);
        sb.append(",popularity=").append(popularity);
        sb.append(",faceValue=").append(faceValue);
        sb.append(",discountValue=").append(discountValue);
        sb.append(",locations=").append(locations == null ? "null" : Arrays.toString(locations));
        sb.append(",attributionSource=").append(attributionSource);
        sb.append(",attributionLogo=").append(attributionLogo);
        sb.append('>');
        return sb.toString();
    }
}
