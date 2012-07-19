/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import java.io.Serializable;
import java.util.Arrays;

public class CGHistogram implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private String name;
	private CGHistogramItem[] items;

	public CGHistogram(String name, CGHistogramItem[] items) {
		this.name = name;
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public CGHistogramItem[] getItems() {
		return items;
	}

	@Override
	public int hashCode() {
		int result = 0;
		result += name == null ? 0 : name.hashCode();
		result += Arrays.hashCode(items);
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
		if (! CGHistogram.class.isInstance(obj)) {
			return false;
		}
		CGHistogram other = (CGHistogram) obj;
		
		return
			((name == null && other.name == null) || (name != null && name.equals(other.name))) &&
			Arrays.equals(items, other.items);
		
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(64);
	 	builder.append("<"); builder.append(getClass().getSimpleName()); builder.append(" ");
		builder.append("name="); builder.append(name);
		builder.append(",items="); builder.append(Arrays.toString(items));
		builder.append(">");
		return builder.toString();
	}
	
	public Object clone() throws CloneNotSupportedException {
		return new CGHistogram(name, items == null ? null : Arrays.copyOf(items, items.length));
	}
}