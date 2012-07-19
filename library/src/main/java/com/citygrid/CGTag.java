/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import java.io.Serializable;

public class CGTag implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;

	public CGTag(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	@Override
	public int hashCode() {
		int result = 0;
		result += id;
		result += name == null ? 0 : name.hashCode();
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
		if (! CGTag.class.isInstance(obj)) {
			return false;
		}
		CGTag other = (CGTag) obj;
		
		return
			this.id == other.id &&
			((name == null && other.name == null) || (name != null && name.equals(other.name)));
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(24);
		builder.append("<"); builder.append(getClass().getSimpleName()); builder.append(" "); 
		builder.append("id="); builder.append(this.id);
		builder.append(",name="); builder.append(this.name);
		builder.append(">");
		return builder.toString();
	}
	
	public Object clone() throws CloneNotSupportedException {
		return new CGTag(id, name);
	}
}