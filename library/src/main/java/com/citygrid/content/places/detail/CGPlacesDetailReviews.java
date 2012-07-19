package com.citygrid.content.places.detail;

import com.citygrid.CGBaseReview;

import java.io.Serializable;
import java.util.Arrays;

public class CGPlacesDetailReviews implements Serializable {
    private int rating;
    private int count;
    private int shown;
    private CGBaseReview[] reviews;

    public CGPlacesDetailReviews(int rating, int count, int shown, CGBaseReview[] reviews) {
        this.rating = rating;
        this.count = count;
        this.shown = shown;
        this.reviews = reviews;
    }

    public int getRating() {
        return rating;
    }

    public int getCount() {
        return count;
    }

    public int getShown() {
        return shown;
    }

    public CGBaseReview[] getReviews() {
        return reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGPlacesDetailReviews)) return false;

        CGPlacesDetailReviews that = (CGPlacesDetailReviews) o;

        if (count != that.count) return false;
        if (rating != that.rating) return false;
        if (shown != that.shown) return false;
        if (!Arrays.equals(reviews, that.reviews)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rating;
        result = 31 * result + count;
        result = 31 * result + shown;
        result = 31 * result + (reviews != null ? Arrays.hashCode(reviews) : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("rating=").append(rating);
        sb.append(",count=").append(count);
        sb.append(",shown=").append(shown);
        sb.append(",reviews=").append(reviews == null ? "null" : Arrays.toString(reviews));
        sb.append('>');
        return sb.toString();
    }
}
