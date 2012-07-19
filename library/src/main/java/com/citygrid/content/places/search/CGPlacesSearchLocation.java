/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.places.search;

import com.citygrid.*;
import com.citygrid.content.places.detail.CGPlacesDetail;
import com.citygrid.content.places.detail.CGPlacesDetailLocation;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;

public class CGPlacesSearchLocation implements CGLocation, CGLocationDetailProvider, Serializable {

    private CGBaseLocation baseLocation;
    private boolean featured;
    private String publicId;
	private String neighborhood;
	private String fax;
	private URI profile;
	private URI website;
	private boolean hasVideo;
	private boolean hasOffers;
	private String offers;
	private int reviews;
	private String[] categories;
	private String tagline;
	private CGTag[] tags;

    public static class Builder {
        private CGBaseLocation baseLocation;
        private boolean featured;
        private String publicId;
        private String neighborhood;
        private String fax;
        private URI profile;
        private URI website;
        private boolean hasVideo;
        private boolean hasOffers;
        private String offers;
        private int reviews;
        private String[] categories;
        private String tagline;

        public Builder baseLocation(CGBaseLocation baseLocation) {
            this.baseLocation = baseLocation;
            return this;
        }

        private CGTag[] tags;

        public Builder featured(boolean featured) {
            this.featured = featured;
            return this;
        }
        
        public Builder publicId(String publicId) {
            this.publicId = publicId;
            return this;
        }
        
        public Builder neighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
            return this;
        }

        public Builder fax(String fax) {
            this.fax = fax;
            return this;
        }

        public Builder profile(URI profile) {
            this.profile = profile;
            return this;
        }

        public Builder website(URI website) {
            this.website = website;
            return this;
        }

        public Builder hasVideo(boolean hasVideo) {
            this.hasVideo = hasVideo;
            return this;
        }

        public Builder hasOffers(boolean hasOffers) {
            this.hasOffers = hasOffers;
            return this;
        }

        public Builder offers(String offers) {
            this.offers = offers;
            return this;
        }

        public Builder reviews(int reviews) {
            this.reviews = reviews;
            return this;
        }

        public Builder categories(String[] categories) {
            this.categories = categories;
            return this;
        }

        public Builder tagline(String tagline) {
            this.tagline = tagline;
            return this;
        }

        public Builder tags(CGTag[] tags) {
            this.tags = tags;
            return this;
        }

        public CGPlacesSearchLocation build() {
            return new CGPlacesSearchLocation(this);
        }
    }

    private CGPlacesSearchLocation(Builder builder) {
        baseLocation = builder.baseLocation;
        featured = builder.featured;
        publicId = builder.publicId;
        neighborhood = builder.neighborhood;
        fax = builder.fax;
        profile = builder.profile;
        website = builder.website;
        hasVideo = builder.hasVideo;
        hasOffers = builder.hasOffers;
        offers = builder.offers;
        reviews = builder.reviews;
        categories = builder.categories;
        tagline = builder.tagline;
        tags = builder.tags;
    }

    public boolean isFeatured() {
        return featured;
    }

    public String getPublicId() {
        return publicId;
    }
    
    public String getNeighborhood() {
        return neighborhood;
    }

    public String getFax() {
        return fax;
    }

    public URI getProfile() {
        return profile;
    }

    public URI getWebsite() {
        return website;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public boolean isHasOffers() {
        return hasOffers;
    }

    public String getOffers() {
        return offers;
    }

    public int getReviews() {
        return reviews;
    }

    public String[] getCategories() {
        return categories;
    }

    public String getTagline() {
        return tagline;
    }

    public CGTag[] getTags() {
        return tags;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public CGPlacesDetail placesDetail() {
        return baseLocation.placesDetail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CGPlacesDetailLocation placesDetailLocation() throws CGException {
        return baseLocation.placesDetailLocation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesSearchLocation)) return false;

        CGPlacesSearchLocation that = (CGPlacesSearchLocation) o;

        if (featured != that.featured) return false;
        if (hasOffers != that.hasOffers) return false;
        if (hasVideo != that.hasVideo) return false;
        if (reviews != that.reviews) return false;
        if (baseLocation != null ? !baseLocation.equals(that.baseLocation) : that.baseLocation != null) return false;
        if (!Arrays.equals(categories, that.categories)) return false;
        if (publicId != null ? !publicId.equals(that.publicId) : that.publicId != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (neighborhood != null ? !neighborhood.equals(that.neighborhood) : that.neighborhood != null) return false;
        if (offers != null ? !offers.equals(that.offers) : that.offers != null) return false;
        if (profile != null ? !profile.equals(that.profile) : that.profile != null) return false;
        if (tagline != null ? !tagline.equals(that.tagline) : that.tagline != null) return false;
        if (!Arrays.equals(tags, that.tags)) return false;
        if (website != null ? !website.equals(that.website) : that.website != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = baseLocation != null ? baseLocation.hashCode() : 0;
        result = 31 * result + (featured ? 1 : 0);
        result = 31 * result + (publicId != null ? publicId.hashCode() : 0);
        result = 31 * result + (neighborhood != null ? neighborhood.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (hasVideo ? 1 : 0);
        result = 31 * result + (hasOffers ? 1 : 0);
        result = 31 * result + (offers != null ? offers.hashCode() : 0);
        result = 31 * result + reviews;
        result = 31 * result + (categories != null ? Arrays.hashCode(categories) : 0);
        result = 31 * result + (tagline != null ? tagline.hashCode() : 0);
        result = 31 * result + (tags != null ? Arrays.hashCode(tags) : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("baseLocation=").append(baseLocation);
        sb.append(",featured=").append(featured);
        sb.append(",publicId=").append(publicId);
        sb.append(",neighborhood=").append(neighborhood);
        sb.append(",fax=").append(fax);
        sb.append(",profile=").append(profile);
        sb.append(",website=").append(website);
        sb.append(",hasVideo=").append(hasVideo);
        sb.append(",hasOffers=").append(hasOffers);
        sb.append(",offers=").append(offers);
        sb.append(",reviews=").append(reviews);
        sb.append(",categories=").append(categories == null ? "null" : Arrays.toString(categories));
        sb.append(",tagline=").append(tagline);
        sb.append(",tags=").append(tags == null ? "null" : Arrays.toString(tags));
        sb.append('>');
        return sb.toString();
    } 
}
