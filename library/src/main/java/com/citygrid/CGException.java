/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import java.util.Arrays;

public class CGException extends Exception {
    private CGError[] errors;

    public CGException(CGError[] errors) {
        this.errors = errors;
    }

    public CGException(CGError error) {
        if (errors == null) {
            errors = new CGError[1];
            errors[0] = error;
        }
    }

    public CGError[] getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        return Arrays.toString(errors);
    }
}
