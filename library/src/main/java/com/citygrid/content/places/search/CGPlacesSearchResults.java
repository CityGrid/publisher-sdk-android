/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.places.search;

import com.citygrid.CGHistogram;
import com.citygrid.CGRegion;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;

public class CGPlacesSearchResults implements Serializable {
	private int firstHit;
	private int lastHit;
	private int totalHits;
	private int page;
	private int resultsPerPage;
	private URI uri;
	private String didYouMean;
	private CGRegion[] regions;
	private CGPlacesSearchLocation[] locations;
	private CGHistogram[] histograms;

    private CGPlacesSearchResults(Builder builder) {
        firstHit = builder.firstHit;
        lastHit = builder.lastHit;
        totalHits = builder.totalHits;
        page = builder.page;
        resultsPerPage = builder.resultsPerPage;
        uri = builder.uri;
        didYouMean = builder.didYouMean;
        regions = builder.regions;
        locations = builder.locations;
        histograms = builder.histograms;
    }

    public static class Builder {
        private int firstHit;
        private int lastHit;
        private int totalHits;
        private int page;
        private int resultsPerPage;
        private URI uri;
        private String didYouMean;
        private CGRegion[] regions;
        private CGPlacesSearchLocation[] locations;
        private CGHistogram[] histograms;

        public Builder firstHit(int val) {
            firstHit = val;
            return this;
        }

        public Builder latHit(int val) {
            lastHit = val;
            return this;
        }

        public Builder totalHits(int val) {
            totalHits = val;
            return this;
        }

        public Builder page(int val) {
            page = val;
            return this;
        }

        public Builder resultsPerPage(int val) {
            resultsPerPage = val;
            return this;
        }

        public Builder uri(URI val) {
            uri = val;
            return this;
        }

        public Builder didYouMean(String val) {
            didYouMean = val;
            return this;
        }

        public Builder regions(CGRegion[] val) {
            regions = val;
            return this;
        }

        public Builder locations(CGPlacesSearchLocation[] val) {
            locations = val;
            return this;
        }

        public Builder histograms(CGHistogram[] val) {
            histograms = val;
            return this;
        }

        public CGPlacesSearchResults build() {
            return new CGPlacesSearchResults(this);
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

    public CGPlacesSearchLocation[] getLocations() {
        return locations;
    }

    public CGHistogram[] getHistograms() {
        return histograms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesSearchResults)) return false;

        CGPlacesSearchResults that = (CGPlacesSearchResults) o;

        if (firstHit != that.firstHit) return false;
        if (lastHit != that.lastHit) return false;
        if (page != that.page) return false;
        if (resultsPerPage != that.resultsPerPage) return false;
        if (totalHits != that.totalHits) return false;
        if (didYouMean != null ? !didYouMean.equals(that.didYouMean) : that.didYouMean != null) return false;
        if (!Arrays.equals(histograms, that.histograms)) return false;
        if (!Arrays.equals(locations, that.locations)) return false;
        if (!Arrays.equals(regions, that.regions)) return false;
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
        result = 31 * result + (locations != null ? Arrays.hashCode(locations) : 0);
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
        sb.append(",locations=").append(locations == null ? "null" : Arrays.toString(locations));
        sb.append(",histograms=").append(histograms == null ? "null" : Arrays.toString(histograms));
        sb.append('>');
        return sb.toString();
    }
}
