/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.offers.search;

import java.util.HashMap;
import java.util.Map;

public enum CGOffersSearchTagOperation {
    Unknown(-1, "unknown"),
    And(1, "and"),
    Or(2, "or");

    private int code;
    private String value;

    private static final Map<String, CGOffersSearchTagOperation> stringToEnum = new HashMap<String, CGOffersSearchTagOperation>();
    static {
        for (CGOffersSearchTagOperation type : values()){
            stringToEnum.put(type.toString().toLowerCase(), type);
        }
    }
    CGOffersSearchTagOperation(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static CGOffersSearchTagOperation fromString(String string) {
        return stringToEnum.get(string.toLowerCase());
    }
}
