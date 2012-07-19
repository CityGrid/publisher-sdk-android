/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import java.util.HashMap;
import java.util.Map;

public enum CGReviewType {
    Unknown(-1, "Unknown"), EditorialReview(1, "editorial_review"), UserReview(2, "user_review");

    private static final Map<String, CGReviewType> stringToEnum = new HashMap<String, CGReviewType>();
    static {
        for (CGReviewType type : values()){
            stringToEnum.put(type.toString(), type);
        }
    }

    private int code ;
    private String value;

    CGReviewType(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return value;
    }

    public static CGReviewType fromString(String string) {
        return stringToEnum.get(string);
    }
}
