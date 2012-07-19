/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

public class CGError {
    private CGErrorNum errorNumber;
    private String description;

    public CGError(CGErrorNum errorNumber, String description) {
        this.errorNumber = errorNumber;
        this.description = description;
    }

    public CGErrorNum getErrorNumber() {
        return errorNumber;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGError)) return false;

        CGError cgError = (CGError) o;

        if (description != null ? !description.equals(cgError.description) : cgError.description != null) return false;
        if (errorNumber != cgError.errorNumber) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = errorNumber != null ? errorNumber.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<").append(getClass().getSimpleName()).append(" ")
                .append("errorNum=").append(errorNumber.toString())
                .append(",description=").append(description)
                .append(">");
        return builder.toString();
    }

}
