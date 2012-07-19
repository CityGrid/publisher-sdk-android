package com.citygrid.ads.tracker;

import com.citygrid.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.citygrid.CGBuilder.isEmpty;
import static com.citygrid.CGBuilder.isNotEmpty;

/**
 * The Places that Pay SDK helps to track referrals and impressions for details to help you earn money.
 * See http://docs.citygridmedia.com/display/citygridv2/Places+that+Pay.
 *
 * <p>To use this API, it is necessary to provide MUID and mobile type/model. Now let's pretend that
 * a location was shown and we want perform the impression tracking and then simulate that the user
 * clicked to view listing profile.</p>
 *
<pre>
{@code
    CityGrid.setPublisher("test");
    CityGrid.setSimulation(false);
    CityGrid.setMuid(YOUR_MUID);
    CityGrid.setMobileType(YOUR_MOBILE_TYPE);

    CGAdsTracker tracker = CityGrid.adsTracker();

    tracker.setLocationId(886038);
    tracker.setReferenceId(2);
    tracker.setImpressionId("123");
    tracker.setActionTarget(CGAdsTrackerActionTarget.ListingProfile);

    tracker.track();
}
</pre>
 *
 * <p>If a place is already shown, say by way of using {@link com.citygrid.content.places.detail.CGPlacesDetail} via
 * {@link com.citygrid.content.places.search.CGPlacesSearch}, then ad tracking can be easily performed using the
 * convenience methods on {@link com.citygrid.content.places.detail.CGPlacesDetailLocation}.
 * See {@link com.citygrid.content.places.search.CGPlacesSearch} for code sample.</p>
 */
public class CGAdsTracker implements Serializable, Cloneable {
    private static final String CGAdsTrackerUri = "ads/tracker/imp";
    private String publisher;
    private CGAdsTrackerActionTarget actionTarget;
    private int locationId;
    private int referenceId;
    private String impressionId;
    private String placement;
    private String sourcePhone;
    private String dialPhone;
    private int connectTimeout;
    private int readTimeout;
    private String muid;
    private String mobileType;

    public CGAdsTracker(String publisher) {
        this.publisher = publisher;
        this.actionTarget = CGAdsTrackerActionTarget.Unknown;
        this.locationId = CGConstants.INTEGER_UNKNOWN;
        this.referenceId = CGConstants.INTEGER_UNKNOWN;
        this.impressionId = null;
        this.placement = null;
        this.sourcePhone = null;
        this.dialPhone = null;
        this.connectTimeout = CGShared.getSharedInstance().getConnectTimeout();
        this.readTimeout = CGShared.getSharedInstance().getReadTimeout();
        this.muid = CGShared.getSharedInstance().getMuid();
        this.mobileType = CGShared.getSharedInstance().getMobileType();
    }

    public static CGAdsTracker adsTracker() {
        return adsTrackerWithPublisherAndPlacement(null, null);
    }

    public static CGAdsTracker adsTrackerWithPublisher(String publisher) {
        return adsTrackerWithPublisherAndPlacement(publisher, null);
    }

    public static CGAdsTracker adsTrackerWithPlacement(String placement) {
        return adsTrackerWithPublisherAndPlacement(null, placement);
    }

    public static CGAdsTracker adsTrackerWithPublisherAndPlacement(
            String publisher, String placement) {
        String resolvedPublisher = publisher != null ? publisher : CGShared.getSharedInstance().getPublisher();
        String resolvedPlacement = placement != null ? placement : CGShared.getSharedInstance().getPlacement();


        CGAdsTracker builder = new CGAdsTracker(resolvedPublisher);
        builder.setPlacement(resolvedPlacement);
        return builder;
    }

