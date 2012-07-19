/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertNotNull;

public class CGBuilderTest {
    @Test
    public void testParseDate() throws ParseException {
        assertNotNull(CGBuilder.parseDate("2006-04-18T08:55:48-07:00"));
        assertNotNull(CGBuilder.parseDate("2001-07-04T12:08:56.235-07:00"));
        assertNotNull(CGBuilder.parseDate("2001-07-04T12:08:56Z"));
    }
}
