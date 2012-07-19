/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import java.io.Serializable;

public class CGLatLon implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private double latitude;
	private double longitude;

	public CGLatLon(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}

	@Override
	public int hashCode() {
		int result = 0;
		result += latitude;
		result += longitude;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (! CGLatLon.class.isInstance(obj)) {
			return false;
		}
		CGLatLon other = (CGLatLon) obj;
		
		return
			latitude == other.latitude &&
			longitude == other.longitude;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(24);
		builder.append("<"); builder.append(getClass().getSimpleName()); builder.append(" "); 
		builder.append("latitude="); builder.append(latitude);
		builder.append(",longitude="); builder.append(longitude);
		builder.append(">");
		return builder.toString();
	}
	
	public Object clone() throws CloneNotSupportedException {
		return new CGLatLon(latitude, longitude);
	}
}