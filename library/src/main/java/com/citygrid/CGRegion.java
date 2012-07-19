/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import java.io.Serializable;

public class CGRegion implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private String type;
	private String name;
	private CGLatLon latlon;
	private float radius;
	
	public CGRegion(String type, String name, CGLatLon latlon, float radius) {
		this.type = type;
		this.name = name;
		this.latlon = latlon;
		this.radius = radius;
	}
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public CGLatLon getLatlon() {
		return latlon;
	}
	public float getRadius() {
		return radius;
	}

	@Override
	public int hashCode() {
		int result = 0;
		result += type == null ? 0 : type.hashCode();
		result += name == null ? 0 : name.hashCode();
		result += latlon == null ? 0 : latlon.hashCode();
		result += radius;
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
		if (! CGRegion.class.isInstance(obj)) {
			return false;
		}
		CGRegion other = (CGRegion) obj;
		
		return
			((type == null && other.type == null) || (type != null && type.equals(other.type))) &&
			((name == null && other.name == null) || (name != null && name.equals(other.name))) &&
			((latlon == null && other.latlon == null) || (latlon != null && latlon.equals(other.latlon))) &&
			radius == other.radius;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(64);
		builder.append("<"); builder.append(getClass().getSimpleName()); builder.append(" "); 
		builder.append("type="); builder.append(type);
		builder.append(",name="); builder.append(name);
		builder.append(",latlon="); builder.append(latlon);
		builder.append(",radius="); builder.append(radius);
		builder.append(">");
		return builder.toString();
	}
	
	public Object clone() throws CloneNotSupportedException {
		return new CGRegion(type, name, latlon == null ? null : (CGLatLon) latlon.clone(), radius);
	}
}