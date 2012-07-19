package com.citygrid.content.places.detail;

import com.citygrid.CGAddress;

import java.io.Serializable;

public class CGPlacesDetailAddress extends CGAddress implements Serializable {
    private String deliveryPoint;
    private String crossStreet;

    public CGPlacesDetailAddress(String street, String city, String state, String zip) {
        super(street, city, state, zip);
    }

    public CGPlacesDetailAddress(String street, String city, String state, String zip, String deliveryPoint, String crossStreet) {
        super(street, city, state, zip);
        this.deliveryPoint = deliveryPoint;
        this.crossStreet = crossStreet;
    }

    public String getDeliveryPoint() {
        return deliveryPoint;
    }

    public String getCrossStreet() {
        return crossStreet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetailAddress)) return false;
        if (!super.equals(o)) return false;

        CGPlacesDetailAddress that = (CGPlacesDetailAddress) o;

        if (crossStreet != null ? !crossStreet.equals(that.crossStreet) : that.crossStreet != null) return false;
        if (deliveryPoint != null ? !deliveryPoint.equals(that.deliveryPoint) : that.deliveryPoint != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (deliveryPoint != null ? deliveryPoint.hashCode() : 0);
        result = 31 * result + (crossStreet != null ? crossStreet.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("street=").append(getStreet());
        sb.append(",city=").append(getCity());
        sb.append(",state=").append(getState());
        sb.append(",zip=").append(getZip());
        sb.append(",deliveryPoint=").append(deliveryPoint);
        sb.append(",crossStreet=").append(crossStreet);
        sb.append('>');
        return sb.toString();
    }
}
