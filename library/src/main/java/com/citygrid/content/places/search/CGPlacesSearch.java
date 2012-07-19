/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid.content.places.search;

import com.citygrid.*;
import org.codehaus.jackson.JsonNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.citygrid.CGBuilder.*;
import static com.citygrid.content.CGContentBuilder.*;

/**
 * A builder for invoking the CityGrid Places Search API as documented
 * at http://docs.citygridmedia.com/display/citygridv2/Places+API.
 *
 * <p>The most popular use case for CityGrid is to be able to perform a places search to show results for places
 * around a lat/lon or zip code, show a detail as a result of any user interaction, and call the tracker on each
 * interaction to earn money.</p>
 *
 * <p>Let's pretend the user chose to find Movie Theater's in 90045 through your application.
 * This would typically be ready from your UI or model.</p>
<pre>
{@code
    // Pretends the user chose to find Movie Theater's in 90045 within the application
    // and this is read from your UI or model
    CGPlacesSearch search = CityGrid.placesSearch();
    search.setType(CGPlacesSearchType.MovieTheater);
    search.setWhere("90045");
    search.setHistograms(true);

    CGPlacesSearchResults results = search(search);

    for (CGPlacesSearchLocation location : results.getLocations()) {
        // do something with the location...
    }
}
</pre>
 *
 * <p>Next, pretend the user selected a location and wanted to view the profile as a result. The selected location
 * might have come from any one from the list of locations, but for the purpose of an example this will chose
 * the first location.</p>
 *
<pre>
{@code
    // Pretend the user selected the first location result and wants to display the full location data
    // Usually this is a navigation to a detail view.
    CGPlacesSearchLocation searchLocation = results.getLocations()[0];

    // Get the detail (This will populate the locationId and impressionId for you)
    // You could alternatively use the locationId and impressionId yourself, but this is easier
    CGPlacesDetailLocation detailLocation = searchLocation.placesDetailLocation();
    // do something with the location detail.
}
</pre>
 *
 * <p>The location is displayed, but we also have to track that the location was displayed.
 * Also, if the user clicks on or navigates on any content be sure to track that as well.</p>
 *
<pre>
{@code
    try {
        detailLocation.track(
                MY_MUID,
                MY_MOBILE_TYPE,
                CGAdsTrackerActionTarget.ClickToCall);
    }
    catch (CGException e) {
        e.printStackTrace();
    }
}
</pre>
 *
 **/
public class CGPlacesSearch implements Serializable, Cloneable {

    private static final String CGPlacesSearchWhereUri = "content/places/v2/search/where";
    private static final String CGPlacesSearchLatLonUri = "content/places/v2/search/latlon";

    /**
     * The publisher code that identifies you. This is required.
     */
    private String publisher;

    /**
     * Restricts results to listings of the given type
     */
    private CGPlacesSearchType type;

    /**
     * Search term text
     */
    private String what;

    /**
     * Restricts search to listings with the given tag id.
     * <p/>
     * <p>Tag ids are internal CityGrid identifiers and subject to change.
     * This parameter should only be used in queries that are obtained as "refinement urls"
     * from previous searches.
     */
    private int tag;

    /**
     * Restricts search to listings with the given chain id.
     * <p/>
     * <p>@discussion Chain ids are internal CityGrid identifiers and subject to change.
     * This parameter should only be used in queries that are obtained as
     * "chain expansion urls" from previous searches.
     */
    private int chain;

    /**
     * Restricts search to entities whose name begins with the given character,
     * or if "#", then to entities beginning with any digit.
     */
    private String first;

    /**
     * The geographic location
     * <p/>
     * <p>Address-based search is performed when this parameter starts with a number
     * and contains non-numeric characters; it is much slower than searching a named region
     */
    private String where;

    /**
     * Radius of a circle search.  If larger than 50 it defaults to 50.
     */
    private float radius;

    /**
     * The page number of the result set to display.
     */
    private int page;

    /**
     * The number of results to be displayed/returned in a page.
     */
    private int resultsPerPage;

