package com.citygrid.content.places.detail;

import java.io.Serializable;
import java.net.URI;

public class CGPlacesDetailUrls implements Serializable {
    private URI profile;
    private URI reviews;
    private URI video;
    private URI website;
    private URI menu;
    private URI reservation;
    private URI map;
    private URI sendToFriend;
    private URI email;
    private URI custom1;
    private URI custom2;
    private URI orderUrl;

    public CGPlacesDetailUrls(Builder builder) {
        profile = builder.profile;
        reviews = builder.reviews;
        video = builder.video;
        website = builder.website;
        menu = builder.menu;
        reservation = builder.reservation;
        map = builder.map;
        sendToFriend = builder.sendToFriend;
        email = builder.email;
        custom1 = builder.custom1;
        custom2 = builder.custom2;
        orderUrl = builder.orderUrl;
    }

    public static class Builder {
        private URI profile;
        private URI reviews;
        private URI video;
        private URI website;
        private URI menu;
        private URI reservation;
        private URI map;
        private URI sendToFriend;
        private URI email;
        private URI custom1;
        private URI custom2;
        private URI orderUrl;

        public CGPlacesDetailUrls build() {
            return new CGPlacesDetailUrls(this);
        }

        public Builder profile(URI profile) {
            this.profile = profile;
            return this;
        }

        public Builder reviews(URI reviews) {
            this.reviews = reviews;
            return this;
        }

        public Builder video(URI video) {
            this.video = video;
            return this;
        }

        public Builder website(URI website) {
            this.website = website;
            return this;
        }

        public Builder menu(URI menu) {
            this.menu = menu;
            return this;
        }

        public Builder reservation(URI reservation) {
            this.reservation = reservation;
            return this;
        }

        public Builder map(URI map) {
            this.map = map;
            return this;
        }

        public Builder sendToFriend(URI sendToFriend) {
            this.sendToFriend = sendToFriend;
            return this;
        }

        public Builder email(URI email) {
            this.email = email;
            return this;
        }

        public Builder custom1(URI custom1) {
            this.custom1 = custom1;
            return this;
        }

        public Builder custom2(URI custom2) {
            this.custom2 = custom2;
            return this;
        }
        
        public Builder orderUrl(URI orderUrl) {
            this.orderUrl = orderUrl;
            return this;
        }
    }

    public URI getProfile() {
        return profile;
    }

    public URI getReviews() {
        return reviews;
    }

    public URI getVideo() {
        return video;
    }

    public URI getWebsite() {
        return website;
    }

    public URI getMenu() {
        return menu;
    }

    public URI getReservation() {
        return reservation;
    }

    public URI getMap() {
        return map;
    }

    public URI getSendToFriend() {
        return sendToFriend;
    }

    public URI getEmail() {
        return email;
    }

    public URI getCustom1() {
        return custom1;
    }

    public URI getCustom2() {
        return custom2;
    }
    
    public URI getOrderUrl() {
        return orderUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetailUrls)) return false;

        CGPlacesDetailUrls that = (CGPlacesDetailUrls) o;

        if (custom1 != null ? !custom1.equals(that.custom1) : that.custom1 != null) return false;
        if (custom2 != null ? !custom2.equals(that.custom2) : that.custom2 != null) return false;
        if (orderUrl != null ? !orderUrl.equals(that.orderUrl) : that.orderUrl != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (map != null ? !map.equals(that.map) : that.map != null) return false;
        if (menu != null ? !menu.equals(that.menu) : that.menu != null) return false;
        if (profile != null ? !profile.equals(that.profile) : that.profile != null) return false;
        if (reservation != null ? !reservation.equals(that.reservation) : that.reservation != null) return false;
        if (reviews != null ? !reviews.equals(that.reviews) : that.reviews != null) return false;
        if (sendToFriend != null ? !sendToFriend.equals(that.sendToFriend) : that.sendToFriend != null) return false;
        if (video != null ? !video.equals(that.video) : that.video != null) return false;
        if (website != null ? !website.equals(that.website) : that.website != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = profile != null ? profile.hashCode() : 0;
        result = 31 * result + (reviews != null ? reviews.hashCode() : 0);
        result = 31 * result + (video != null ? video.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (menu != null ? menu.hashCode() : 0);
        result = 31 * result + (reservation != null ? reservation.hashCode() : 0);
        result = 31 * result + (map != null ? map.hashCode() : 0);
        result = 31 * result + (sendToFriend != null ? sendToFriend.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (custom1 != null ? custom1.hashCode() : 0);
        result = 31 * result + (custom2 != null ? custom2.hashCode() : 0);
        result = 31 * result + (orderUrl != null ? orderUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("profile=").append(profile);
        sb.append(",reviews=").append(reviews);
        sb.append(",video=").append(video);
        sb.append(",website=").append(website);
        sb.append(",menu=").append(menu);
        sb.append(",reservation=").append(reservation);
        sb.append(",map=").append(map);
        sb.append(",sendToFriend=").append(sendToFriend);
        sb.append(",email=").append(email);
        sb.append(",custom1=").append(custom1);
        sb.append(",custom2=").append(custom2);
        sb.append(",orderUrl=").append(orderUrl);
        sb.append('>');
        return sb.toString();
    }
}
