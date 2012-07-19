package com.citygrid.content.places.detail;

import com.citygrid.*;
import com.citygrid.ads.tracker.CGAdsTracker;
import com.citygrid.ads.tracker.CGAdsTrackerActionTarget;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;

import static com.citygrid.CGBuilder.isEmpty;

public class CGPlacesDetailLocation implements CGLocation, Serializable {
    private CGBaseLocation baseLocation;
    private int referenceId;
    private boolean displayAd;
    private int infoUsaId;
    private String publicId;
    private String teaser;
    private String callPhone;
    private URI displayUrl;
    private String[] markets;
    private String[] neighborhoods;
    private CGPlacesDetailUrls urls;
    private CGPlacesDetailCustomerContent customerContent;
    private CGPlacesDetailOffer[] offers;
    private CGPlacesDetailCategory[] categories;
    private CGPlacesDetailAttribute[] attributes;
    private String businessHours;
    private String parking;
    private CGPlacesDetailTip[] tips;
    private CGPlacesDetailImage[] images;
    private CGPlacesDetailEditorial[] editorials;
    private CGPlacesDetailReviews reviews;

    private CGPlacesDetailLocation(Builder builder) {
        baseLocation = builder.baseLocation;
        referenceId = builder.referenceId;
        displayAd = builder.displayAd;
        infoUsaId = builder.infoUsaId;
        publicId = builder.publicId;
        teaser = builder.teaser;
        callPhone = builder.callPhone;
        displayUrl = builder.displayUrl;
        markets = builder.markets;
        neighborhoods = builder.neighborhoods;
        urls = builder.urls;
        customerContent = builder.customerContent;
        offers = builder.offers;
        categories = builder.categories;
        attributes = builder.attributes;
        businessHours = builder.businessHours;
        parking = builder.parking;
        tips = builder.tips;
        images = builder.images;
        editorials = builder.editorials;
        reviews = builder.reviews;
    }


    public static class Builder {
        public CGBaseLocation baseLocation;
        private int referenceId;
        private boolean displayAd;
        private int infoUsaId;
        private String publicId;
        private String teaser;
        private String callPhone;
        private URI displayUrl;
        private String[] markets;
        private String[] neighborhoods;
        private CGPlacesDetailUrls urls;
        private CGPlacesDetailCustomerContent customerContent;
        private CGPlacesDetailOffer[] offers;
        private CGPlacesDetailCategory[] categories;
        private CGPlacesDetailAttribute[] attributes;
        private String businessHours;
        private String parking;
        private CGPlacesDetailTip[] tips;
        private CGPlacesDetailImage[] images;
        private CGPlacesDetailEditorial[] editorials;
        private CGPlacesDetailReviews reviews;

        public Builder baseLocation(CGBaseLocation baseLocation) {
            this.baseLocation = baseLocation;
            return this;
        }

        public Builder referenceId(int referenceId) {
            this.referenceId = referenceId;
            return this;
        }

        public Builder displayAd(boolean displayAd) {
            this.displayAd = displayAd;
            return this;
        }

        public Builder infoUsaId(int infoUsaId) {
            this.infoUsaId = infoUsaId;
            return this;
        }
        
        public Builder publicId(String publicId) {
            this.publicId = publicId;
            return this;
        }

        public Builder teaser(String teaser) {
            this.teaser = teaser;
            return this;
        }

        public Builder callPhone(String callPhone) {
            this.callPhone = callPhone;
            return this;
        }

        public Builder displayUrl(URI displayUrl) {
            this.displayUrl = displayUrl;
            return this;
        }

        public Builder markets(String[] markets) {
            this.markets = markets;
            return this;
        }

        public Builder neighborhoods(String[] neighborhoods) {
            this.neighborhoods = neighborhoods;
            return this;
        }

        public Builder urls(CGPlacesDetailUrls urls) {
            this.urls = urls;
            return this;
        }

        public Builder customerContent(CGPlacesDetailCustomerContent customerContent) {
            this.customerContent = customerContent;
            return this;
        }

        public Builder offers(CGPlacesDetailOffer[] offers) {
            this.offers = offers;
            return this;
        }

        public Builder categories(CGPlacesDetailCategory[] categories) {
            this.categories = categories;
            return this;
        }

        public Builder attributes(CGPlacesDetailAttribute[] attributes) {
            this.attributes = attributes;
            return this;
        }

        public Builder businessHours(String businessHours) {
            this.businessHours = businessHours;
            return this;
        }

        public Builder parking(String parking) {
            this.parking = parking;
            return this;
        }

