package com.citygrid.ads.tracker;

import java.util.HashMap;
import java.util.Map;

public enum CGAdsTrackerActionTarget {
    Unknown(-1, "unknown"),
    ListingProfile(1, "listing_profile"),
    ClickToCall(2, "click_to_call"),
    ListingProfilePrint(3, "listing_profile_print"),
    ListingWebsite(4, "listing_website"),
    ListingReview(5, "listing_review"),
    WriteReview(6, "write_review"),
    ListingMap(7, "listing_map"),
    ListingDrivingDirection(8, "listing_driving_direction"),
    ListingMapPrint(9, "listing_map_print"),
    SendListingEmail(10, "send_listing_email"),
    SendListingPhone(11, "send_listing_phone"),
    SendListingGps(12, "send_listing_gps"),
    Offer(13, "offer"),
    OfferPrint(14, "offer_print"),
    ListingRequestOffer(15, "listing_request_offer"),
    PartnerMenu(16, "partner_menu"),
    PartnerReservation(17, "partner_reservation"),
    ListingPhoto(18, "listing_photo"),
    UploadListingPhoto(19, "upload_listing_photo"),
    ListingBlog(20, "listing_blog"),
    ListingForums(21, "listing_forums"),
    ListingNewsletters(22, "listing_newsletters"),
    ListingVote(23, "listing_vote"),
    ListingCorrection(24, "listing_correction"),
    ListingBookmark(25, "listing_bookmark");

    private int code;
    private String value;

    private static final Map<String, CGAdsTrackerActionTarget> stringToEnum = new HashMap<String, CGAdsTrackerActionTarget>();
    static {
        for (CGAdsTrackerActionTarget type : values()){
            stringToEnum.put(type.toString().toLowerCase(), type);
        }
    }
    
    CGAdsTrackerActionTarget(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static CGAdsTrackerActionTarget fromString(String value) {
        return stringToEnum.get(value.toLowerCase());
    }
}