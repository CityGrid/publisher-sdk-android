/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import java.net.URI;
import java.util.Date;

public interface CGReview {

    String getReviewId() ;
    URI getUrl() ;
    String getTitle() ;
    String getAuthor() ;
    String getText() ;
    String getPros() ;
    String getCons() ;
    Date getDate() ;
    int getRating() ;
    int getHelpful() ;
    int getUnhelpful() ;
    String getAttributionText() ;
    URI getAttributionLogo() ;
    int getAttributionSource() ;

}