    /**
     * Sort criterion for the results.
     * <p> Use alpha to sort results alphabetically, and dist to sort by increasing
     * distance from the center of the search region. The dist request is only valid for
     * address-based and circle searches.
     * <p/>
     * <p>If this parameter is omitted, the results will be ranked by relevance for keyword
     * searches and distance for latitude and longitude
     */
    private CGPlacesSearchSort sort;

    /**
     * An optional property for storing additional information you would like CityGrid
     * Media to log for this view.
     * <p/>
     * <p>An example: if you run a search engine marketing campaign for, say, Google
     * and Yahoo!, you can set the placement property to "sem_google" or "sem_yahoo".
     * Alternatively, if you publish CityGrid listings in different locations in your own site,
     * you can set the placement property to values such as "home_page" or "search" (all up to
     * you). CityGrid will organize reports for you by placement
     */
    private String placement;

    /**
     * Whether to return only listings that have offers associated with them.
     */
    private boolean offers;

    /**
     * An optional property for providing information on how many listings are
     * in given groups and categories.
     * <p/>
     * <p>Turning on histograms can affect performance.
     */
    private boolean histograms;

    /**
     * Latitude and longitude of the center of a circle for a geographic search.
     */
    private CGLatLon latlon;

    /**
     * Second latitude and longitude used when performing a manual box search.
     */
    private CGLatLon latlon2;

    /**
     * The network connect connectTimeout used when performing a search. In milliseconds.
     */
    private int connectTimeout;

    /**
     * The network read connectTimeout used when performing a search. In milliseconds.
     */
    private int readTimeout;

    /**
     * An optional property for grouping API calls for tracking purposes.
     * <p/>
     * <p>The value should be set when making subsequent calls that are related to
     * a previously made call. The user should never supply their own generated value for
     * the impression_id.
     */
    private String impressionId;

