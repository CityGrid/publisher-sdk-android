package com.citygrid.content.places.detail;

import java.io.Serializable;
import java.util.Arrays;

public class CGPlacesDetailResults implements Serializable {
   private CGPlacesDetailLocation[] locations;

    public CGPlacesDetailResults(CGPlacesDetailLocation[] locations) {
        this.locations = locations;
    }

    public CGPlacesDetailLocation[] getLocations() {
        return locations;
    }

    public CGPlacesDetailLocation getLocation() {
        if (locations != null && locations.length > 0) {
            return locations[0];
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetailResults)) return false;

        CGPlacesDetailResults that = (CGPlacesDetailResults) o;

        if (!Arrays.equals(locations, that.locations)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return locations != null ? Arrays.hashCode(locations) : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("locations=").append(locations == null ? "null" : Arrays.toString(locations));
        sb.append('>');
        return sb.toString();
    }
}
