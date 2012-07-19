package com.citygrid.content.places.detail;

public enum CGPlacesDetailImageType{
    UNKNOWN(-1), WEBSITE_THUMBNAIL(1), GENERIC_IMAGE(2), OWNER_PHOTO(3);
    private int code;
    CGPlacesDetailImageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
