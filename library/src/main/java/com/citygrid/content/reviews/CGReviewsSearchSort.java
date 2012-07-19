package com.citygrid.content.reviews;

public enum CGReviewsSearchSort {

    Unknown(""),CreateDate("createdate"),ReviewRating("reviewRating");
   
    private String name;
    
    public String getName(){
        return name;
    }
    
    
    CGReviewsSearchSort(String name){
        this.name=name;
    }
    
}
