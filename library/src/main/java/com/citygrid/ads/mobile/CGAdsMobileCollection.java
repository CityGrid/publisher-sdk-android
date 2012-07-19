/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.ads.mobile;

import java.util.HashMap;
import java.util.Map;

public enum CGAdsMobileCollection {
    CollectionUnknown(-1, "unknown"),
    Collection320x50(1, "mobile-001-320x50"),
    Collection640x100(2, "mobile-004-320x50"),
    Collection300x50(3, "mobile-002-300x50"),
    Collection300x250(4, "mobile-003-300x250"),
    Collection320x50cat(5, "mobile-006-320x50"),
    Collection300x50cat(6, "mobile-007-300x50");

    private int code;
    private String value;
    private static final Map<String, CGAdsMobileCollection> stringToEnum = new HashMap<String, CGAdsMobileCollection>();
    static {
        for (CGAdsMobileCollection type : values()){
            stringToEnum.put(type.toString().toLowerCase(), type);
        }
    }

    CGAdsMobileCollection(int code, String value) {
        this.code = code;
        this.value= value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toLowerCase();
    }

    public static CGAdsMobileCollection fromString(String value) {
        return stringToEnum.get(value.toLowerCase());
    }
}
