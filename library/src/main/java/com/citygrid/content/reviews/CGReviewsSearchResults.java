/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.reviews;

import com.citygrid.CGHistogram;
import com.citygrid.CGRegion;

import java.net.URI;
import java.util.Arrays;

public class CGReviewsSearchResults {
    private int firstHit;
    private int lastHit;
    private int totalHits;
    private int page;
    private int resultsPerPage;
    private URI uri;
    private String didYouMean;
    private CGRegion[] regions;
    private CGReviewsSearchReview[] reviews;
    private CGHistogram[] histograms;

    private CGReviewsSearchResults(Builder builder) {
        firstHit = builder.firstHit;
        lastHit = builder.lastHit;
        totalHits = builder.totalHits;
        page = builder.page;
        resultsPerPage = builder.resultsPerPage;
        uri = builder.uri;
        didYouMean = builder.didYouMean;
        regions = builder.regions;
        reviews = builder.reviews;
        histograms = builder.histograms;
    }

    public static class Builder {
        private int firstHit;
        private int lastHit;
        private int totalHits;
        private int page;
        private int resultsPerPage;
        URI uri;
        private String didYouMean;
        CGRegion[] regions;
        CGReviewsSearchReview[] reviews;
        CGHistogram[] histograms;

        public CGReviewsSearchResults build() {
            return new CGReviewsSearchResults(this);
        }

        public Builder firstHit(int firstHit) {
            this.firstHit = firstHit;
            return this;
        }

        public Builder lastHit(int lastHit) {
            this.lastHit = lastHit;
            return this;
        }

        public Builder totalHits(int totalHits) {
            this.totalHits = totalHits;
            return this;
        }

        public Builder page(int page) {
            this.page = page;
            return this;
        }

        public Builder resultsPerPage(int resultsPerPage) {
            this.resultsPerPage = resultsPerPage;
            return this;
        }

        public Builder uri(URI uri) {
            this.uri = uri;
            return this;
        }

        public Builder didYouMean(String didYouMean) {
            this.didYouMean = didYouMean;
            return this;
        }

        public Builder regions(CGRegion[] regions) {
            this.regions = regions;
            return this;
        }

        public Builder reviews(CGReviewsSearchReview[] reviews) {
            this.reviews = reviews;
            return this;
        }

        public Builder histograms(CGHistogram[] histograms) {
            this.histograms = histograms;
            return this;
        }
    }

    public int getFirstHit() {
        return firstHit;
    }

    public int getLastHit() {
        return lastHit;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public int getPage() {
        return page;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public URI getUri() {
        return uri;
    }

    public String getDidYouMean() {
        return didYouMean;
    }

    public CGRegion[] getRegions() {
        return regions;
    }

    public CGReviewsSearchReview[] getReviews() {
        return reviews;
    }

    public CGHistogram[] getHistograms() {
        return histograms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGReviewsSearchResults)) return false;

        CGReviewsSearchResults that = (CGReviewsSearchResults) o;

        if (firstHit != that.firstHit) return false;
        if (lastHit != that.lastHit) return false;
        if (page != that.page) return false;
        if (resultsPerPage != that.resultsPerPage) return false;
        if (totalHits != that.totalHits) return false;
        if (didYouMean != null ? !didYouMean.equals(that.didYouMean) : that.didYouMean != null) return false;
        if (!Arrays.equals(histograms, that.histograms)) return false;
        if (!Arrays.equals(regions, that.regions)) return false;
        if (!Arrays.equals(reviews, that.reviews)) return false;
        if (uri != null ? !uri.equals(that.uri) : that.uri != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstHit;
        result = 31 * result + lastHit;
        result = 31 * result + totalHits;
        result = 31 * result + page;
        result = 31 * result + resultsPerPage;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        result = 31 * result + (didYouMean != null ? didYouMean.hashCode() : 0);
        result = 31 * result + (regions != null ? Arrays.hashCode(regions) : 0);
        result = 31 * result + (reviews != null ? Arrays.hashCode(reviews) : 0);
        result = 31 * result + (histograms != null ? Arrays.hashCode(histograms) : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("firstHit=").append(firstHit);
        sb.append(",lastHit=").append(lastHit);
        sb.append(",totalHits=").append(totalHits);
        sb.append(",page=").append(page);
        sb.append(",resultsPerPage=").append(resultsPerPage);
        sb.append(",uri=").append(uri);
        sb.append(",didYouMean=").append(didYouMean);
        sb.append(",regions=").append(regions == null ? "null" : Arrays.toString(regions));
        sb.append(",reviews=").append(reviews == null ? "null" : Arrays.toString(reviews));
        sb.append(",histograms=").append(histograms == null ? "null" : Arrays.toString(histograms));
        sb.append('>');
        return sb.toString();
    }
}
