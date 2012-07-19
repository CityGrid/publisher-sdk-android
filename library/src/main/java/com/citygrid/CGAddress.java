package com.citygrid;

import java.io.Serializable;

public class CGAddress implements Serializable {
    String street;
    String city;
    String state;
    String zip;

    public CGAddress(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGAddress)) return false;

        CGAddress cgAddress = (CGAddress) o;

        if (city != null ? !city.equals(cgAddress.city) : cgAddress.city != null) return false;
        if (state != null ? !state.equals(cgAddress.state) : cgAddress.state != null) return false;
        if (street != null ? !street.equals(cgAddress.street) : cgAddress.street != null) return false;
        if (zip != null ? !zip.equals(cgAddress.zip) : cgAddress.zip != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<").append(getClass().getSimpleName()).append(" ")
                .append("street=").append(street)
                .append(",city=").append(city)
                .append(",state=").append(state)
                .append(",zip=").append(zip)
                .append(">");
        return builder.toString();
    }
}