        public Builder tips(CGPlacesDetailTip[] tips) {
            this.tips = tips;
            return this;
        }

        public Builder images(CGPlacesDetailImage[] images) {
            this.images = images;
            return this;
        }

        public Builder editorials(CGPlacesDetailEditorial[] editorials) {
            this.editorials = editorials;
            return this;
        }

        public Builder reviews(CGPlacesDetailReviews reviews) {
            this.reviews = reviews;
            return this;
        }

        public CGPlacesDetailLocation build() {
            return new CGPlacesDetailLocation(this);
        }
    }

    public int getLocationId() {
        return baseLocation.getLocationId();
    }

    public String getImpressionId() {
        return baseLocation.getImpressionId();
    }

    public String getName() {
        return baseLocation.getName();
    }

    public CGAddress getAddress() {
        return baseLocation.getAddress();
    }

    public CGLatLon getLatlon() {
        return baseLocation.getLatlon();
    }

    public URI getImage() {
        return baseLocation.getImage();
    }

    public String getPhone() {
        return baseLocation.getPhone();
    }

    public int getRating() {
        return baseLocation.getRating();
    }

    public int getReferenceId() {
        return referenceId;
    }

    public boolean isDisplayAd() {
        return displayAd;
    }

    public int getInfoUsaId() {
        return infoUsaId;
    }
    
    public String getPublicId() {
        return publicId;
    }

    public String getTeaser() {
        return teaser;
    }

    public String getCallPhone() {
        return callPhone;
    }

    public URI getDisplayUrl() {
        return displayUrl;
    }

    public String[] getMarkets() {
        return markets;
    }

    public String[] getNeighborhoods() {
        return neighborhoods;
    }

    public CGPlacesDetailUrls getUrls() {
        return urls;
    }

    public CGPlacesDetailCustomerContent getCustomerContent() {
        return customerContent;
    }

    public CGPlacesDetailOffer[] getOffers() {
        return offers;
    }

    public CGPlacesDetailCategory[] getCategories() {
        return categories;
    }

    public CGPlacesDetailAttribute[] getAttributes() {
        return attributes;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public String getParking() {
        return parking;
    }

    public CGPlacesDetailTip[] getTips() {
        return tips;
    }

    public CGPlacesDetailImage[] getImages() {
        return images;
    }

    public CGPlacesDetailEditorial[] getEditorials() {
        return editorials;
    }

    public CGPlacesDetailReviews getReviews() {
        return reviews;
    }

    /**
     * Convenience method to provider a builder for Ads tracking, i.e. {@link CGAdsTracker} for current location.
     */
    public CGAdsTracker adsTracker(String muid, String mobileType) throws CGException {
        if (isEmpty(muid) || isEmpty(mobileType)) {
            throw new CGException(new CGError(CGErrorNum.MuidRequired, "MUID and Mobile Type are required for Ad tracking"));
        }
        CGAdsTracker tracker = CityGrid.adsTracker();
        tracker.setMuid(muid);
        tracker.setMobileType(mobileType);
        tracker.setLocationId(getLocationId());
        tracker.setImpressionId(getImpressionId());
        tracker.setReferenceId(getReferenceId());
        tracker.setDialPhone(
                CGBuilder.isNotEmpty(getCallPhone()) ? getCallPhone() : getPhone());
        return tracker;
    }

    /**
     * Convenience method to track current location with a {@link CGAdsTrackerActionTarget}
     *
     * @param actionTarget
     * @throws CGException
     */
    public void track(String muid, String mobileType, CGAdsTrackerActionTarget actionTarget) throws CGException {
        CGAdsTracker tracker = adsTracker(muid, mobileType);
        tracker.setActionTarget(actionTarget);
        tracker.track();
    }

    /**
     * Convenience method to track current location with a {@link CGAdsTrackerActionTarget} and a placement
     *
     * @param actionTarget
     * @param placement
     * @throws CGException
     */
    public void track(String muid, String mobileType, CGAdsTrackerActionTarget actionTarget, String placement) throws CGException {
        CGAdsTracker tracker = adsTracker(muid, mobileType);
        tracker.setActionTarget(actionTarget);
        tracker.setPlacement(placement);
        tracker.track();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetailLocation)) return false;

        CGPlacesDetailLocation that = (CGPlacesDetailLocation) o;

