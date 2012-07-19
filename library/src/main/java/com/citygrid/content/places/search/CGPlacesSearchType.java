/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.places.search;

public enum CGPlacesSearchType {
	Unknown(-1, "unknown"),
	Movie(1, "movie"),
	MovieTheater(2, "movietheater"),
	Restaurant(3, "restaurant"),
	Hotel(4, "hotel"),
	Travel(5, "travel"),
	BarClub(6, "barclub"),
	SpaBeauty(7, "spabeauty"),
	Shopping(8, "shopping");
	
	private int code;
    private String name;
	
	private CGPlacesSearchType(int code, String name) {
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
