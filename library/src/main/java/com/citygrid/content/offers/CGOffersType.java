/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.offers;

import java.util.HashMap;
import java.util.Map;

public enum CGOffersType {
    Unknown(-1, "unknown"),
    PercentOff(1, "percentoff"),
    Free(2, "free"),
    DollarsOff(3, "dollarsoff"),
    Gift(4, "gift"),
    Buy1Get1(5, "buy1get1"),
    Purchase(6, "purchase"),
    Other(7, "other"),
    PrintableCoupon(8, "printablecoupon"),
    GroupBuy(9, "groupbuy"),
    DailyDeal(10, "dailydeal");
    

    private int code;
    private String value;

    private static final Map<String, CGOffersType> stringToEnum = new HashMap<String, CGOffersType>();
    static {
        for (CGOffersType type : values()){
            stringToEnum.put(type.toString().toLowerCase(), type);
        }
    }

    CGOffersType(int code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static CGOffersType fromString(String parsedType) {
        return stringToEnum.get(parsedType.toLowerCase());
    }
}
