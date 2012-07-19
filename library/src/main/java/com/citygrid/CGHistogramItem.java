/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;

public class CGHistogramItem implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private String name;
    private int count;
    private URI uri;
    private String[] tagIds;

    public CGHistogramItem(String name, int count, URI uri, String[] tagIds) {
        this.name = name;
        this.count = count;
        this.uri = uri;
        this.tagIds = tagIds;
    }

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	public URI getUri() {
		return uri;
	}

	public String[] getTagIds() {
		return tagIds;
	}
    
	@Override
	public int hashCode() {
		int result = 0;
		result += name == null ? 0 : name.hashCode();
		result += count;
		result += uri == null ? 0 : uri.hashCode();
		result += Arrays.hashCode(tagIds);
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
		if (! CGHistogramItem.class.isInstance(obj)) {
			return false;
		}
		CGHistogramItem other = (CGHistogramItem) obj;
		
		return
			((name == null && other.name == null) || (name != null && name.equals(other.name))) &&
			count == other.count &&
			((uri == null && other.uri == null) || (uri != null && uri.equals(other.uri))) &&
			Arrays.equals(tagIds, other.tagIds);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(64);
		builder.append("<"); builder.append(getClass().getSimpleName()); builder.append(" ");
		builder.append("name="); builder.append(name);
		builder.append(",count="); builder.append(count);
		builder.append(",uri="); builder.append(uri);
		builder.append(",tagIds="); builder.append(Arrays.toString(tagIds));
		builder.append(">");
		return builder.toString();
	}

    public Object clone() throws CloneNotSupportedException {
		return new CGHistogramItem(name, count, uri, tagIds == null ? null : Arrays.copyOf(tagIds, tagIds.length));
	}
}