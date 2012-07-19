package com.citygrid;

public enum CGErrorNum {
    Unknown(-1),
    Underspecified(1),
    QueryTypeUnknown(2),
    QueryOverspecified(3),
    GeographyUnderspecified(4),
    GeographyOverspecified(5),
    RadiusRequired(6),
    DatePast(7),
    DateRangeIncomplete(8),
    DateRangeTooLong(9),
    GeocodeFailure(10),
    TagIllegal(11),
    ChainIllegal(12),
    FirstIllegal(13),
    LatitudeIllegal(14),
    LongitudeIllegal(15),
    RadiusIllegal(16),
    PageIllegal(17),
    ResultsPerPageIllegal(18),
    FromIllegal(19),
    ToIllegal(20),
    SortIllegal(21),
    RadiusOutOfRange(22),
    PageOutOfRange(23),
    ResultsPerPageOutOfRange(24),
    PublisherRequired(25),
    Internal(26),
    ListingNotFound(27),
    NetworkError(28),
    ParseError(29),
    PhoneIllegal(30),
    LocationIdOutOfRange(31),
    InfoUsaIdOutOfRange(32),
    ReviewCountOutOfRange(33),
    ReviewRatingOutOfRange(34),
    ReviewDaysOutOfRange(35),
    OfferIdRequired(36),
    MaxOutOfRange(37),
    CollectionRequired(38),
    SizeRequired(39),
    ActionTargetRequired(40),
    LocationIdRequired(41),
    ReferenceIdRequired(42),
    DialPhoneRequired(43),
    MuidRequired(44),
    IdTypeRequired(45),
    MobileTypeRequired(46),
    MaxWhatOutOfRange(47);

    private int code;

    CGErrorNum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