        if (displayAd != that.displayAd) return false;
        if (infoUsaId != that.infoUsaId) return false;
        if (publicId != null ? !publicId.equals(that.publicId) : that.publicId != null) return false;
        if (referenceId != that.referenceId) return false;
        if (!Arrays.equals(attributes, that.attributes)) return false;
        if (baseLocation != null ? !baseLocation.equals(that.baseLocation) : that.baseLocation != null) return false;
        if (businessHours != null ? !businessHours.equals(that.businessHours) : that.businessHours != null)
            return false;
        if (callPhone != null ? !callPhone.equals(that.callPhone) : that.callPhone != null) return false;
        if (!Arrays.equals(categories, that.categories)) return false;
        if (customerContent != null ? !customerContent.equals(that.customerContent) : that.customerContent != null)
            return false;
        if (displayUrl != null ? !displayUrl.equals(that.displayUrl) : that.displayUrl != null) return false;
        if (!Arrays.equals(editorials, that.editorials)) return false;
        if (!Arrays.equals(images, that.images)) return false;
        if (!Arrays.equals(markets, that.markets)) return false;
        if (!Arrays.equals(neighborhoods, that.neighborhoods)) return false;
        if (!Arrays.equals(offers, that.offers)) return false;
        if (parking != null ? !parking.equals(that.parking) : that.parking != null) return false;
        if (reviews != null ? !reviews.equals(that.reviews) : that.reviews != null) return false;
        if (teaser != null ? !teaser.equals(that.teaser) : that.teaser != null) return false;
        if (!Arrays.equals(tips, that.tips)) return false;
        if (urls != null ? !urls.equals(that.urls) : that.urls != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = baseLocation != null ? baseLocation.hashCode() : 0;
        result = 31 * result + referenceId;
        result = 31 * result + (displayAd ? 1 : 0);
        result = 31 * result + infoUsaId;
        result = 31 * result + (publicId != null ? publicId.hashCode() : 0);
        result = 31 * result + (teaser != null ? teaser.hashCode() : 0);
        result = 31 * result + (callPhone != null ? callPhone.hashCode() : 0);
        result = 31 * result + (displayUrl != null ? displayUrl.hashCode() : 0);
        result = 31 * result + (markets != null ? Arrays.hashCode(markets) : 0);
        result = 31 * result + (neighborhoods != null ? Arrays.hashCode(neighborhoods) : 0);
        result = 31 * result + (urls != null ? urls.hashCode() : 0);
        result = 31 * result + (customerContent != null ? customerContent.hashCode() : 0);
        result = 31 * result + (offers != null ? Arrays.hashCode(offers) : 0);
        result = 31 * result + (categories != null ? Arrays.hashCode(categories) : 0);
        result = 31 * result + (attributes != null ? Arrays.hashCode(attributes) : 0);
        result = 31 * result + (businessHours != null ? businessHours.hashCode() : 0);
        result = 31 * result + (parking != null ? parking.hashCode() : 0);
        result = 31 * result + (tips != null ? Arrays.hashCode(tips) : 0);
        result = 31 * result + (images != null ? Arrays.hashCode(images) : 0);
        result = 31 * result + (editorials != null ? Arrays.hashCode(editorials) : 0);
        result = 31 * result + (reviews != null ? reviews.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("baseLocation=").append(baseLocation);
        sb.append(",referenceId=").append(referenceId);
        sb.append(",displayAd=").append(displayAd);
        sb.append(",infoUsaId=").append(infoUsaId);
        sb.append(",publicId=").append(publicId);
        sb.append(",teaser=").append(teaser);
        sb.append(",callPhone=").append(callPhone);
        sb.append(",displayUrl=").append(displayUrl);
        sb.append(",markets=").append(markets == null ? "null" : Arrays.toString(markets));
        sb.append(",neighborhoods=").append(neighborhoods == null ? "null" : Arrays.toString(neighborhoods));
        sb.append(",urls=").append(urls);
        sb.append(",customerContent=").append(customerContent);
        sb.append(",offers=").append(offers == null ? "null" : Arrays.toString(offers));
        sb.append(",categories=").append(categories == null ? "null" : Arrays.toString(categories));
        sb.append(",attributes=").append(attributes == null ? "null" : Arrays.toString(attributes));
        sb.append(",businessHours=").append(businessHours);
        sb.append(",parking=").append(parking);
        sb.append(",tips=").append(tips == null ? "null" : Arrays.toString(tips));
        sb.append(",images=").append(images == null ? "null" : Arrays.toString(images));
        sb.append(",editorials=").append(editorials == null ? "null" : Arrays.toString(editorials));
        sb.append(",reviews=").append(reviews);
        sb.append('>');
        return sb.toString();
    }
}
