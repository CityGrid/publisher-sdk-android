/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.offers.detail;

import com.citygrid.content.offers.CGOffersOffer;

public class CGOffersDetailResults {
    private CGOffersOffer offer;

    public CGOffersDetailResults(CGOffersOffer offer) {
        this.offer = offer;
    }

    public CGOffersOffer getOffer() {
        return offer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGOffersDetailResults)) return false;

        CGOffersDetailResults that = (CGOffersDetailResults) o;

        if (offer != null ? !offer.equals(that.offer) : that.offer != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return offer != null ? offer.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("offer=").append(offer);
        sb.append('>');
        return sb.toString();
    }
}
