/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.ads.mobile;

import java.io.Serializable;
import java.util.Arrays;

public class CGAdsMobileResults implements Serializable {
    private CGAdsMobileAd[] ads;

    public CGAdsMobileResults(CGAdsMobileAd[] ads) {
        this.ads = ads;
    }

    public CGAdsMobileAd[] getAds() {
        return ads;
    }

    public CGAdsMobileAd getAd() {
        if (ads != null && ads.length > 0) {
            return ads[0];
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGAdsMobileResults)) return false;

        CGAdsMobileResults that = (CGAdsMobileResults) o;

        if (!Arrays.equals(ads, that.ads)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return ads != null ? Arrays.hashCode(ads) : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("ads=").append(ads == null ? "null" : Arrays.toString(ads));
        sb.append('>');
        return sb.toString();
    }
}
