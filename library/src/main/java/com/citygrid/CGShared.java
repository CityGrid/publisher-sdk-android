/*
 * Created by Lolay, Inc.
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */
package com.citygrid;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.citygrid.CGBuilder.parseJsonFile;

public class CGShared implements Serializable {
	private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger("com.citygrid.CGShared");
	private static final CGShared instance = new CGShared();

	private String publisher = null;
	private String placement = null;
	private int connectTimeout = CGConstants.DEFAULT_CONNECT_TIMEOUT;
	private int readTimeout = CGConstants.DEFAULT_READ_TIMEOUT;
	private String baseUrl = CGConstants.BASE_URL;
	private boolean debug = CGConstants.DEFAULT_DEBUG;
	private boolean simulation = CGConstants.DEFAULT_SIMULATION;
    private String muid = null;
    private String mobileType = null;

	private CGShared() {
	}

	public static CGShared getSharedInstance() {
		return instance;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPlacement() {
		return placement;
	}

	public void setPlacement(String placement) {
		this.placement = placement;
	}

	public Integer getConnectTimeout() {
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

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public boolean getDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public boolean getSimulation() {
		return simulation;
	}

	public void setSimulation(boolean simulation) {
		this.simulation = simulation;
	}

    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }

    public String getMobileType() {
        return mobileType;
    }

    public void setMobileType(String mobileType) {
        this.mobileType = mobileType;
    }

    @Override
	public int hashCode() {
		int result = 0;
		result += publisher == null ? 0 : publisher.hashCode();
		result += placement == null ? 0 : placement.hashCode();
		result += connectTimeout;
		result += readTimeout;
		result += baseUrl == null ? 0 : baseUrl.hashCode();
		result += debug ? 0 : 1;
		result += simulation ? 0 : 1;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (! CGShared.class.isInstance(obj.getClass())) {
			return false;
		}
		CGShared other = (CGShared) obj;
		
		return
			((publisher == null && other.publisher == null) || (publisher != null && publisher.equals(other.publisher))) &&
			((placement == null && other.placement == null) || (placement != null && placement.equals(other.placement))) &&
			connectTimeout == other.connectTimeout &&
			readTimeout == other.readTimeout &&
			((baseUrl == null && other.baseUrl == null) || (baseUrl != null && baseUrl.equals(other.baseUrl))) &&
			debug == other.debug &&
			simulation == other.simulation;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(64);
		builder.append("<"); builder.append(getClass().getSimpleName()); builder.append(" ");
		builder.append("publisher="); builder.append(publisher);
		builder.append(",placement="); builder.append(placement);
		builder.append(",connectTimeout="); builder.append(connectTimeout);
		builder.append(",readTimeout="); builder.append(readTimeout);
		builder.append(",baseUrl="); builder.append(baseUrl);
		builder.append(",debug="); builder.append(debug);
		builder.append(",simulation="); builder.append(simulation);
		builder.append(">");
		return builder.toString();
	}

    /**
     * Mirroring citygrid-ios API so using List<String> to return errors instead of throw exceptions.
     *
     * @param apiUrl
     * @param parameters
     * @param connectTimeout
     *@param readTimeout @return
     */
    public JsonNode sendSynchronousRequest(String apiUrl, Map<String, Object> parameters, int connectTimeout, int readTimeout) throws CGException {

        JsonNode rootNode = null;
        if (simulation) {
            if(logger.isLoggable(Level.FINE)){
                logger.fine("Running in simulation mode.");
            }
            if (apiUrl.equals("ads/tracker/imp")) {
                // AdsTracker API
                return null;
            }
            // content/places/v2/detail to content_places_v2_detail
            String jsonFilePath = buildSimulationJsonPath(apiUrl);
            if(logger.isLoggable(Level.FINE)){
                logger.fine("Attempt to read json from file: " + jsonFilePath);
            }
            rootNode = parseJsonFile(jsonFilePath);
        }
        else {
            String fullUrl = getBaseUrl() + "/" + apiUrl + dictAsUrlEncodedParameters(parameters);
            if(logger.isLoggable(Level.FINE)){
                logger.fine("API to query: " + fullUrl);
            }
            HttpURLConnection connection = null;
            try {
                URL finalUrl = new URL(fullUrl);
                connection = (HttpURLConnection) finalUrl.openConnection();
                connection.setConnectTimeout(connectTimeout);
                connection.setReadTimeout(readTimeout);
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    String responseString = getResponseAsString(connection);
                    CGError error = new CGError(
                            CGErrorNum.NetworkError,
                            String.format("Received a %s status code while invoking %s with response: \n %s",
                                    responseCode, fullUrl, responseString));
                    throw new CGException(error);
                }

                //TODO AdsTracker call returns content type as text/plain!!!
                if ( !(connection.getContentType().contains("application/json")
                        || connection.getContentType().equals("image/gif")
                        || (apiUrl.equals("ads/tracker/imp") && connection.getContentType().equals("text/plain")))) {
                    //response is not application/json or image/gif
                    String responseString = getResponseAsString(connection);
                    CGError error = new CGError(
                            CGErrorNum.NetworkError,
                            String.format("Received a %s mime type while invoking %s with response: \n %s",
                                    connection.getContentType(), finalUrl, responseString));
                    throw new CGException(error);
                }

                if (connection.getInputStream() == null) {
                    CGError error = new CGError(
                            CGErrorNum.NetworkError,
                            String.format("Received empty data while invoking %S", fullUrl));
                    throw new CGException(error);
                }

                //TODO AdsTracker call returns content type as text/plain!!!
                if (connection.getContentType().equals("image/gif") ||
                        (apiUrl.equals("ads/tracker/imp") && connection.getContentType().equals("text/plain"))) {
                    return null;
                }

                rootNode = new ObjectMapper().readValue(connection.getInputStream(), JsonNode.class);
            }
            catch(IOException e) {
                CGError error = new CGError(
                        CGErrorNum.NetworkError,
                        String.format("Received unknown network error %s while invoking %s", e.getMessage(), fullUrl));
                throw new CGException(error);
            }
            finally {
                if (connection != null) {
                    try {
                        connection.disconnect();
                    }
                    catch (Exception ignore) {

                    }
                }
            }
        }
        if(logger.isLoggable(Level.FINE)) {
            logger.fine("Returning rootNode: " + rootNode.toString());
        }
        return rootNode;
    }