    private List<CGError> validate() {
        List<CGError> errors = new ArrayList<CGError>();

        if (isEmpty(this.publisher)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.PublisherRequired));
        }
        if (this.actionTarget == CGAdsTrackerActionTarget.Unknown) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.ActionTargetRequired));
        }
        if (this.locationId == CGConstants.INTEGER_UNKNOWN) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.LocationIdRequired));
        }
        if (this.referenceId == CGConstants.INTEGER_UNKNOWN) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.ReferenceIdRequired));
        }
        if (this.actionTarget == CGAdsTrackerActionTarget.ClickToCall && isEmpty(this.dialPhone)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.DialPhoneRequired));
        }
        if (isEmpty(this.muid)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.MuidRequired));
        }
        if (isEmpty(this.mobileType)) {
            errors.add(CGShared.getSharedInstance().errorWithErrorNum(CGErrorNum.MobileTypeRequired));
        }

        return errors;
    }

    private Map<String, Object> build() {
        Map<String, Object> parameters = new HashMap<String, Object>();

        if (isNotEmpty(this.publisher)) {
            parameters.put("publisher", this.publisher);
        }
        if (this.actionTarget != CGAdsTrackerActionTarget.Unknown) {
            parameters.put("action_target", this.actionTarget.toString());
        }
        if (this.locationId != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("listing_id", String.format("%d", this.locationId));
        }                                   
        if (this.referenceId != CGConstants.INTEGER_UNKNOWN) {
            parameters.put("reference_id", String.format("%d", this.referenceId));
        }
        if (isNotEmpty(this.placement)) {
            parameters.put("placement", this.placement);
        }
        if (isNotEmpty(this.impressionId)) {
            parameters.put("i", this.impressionId);
        }
        if (isNotEmpty(this.sourcePhone)) {
            parameters.put("sourcePhone", this.sourcePhone);
        }
        if (isNotEmpty(this.dialPhone)) {
            parameters.put("dialPhone", this.dialPhone);
        }

        parameters.put("mobile_type", this.mobileType);
        parameters.put("muid", this.muid);

        parameters.put("format", "json");

        return parameters;
    }

    public void track() throws CGException {
        List<CGError> validationErrors = validate();
        if (!validationErrors.isEmpty()) {
            throw new CGException(validationErrors.toArray(new CGError[validationErrors.size()]));
        }

        Map<String, Object> params = build();
        CGShared.getSharedInstance().sendSynchronousRequest(CGAdsTrackerUri, params, connectTimeout, readTimeout);
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public CGAdsTrackerActionTarget getActionTarget() {
        return actionTarget;
    }

    public void setActionTarget(CGAdsTrackerActionTarget actionTarget) {
        this.actionTarget = actionTarget;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public String getImpressionId() {
        return impressionId;
    }

    public void setImpressionId(String impressionId) {
        this.impressionId = impressionId;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public String getSourcePhone() {
        return sourcePhone;
    }

    public void setSourcePhone(String sourcePhone) {
        this.sourcePhone = sourcePhone;
    }

    public String getDialPhone() {
        return dialPhone;
    }

    public void setDialPhone(String dialPhone) {
        this.dialPhone = dialPhone;
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

    public String getMobileType() {
        return mobileType;
    }

    public void setMobileType(String mobileType) {
        this.mobileType = mobileType;
    }

    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGAdsTracker)) return false;

        CGAdsTracker tracker = (CGAdsTracker) o;

        if (connectTimeout != tracker.connectTimeout) return false;
        if (locationId != tracker.locationId) return false;
        if (readTimeout != tracker.readTimeout) return false;
        if (referenceId != tracker.referenceId) return false;
        if (actionTarget != tracker.actionTarget) return false;
        if (dialPhone != null ? !dialPhone.equals(tracker.dialPhone) : tracker.dialPhone != null) return false;
        if (impressionId != null ? !impressionId.equals(tracker.impressionId) : tracker.impressionId != null)
            return false;
        if (mobileType != null ? !mobileType.equals(tracker.mobileType) : tracker.mobileType != null) return false;
        if (muid != null ? !muid.equals(tracker.muid) : tracker.muid != null) return false;
        if (placement != null ? !placement.equals(tracker.placement) : tracker.placement != null) return false;
        if (publisher != null ? !publisher.equals(tracker.publisher) : tracker.publisher != null) return false;
        if (sourcePhone != null ? !sourcePhone.equals(tracker.sourcePhone) : tracker.sourcePhone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = publisher != null ? publisher.hashCode() : 0;
        result = 31 * result + (actionTarget != null ? actionTarget.hashCode() : 0);
        result = 31 * result + locationId;
        result = 31 * result + referenceId;
        result = 31 * result + (impressionId != null ? impressionId.hashCode() : 0);
        result = 31 * result + (placement != null ? placement.hashCode() : 0);
        result = 31 * result + (sourcePhone != null ? sourcePhone.hashCode() : 0);
        result = 31 * result + (dialPhone != null ? dialPhone.hashCode() : 0);
        result = 31 * result + connectTimeout;
        result = 31 * result + readTimeout;
        result = 31 * result + (muid != null ? muid.hashCode() : 0);
        result = 31 * result + (mobileType != null ? mobileType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("publisher=").append(publisher);
        sb.append(",actionTarget=").append(actionTarget);
        sb.append(",locationId=").append(locationId);
        sb.append(",referenceId=").append(referenceId);
        sb.append(",impressionId=").append(impressionId);
        sb.append(",placement=").append(placement);
        sb.append(",sourcePhone=").append(sourcePhone);
        sb.append(",dialPhone=").append(dialPhone);
        sb.append(",connectTimeout=").append(connectTimeout);
        sb.append(",readTimeout=").append(readTimeout);
        sb.append(",muid=").append(muid);
        sb.append(",mobileType=").append(mobileType);
        sb.append('>');
        return sb.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
