/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import com.citygrid.ads.custom.CGAdsCustom;
import com.citygrid.ads.mobile.CGAdsMobile;
import com.citygrid.ads.tracker.CGAdsTracker;
import com.citygrid.content.offers.detail.CGOffersDetail;
import com.citygrid.content.offers.search.CGOffersSearch;
import com.citygrid.content.places.detail.CGPlacesDetail;
import com.citygrid.content.places.search.CGPlacesSearch;
import com.citygrid.content.reviews.CGReviewsSearch;

/**
 * CityGrid SDK
 * <p></p>
 * <p>A Java library that provides an interface to the CityGrid REST API's as
 * documented at http://docs.citygridmedia.com/display/citygridv2/CityGrid+APIs.
 */
public class CityGrid {

    /**
     * Gets the global publisher that identifies a developer.
     *
     * @return
     */
    public static String getPublisher() {
		return CGShared.getSharedInstance().getPublisher();
	}

    /**
     * Sets the global publisher that identifies a developer.
     *
     * @param publisher
     */
    public static void setPublisher(String publisher) {
        CGShared.getSharedInstance().setPublisher(publisher);
    }

    /**
     * Sets the global placement that assists a developer in breakdowns for publisher reports
     *
     * @param placement
     */
    public static void setPlacement(String placement) {
        CGShared.getSharedInstance().setPlacement(placement);
    }

    /**
     * Gets the global placement that assists a developer in breakdowns for publisher reports
     *
     * @return
     */
	public static String getPlacement() {
		return CGShared.getSharedInstance().getPlacement();
	}

    /**
     * Sets the global network timeout for all server calls, in milliseconds.
     *
     * @param connectTimeout
     */
	public static void setConnectTimeout(int connectTimeout) {
		CGShared.getSharedInstance().setConnectTimeout(connectTimeout);
	}

    /**
     * Gets the global network connect timeout for all server calls, in milliseconds.
     * @return
     */
	public static int getConnectTimeout() {
		return CGShared.getSharedInstance().getConnectTimeout();
	}
	
    /**
     * Sets the global network read timeout for all server calls, in milliseconds.
     *
     * @param readTimeout
     */
	public static void setReadTimeout(int readTimeout) {
		CGShared.getSharedInstance().setReadTimeout(readTimeout);
	}
	
    /**
     * Gets the global network read timeout for all server calls, in milliseconds.
     * @return
     */
	public static int getReadTimeout() {
		return CGShared.getSharedInstance().getReadTimeout();
	}

    /**
     * Sets whether any debug information is displayed.
     *
     * @param debug
     */
	public static void setDebug(boolean debug) {
		CGShared.getSharedInstance().setDebug(debug);
	}

    /**
     * Gets whether any debug information is displayed.
     * @return
     */
	public static boolean getDebug() {
		return CGShared.getSharedInstance().getDebug();
	}

    /**
     * Gets whether any calls are made to the API or all network activity is simulated.
     *
     * @return
     */
	public static boolean getSimulation() {
		return CGShared.getSharedInstance().getSimulation();
	}

    /**
     * Sets whether any calls are made to the API or all network activity is simulated.
     *
     * @param simulation
     */
	public static void setSimulation(boolean simulation) {
		CGShared.getSharedInstance().setSimulation(simulation);
	}

    /**
     * Gets the MUID supplied by client of the SDK.
     * @return
     */
    public static String getMuid() {
        return CGShared.getSharedInstance().getMuid();
    }

    /**
     * Sets the MUID to be used with certain CityGrid API calls, e.g. {@link CGAdsMobile} and {@link CGAdsTracker}.
     *
     * @param muid
     */
    public static void setMuid(String muid) {
        CGShared.getSharedInstance().setMuid(muid);
    }

    /**
     * Gets the mobile type/model supplied by client of the SDK.
     * @return
     */
    public static String getMobileType() {
        return CGShared.getSharedInstance().getMobileType();
    }

    /**
     * Sets the mobile type/model to be used with certain CityGrid API calls, e.g.
     * {@link CGAdsMobile} and {@link CGAdsTracker}.
     *
     * @param mobileType
     */
    public static void setMobileType(String mobileType) {
        CGShared.getSharedInstance().setMobileType(mobileType);
    }

    /**
     * Gets the builder for invoking place searches. The builder will default to the global publisher and timeout.
     *
     * @return
     */
    public static CGPlacesSearch placesSearch() {
        return CGPlacesSearch.placesSearch();
    }

    /**
     * Gets the builder for getting a detail for a place. The builder will default to the global publisher and timeout.
     *
     * @return
     */
    public static CGPlacesDetail placesDetail() {
        return CGPlacesDetail.placesDetail();
    }


    /**
     * Get the build for invoking review searches. The builder will default to the global publisher and timeout.
     * @return
     */
    public static CGReviewsSearch reviewsSearch() {
        return CGReviewsSearch.reviewsSearch();
    }

    /**
     * Gets the builder for invoking offers searches. The builder will default to the global publisher and timeout.
     *
     * @return
     */
    public static CGOffersSearch offersSearch() {
        return CGOffersSearch.offersSearch();
    }

    /**
     * Gets the builder for getting an offer for a specific location. The builder
     * will default to the global publisher and timeout.
     *
     * @return
     */
    public static CGOffersDetail offersDetail() {
        return CGOffersDetail.offersDetail();
    }

    /**
     * Gets the builder for invoking ad searches. The builder will default to the global publisher and timeout.
     *
     * @return
     */
    public static CGAdsCustom adsCustom() {
        return CGAdsCustom.adsCustom();
    }

    /**
     * Gets the builder for invoking ad searches that return a mobile banner. The builder will default to the global
     * publisher and timeout.
     *
     * @return
     */
    public static CGAdsMobile adsMobile() {
        return CGAdsMobile.adsMobile();
    }

    /**
     * Gets the builder for invoking Places that Pay APIs. The builder will default to the global
     * publisher and timeout.
     *
     * @return
     */
    public static CGAdsTracker adsTracker() {
        return CGAdsTracker.adsTracker();
    }
}