    /**
     * Create a places search with the publisher.
     * <p/>
     * <p>Although this is available to use, it's recommended to use one of the following:
     * <ol>
     * <li> CityGrid.placesSearch()
     * <li> CGPlacesSearch.placesSearch()
     * <li> CGPlacesSearch placesSearchWithPublisher()
     * </ol>
     */
    public CGPlacesSearch(String publisher) {
        this.publisher = publisher;
        this.type = CGPlacesSearchType.Unknown;
        this.sort = CGPlacesSearchSort.Unknown;
        this.tag = CGConstants.INTEGER_UNKNOWN;
        this.chain = CGConstants.INTEGER_UNKNOWN;
        this.radius = CGConstants.FLOAT_UNKNOWN;
        this.page = CGConstants.INTEGER_UNKNOWN;
        this.resultsPerPage = CGConstants.INTEGER_UNKNOWN;
        this.connectTimeout = CGShared.getSharedInstance().getConnectTimeout();
        this.readTimeout = CGShared.getSharedInstance().getReadTimeout();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public CGPlacesSearchType getType() {
        return type;
    }

    public void setType(CGPlacesSearchType type) {
        this.type = type;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getChain() {
        return chain;
    }

    public void setChain(int chain) {
        this.chain = chain;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public CGPlacesSearchSort getSort() {
        return sort;
    }

    public void setSort(CGPlacesSearchSort sort) {
        this.sort = sort;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public boolean isOffers() {
        return offers;
    }

    public void setOffers(boolean offers) {
        this.offers = offers;
    }

    public boolean isHistograms() {
        return histograms;
    }

    public void setHistograms(boolean histograms) {
        this.histograms = histograms;
    }

    public CGLatLon getLatlon() {
        return latlon;
    }

    public void setLatlon(CGLatLon latlon) {
        this.latlon = latlon;
    }

    public CGLatLon getLatlon2() {
        return latlon2;
    }

    public void setLatlon2(CGLatLon latlon2) {
        this.latlon2 = latlon2;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public String getImpressionId() {
        return impressionId;
    }

    public void setImpressionId(String impressionId) {
        this.impressionId = impressionId;
    }

    /**
     * Returns a search builder that is initialized to the global publisher ande timeout values.
     *
     * @return A places search builder.
     */
    public static CGPlacesSearch placesSearch() {
        return placesSearchWithPublisherAndPlacement(null, null);
    }

    /**
     * Returns a search builder that is initialized to the global connectTimeout values.
     *
     * @param publisher
     * @return A places search builder
     */
    public static CGPlacesSearch placesSearchWithPublisher(String publisher) {
        return placesSearchWithPublisherAndPlacement(publisher, null);
    }

    /**
     * Returns a search builder that is initialized to the global connectTimeout values.
     *
     * @param placement
     * @return A places search builder
     */
    public static CGPlacesSearch placesSearchWithPlacement(String placement) {
        return placesSearchWithPublisherAndPlacement(null, placement);
    }

    /**
     * Returns a search builder that is initialized to the global connectTimeout values.
     *
     * @param publisher
     * @param placement
     * @return A places search builder
     */

    public static CGPlacesSearch placesSearchWithPublisherAndPlacement(
            String publisher, String placement) {
        String resolvedPublisher = publisher != null ? publisher : CGShared.getSharedInstance().getPublisher();
        String resolvedPlacement = placement != null ? placement : CGShared.getSharedInstance().getPlacement();

        CGPlacesSearch builder = new CGPlacesSearch(resolvedPublisher);
        builder.setPlacement(resolvedPlacement);
        return builder;
    }

    private List<CGError> validate() {
        List<CGError> errors = new ArrayList<CGError>();

        if (isEmpty(this.publisher)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.PublisherRequired));
        }
        if (isEmpty(this.where) && (this.latlon == null || (this.radius == CGConstants.FLOAT_UNKNOWN && this.latlon2 == null))) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.GeographyUnderspecified));
        }
        if (isEmpty(this.what)
                && this.type == CGPlacesSearchType.Unknown
                && this.tag == CGConstants.INTEGER_UNKNOWN
                && this.chain == CGConstants.INTEGER_UNKNOWN) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.Underspecified));
        }
        if (this.tag != CGConstants.INTEGER_UNKNOWN && this.tag < 1) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.TagIllegal));
        }
        if (this.chain != CGConstants.INTEGER_UNKNOWN && this.chain < 1) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.ChainIllegal));
        }
        if (this.resultsPerPage != CGConstants.INTEGER_UNKNOWN && (this.resultsPerPage < 1 || this.resultsPerPage > 50)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.ResultsPerPageOutOfRange));
        }
        if (this.page != CGConstants.INTEGER_UNKNOWN && this.page < 1) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.PageOutOfRange));
        }
        if (this.first != null && this.first.length() > 1) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.FirstIllegal));
        }
        if (this.latlon != null && (this.latlon.getLatitude() > 180.0 || this.latlon.getLatitude() < -180.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LatitudeIllegal));
        }
        if (this.latlon != null && (this.latlon.getLongitude() > 180.0 || this.latlon.getLongitude() < -180.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LongitudeIllegal));
        }
        if (this.latlon2 != null && (this.latlon2.getLatitude() > 180.0 || this.latlon2.getLatitude() < -180.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LatitudeIllegal));
        }
        if (this.latlon2 != null && (this.latlon2.getLongitude() > 180.0 || this.latlon2.getLongitude() < -180.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LongitudeIllegal));
        }
        if (this.radius != CGConstants.FLOAT_UNKNOWN && !(this.radius >= 1.0 && this.radius <= 50.0)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.RadiusOutOfRange));
        }

        return errors;
    }

    /**
     * Performs a where search as documented by http://docs.citygridmedia.com/display/citygridv2/Places+API#PlacesAPI-SearchUsingWhere
     *
     * @return Places search result.
     * @throws CGException
     */
    public CGPlacesSearchResults search() throws CGException {
        List<CGError> validationErrors = validate();
        if (!validationErrors.isEmpty()) {
            throw new CGException(validationErrors.toArray(new CGError[validationErrors.size()]));
        }

        String url = isNotEmpty(this.where) ? CGPlacesSearchWhereUri : CGPlacesSearchLatLonUri;
        Map<String, Object> params = build();
        JsonNode rootNode = CGShared.getSharedInstance().sendSynchronousRequest(url, params, connectTimeout, readTimeout);
        if (rootNode == null) {
            return null;
        }

        CGPlacesSearchResults results = processResults(rootNode.get("results"));
        return results;
    }

    private Map<String, Object> build() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        if (type != CGPlacesSearchType.Unknown) {
            parameters.put("type", type.getName());
        }
        if (isNotEmpty(this.what)) {
            parameters.put("what", this.what);
        }
        if (this.tag != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("tag", String.valueOf(this.tag));
        }
        if (this.chain != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("chain", String.valueOf(this.chain));
        }
        if (isNotEmpty(this.first)) {
            parameters.put("first", this.first);
        }
        if (isNotEmpty(this.where)) {
            parameters.put("where", this.where);
        }
        if (this.latlon != null) {
            parameters.put("lat", String.valueOf(this.latlon.getLatitude()));
            parameters.put("lon", String.valueOf(this.latlon.getLongitude()));
        }
        if (this.radius != CGConstants.FLOAT_UNKNOWN) {
            parameters.put("radius", String.valueOf(this.radius));
        }
        if (this.latlon2 != null) {
            parameters.put("lat2", String.valueOf(this.latlon2.getLatitude()));
            parameters.put("lon2", String.valueOf(this.latlon2.getLongitude()));
        }
        if (this.page != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("page", String.valueOf(this.page));
        }
        if (this.resultsPerPage != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("rpp", String.valueOf(this.resultsPerPage));
        }
        if (sort != CGPlacesSearchSort.Unknown) {
            parameters.put("sort", sort.getName());
        }
        if (isNotEmpty(this.publisher)) {
            parameters.put("publisher", this.publisher);
        }
        if (isNotEmpty(this.placement)) {
            parameters.put("placement", this.placement);
        }
        parameters.put("has_offers", this.offers ? "true" : "false");
        parameters.put("histograms", this.histograms ? "true" : "false");
        if (isNotEmpty(this.impressionId)) {
            parameters.put("i", this.impressionId);
        }

        parameters.put("format", "json");
        return parameters;
    }

    CGPlacesSearchResults processResults(JsonNode rootNode) {
        CGPlacesSearchResults results = new CGPlacesSearchResults.Builder()
                .firstHit(nullSafeGetIntValue(rootNode.get("first_hit")))
                .latHit(nullSafeGetIntValue(rootNode.get("last_hit")))
                .totalHits(nullSafeGetIntValue(rootNode.get("total_hits")))
                .page(nullSafeGetIntValue(rootNode.get("page")))
                .resultsPerPage(nullSafeGetIntValue(rootNode.get("rpp")))
                .didYouMean(nullSafeGetTextValue(rootNode.get("did_you_mean")))
                .uri(jsonNodeToUri(rootNode.get("uri")))
                .regions(processRegions(rootNode.get("regions")))
                .locations(processLocations(rootNode.get("locations")))
                .histograms(processHistograms(rootNode.get("histograms")))
                .build();
        return results;
    }

    private CGPlacesSearchLocation[] processLocations(JsonNode locationsNode) {
        CGPlacesSearchLocation[] locations = null;
        if (locationsNode != null) {
            locations = new CGPlacesSearchLocation[locationsNode.size()];
            int i = 0;
            for (JsonNode node : locationsNode) {
                CGBaseLocation baseLocation = new CGBaseLocation.Builder()
                        .locationId(nullSafeGetIntValue(node.get("id")))
                        .impressionId(nullSafeGetTextValue(node.get("impression_id")))
                        .name(nullSafeGetTextValue(node.get("name")))
                        .address(processLocationAddress(node.get("address"), false))
                        .latlon(new CGLatLon(nullSafeGetDoubleValue(node.get("latitude")), nullSafeGetDoubleValue(node.get("longitude"))))
                        .image(null) // ios hardcoded nil.
                        .rating(nullSafeGetIntValue(node.get("rating")))
                        .phone(nullSafeGetTextValue(node.get("phone_number")))
                        .build();
                CGPlacesSearchLocation location = new CGPlacesSearchLocation.Builder()
                        .baseLocation(baseLocation)
                        .publicId(nullSafeGetTextValue(node.get("public_id")))
                        .featured(nullSafeGetBooleanValue(node.get("featured")))
                        .hasOffers(nullSafeGetBooleanValue(node.get("has_offers")))
                        .hasVideo(nullSafeGetBooleanValue(node.get("has_video")))
                        .neighborhood(nullSafeGetTextValue(node.get("neighborhood")))
                        .fax(nullSafeGetTextValue(node.get("fax_number")))
                        .offers(nullSafeGetTextValue(node.get("offers")))
                        .tagline(nullSafeGetTextValue(node.get("tagline")))
                        .reviews(nullSafeGetIntValue(node.get("user_review_count")))
                        .categories(processCategories(node))
                        .tags(processTags(node.get("tags")))
                        .profile(jsonNodeToUri(node.get("profile")))
                        .website(jsonNodeToUri(node.get("website")))
                        .build();

                locations[i++] = location;
            }
        }
        return locations;
    }

    private String[] processCategories(JsonNode node) {
        String categoriesText = nullSafeGetTextValue(node.get("sample_categories"));
        String[] categories = null;
        if (categoriesText != null) {
            categories = categoriesText.split(",");
            // trim any white spaces
            int j = 0;
            for (String string : categories) {
                categories[j] = categories[j].trim();
                j++;
            }
        }
        return categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesSearch)) return false;

        CGPlacesSearch that = (CGPlacesSearch) o;

        if (chain != that.chain) return false;
        if (connectTimeout != that.connectTimeout) return false;
        if (histograms != that.histograms) return false;
        if (offers != that.offers) return false;
        if (page != that.page) return false;
        if (Float.compare(that.radius, radius) != 0) return false;
        if (readTimeout != that.readTimeout) return false;
        if (resultsPerPage != that.resultsPerPage) return false;
        if (tag != that.tag) return false;
        if (first != null ? !first.equals(that.first) : that.first != null) return false;
        if (impressionId != null ? !impressionId.equals(that.impressionId) : that.impressionId != null) return false;
        if (latlon != null ? !latlon.equals(that.latlon) : that.latlon != null) return false;
        if (latlon2 != null ? !latlon2.equals(that.latlon2) : that.latlon2 != null) return false;
        if (placement != null ? !placement.equals(that.placement) : that.placement != null) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;
        if (sort != that.sort) return false;
        if (type != that.type) return false;
        if (what != null ? !what.equals(that.what) : that.what != null) return false;
        if (where != null ? !where.equals(that.where) : that.where != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = publisher != null ? publisher.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (what != null ? what.hashCode() : 0);
        result = 31 * result + tag;
        result = 31 * result + chain;
        result = 31 * result + (first != null ? first.hashCode() : 0);
        result = 31 * result + (where != null ? where.hashCode() : 0);
        result = 31 * result + (radius != +0.0f ? Float.floatToIntBits(radius) : 0);
        result = 31 * result + page;
        result = 31 * result + resultsPerPage;
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (placement != null ? placement.hashCode() : 0);
        result = 31 * result + (offers ? 1 : 0);
        result = 31 * result + (histograms ? 1 : 0);
        result = 31 * result + (latlon != null ? latlon.hashCode() : 0);
        result = 31 * result + (latlon2 != null ? latlon2.hashCode() : 0);
        result = 31 * result + connectTimeout;
        result = 31 * result + readTimeout;
        result = 31 * result + (impressionId != null ? impressionId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("publisher=").append(publisher);
        sb.append(",type=").append(type);
        sb.append(",what=").append(what);
        sb.append(",tag=").append(tag);
        sb.append(",chain=").append(chain);
        sb.append(",first=").append(first);
        sb.append(",where=").append(where);
        sb.append(",radius=").append(radius);
        sb.append(",page=").append(page);
        sb.append(",resultsPerPage=").append(resultsPerPage);
        sb.append(",sort=").append(sort);
        sb.append(",placement=").append(placement);
        sb.append(",offers=").append(offers);
        sb.append(",histograms=").append(histograms);
        sb.append(",latlon=").append(latlon);
        sb.append(",latlon2=").append(latlon2);
        sb.append(",connectTimeout=").append(connectTimeout);
        sb.append(",readTimeout=").append(readTimeout);
        sb.append(",impressionId=").append(impressionId);
        sb.append('>');
        return sb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
