/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.places.search;

public enum CGPlacesSearchSort {

	Unknown(-1, "unknown"),
	Distance(1, "dist"),
	Alphabetical(2, "alpha"),
	HighestRated(3, "highestrated"),
	MostReviewed(4, "mostreviewed"),
	TopMatches(5, "topmatches"),
	Offers(6, "offers");
	
	private int code;
    private String name;

	CGPlacesSearchSort(int code, String name) {
		this.code = code;
        this.name = name;
	}

	public int getCode() {
		return code;
	}

    public String getName() {
        return name;
    }
}