    String buildSimulationJsonPath(String apiUrl) {
        return new StringBuilder("com/citygrid/simulation/").append(apiUrl.replace('/', '_')).append(".json").toString();
    }

    private String getResponseAsString(HttpURLConnection connection) throws IOException {
        InputStream is = null;
        String result = null;
        try {
            is = connection.getErrorStream();
            if (is == null) {
                is = connection.getInputStream();
            }
            result = readStringFromInputStream(is);
        }
        catch(IOException ioe) {
            is = connection.getInputStream();
            result = readStringFromInputStream(is);
        }
        return result;

    }

    private String readStringFromInputStream(InputStream is) throws IOException {
        if (is == null) {
            return null;
        }

        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (Exception ignore) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                }
                catch(Exception ignore) {
                }
            }
        }
        return sb.toString();
    }

    /**
     * IMPOOTANT!!! Only String and String[] are allowed as value of a parameter.  Other types
     * will lead to run time exception!!!
     * @param dict
     * @return
     */
    String dictAsUrlEncodedParameters(Map<String, Object> dict) {
        if (dict == null || dict.isEmpty()) {
            return "";
        }

        StringBuilder params = new StringBuilder();

        for (Map.Entry<String, Object> entry : dict.entrySet()) {
            try {
                Object value = entry.getValue();
                if (value instanceof String[]) {
                    String[] stringArray = (String[]) value;
                    for (String string : stringArray) {
                        if (string != null && string.trim().length() > 0) {
                            params.append(entry.getKey())
                                    .append("=")
                                    .append(URLEncoder.encode(string, "UTF-8"))
                                    .append("&");
                        }
                    }
                }
                else if (value instanceof String) {
                    String string = (String) value;
                    params.append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(string, "UTF-8"))
                            .append("&");

                }
                else {
                    throw new RuntimeException(
                            "Only String and String[] are allowed for URL parameters, got "
                                    + (value != null
                                            ? value.getClass().getCanonicalName()  + " with value: " + value
                                            : "null"));
                }
            } catch (UnsupportedEncodingException e) {
                logger.warning(
                        String.format("Exception converting key %s with value %s to query parameter because of %s",
                                entry.getKey(), entry.getValue(), e.getMessage()));
            }

        }

        // remove last &
        if (params.length() > 0) {
            params.deleteCharAt(params.length() -1);
        }

        if (params.length() > 0) {
            return "?" + params.toString();
        } else {
            return "";
        }

    }

    public CGError errorWithErrorNum(CGErrorNum errorNum) {
        CGError error = null;
        switch (errorNum) {
            case Underspecified:
                error = new CGError(errorNum, "Neither type, what, tag, nor chain was specified");
                break;
            case QueryTypeUnknown:
                error = new CGError(errorNum, "The query type parameter is not supported");
                break;
            case QueryOverspecified:
                error = new CGError(errorNum, "The parameter type was specified in addition to tag or chain");
                break;
            case GeographyUnderspecified:
                error = new CGError(errorNum, "Neither where or latitude/longitude/radius were given");
                break;
            case GeographyOverspecified:
                error = new CGError(errorNum, "Both where and one of latitude or longitude were given");
                break;
            case RadiusRequired:
                error = new CGError(errorNum, "Both latitude and longitude were given, but radius was missing");
                break;
            case DatePast:
                error = new CGError(errorNum, "");
                break;
            case DateRangeIncomplete:
                error = new CGError(errorNum, "");
                break;
            case DateRangeTooLong:
                error = new CGError(errorNum, "");
                break;
            case GeocodeFailure:
                error = new CGError(errorNum, "The geocoder could not find a match for the given where parameter");
                break;
            case TagIllegal:
                error = new CGError(errorNum, "The parameter tag was not an integer");
                break;
            case ChainIllegal:
                error = new CGError(errorNum, "The parameter chain was not an integer");
                break;
            case FirstIllegal:
                error = new CGError(errorNum, "The parameter first was not a character");
                break;
            case LatitudeIllegal:
                error = new CGError(errorNum, "The parameter latitude was not a valid number");
                break;
            case LongitudeIllegal:
                error = new CGError(errorNum, "The parameter longitude was not a valid number");
                break;
            case RadiusIllegal:
                error = new CGError(errorNum, "The parameter radius was not a valid number");
                break;
            case PageIllegal:
                error = new CGError(errorNum, "The parameter page was not an integer");
                break;
            case ResultsPerPageIllegal:
                error = new CGError(errorNum, "The parameter resultsPerPage was not an integer");
                break;
            case FromIllegal:
                error = new CGError(errorNum, "");
                break;
            case ToIllegal:
                error = new CGError(errorNum, "");
                break;
            case SortIllegal:
                error = new CGError(errorNum, "The parameter sort contained an unknown value");
                break;
            case RadiusOutOfRange:
                error = new CGError(errorNum, "The parameter radius was below 1");
                break;
            case PageOutOfRange:
                error = new CGError(errorNum, "The parameter page was less than 1");
                break;
            case ResultsPerPageOutOfRange:
                error = new CGError(errorNum, "The parameter resultsPerPage was not in the range 1..50");
                break;
            case PublisherRequired:
                error = new CGError(errorNum, "The publisher parameter is missing");
                break;
            case Internal:
                error = new CGError(errorNum, "An internal problem with the service occurred");
                break;
            case ListingNotFound:
                error = new CGError(errorNum, "");
                break;
            case NetworkError:
                error = new CGError(errorNum, "A problem with the network occurred");
                break;
            case ParseError:
                error = new CGError(errorNum, "A problem with the network payload occurred");
                break;
            case PhoneIllegal:
                error = new CGError(errorNum, "The parameter phone was illegal and was not 10 digits");
                break;
            case LocationIdOutOfRange:
                error = new CGError(errorNum, "The parameter locationId was illegal and not a positive integer");
                break;
            case InfoUsaIdOutOfRange:
                error = new CGError(errorNum, "The parameter infoUsaId was illegal and not a positive integer");
                break;
            case ReviewCountOutOfRange:
                error = new CGError(errorNum, "The parameter reviewCount was illegal and not between 0 and 20");
                break;
            case ReviewRatingOutOfRange:
                error = new CGError(errorNum, "The parameter ratomg was illegal and not a positive integer");
                break;
            case ReviewDaysOutOfRange:
                error = new CGError(errorNum, "The parameter reviewCount was illegal and not a positive integer");
                break;
            case OfferIdRequired:
                error = new CGError(errorNum, "The parameter offerId is required");
                break;
            case MaxOutOfRange:
                error = new CGError(errorNum, "The parameter max was not in the range 1..10");
                break;
            case CollectionRequired:
                error = new CGError(errorNum, "The collection parameter is missing");
                break;
            case SizeRequired:
                error = new CGError(errorNum, "The size parameter is missing");
                break;
            case ActionTargetRequired:
                error = new CGError(errorNum, "The actionTarget parameter is missing");
                break;
            case LocationIdRequired:
                error = new CGError(errorNum, "The listingId parameter is missing");
                break;
            case ReferenceIdRequired:
                error = new CGError(errorNum, "The referenceId parameter is missing");
                break;
            case DialPhoneRequired:
                error = new CGError(errorNum, "The dialPhone parameter is missing when the actionTarget is click to call");
                break;
            case MuidRequired:
                error = new CGError(errorNum, "The MUID parameter is missing");
                break;
            case IdTypeRequired:
                error = new CGError(errorNum, "The idType parameter is missing");
                break;
            case MobileTypeRequired:
                error = new CGError(errorNum, "The mobileType parameter is missing");
                break;
            case MaxWhatOutOfRange:
                error = new CGError(errorNum, "The maximum number of what terms has been reached ( > 3 )");
                break;
            default:
                error = new CGError(errorNum, "An unknown error number was specified");
                break;
        }

        return error;
    }
}


