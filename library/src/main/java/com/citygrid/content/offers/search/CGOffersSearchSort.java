/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.offers.search;

import java.util.HashMap;
import java.util.Map;

public enum CGOffersSearchSort {
    Unknown(-1, "unknown"),
    Distance(1, "dist"),
    Relevance(2, "relevance"),
    StartDate(3, "startdate"),
    ExpiryDate(4, "expirydate"),
    Popularity(5, "popularity"),
    Alphabetical(6, "alpha");

    private int code;
    private String value;

    private static final Map<String, CGOffersSearchSort> stringToEnum = new HashMap<String, CGOffersSearchSort>();
    static {
        for (CGOffersSearchSort type : values()){
            stringToEnum.put(type.toString().toLowerCase(), type);
        }
    }
    CGOffersSearchSort(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return value;
    }

    public static CGOffersSearchSort fromString(String string) {
        return stringToEnum.get(string.toLowerCase());
    }
}
