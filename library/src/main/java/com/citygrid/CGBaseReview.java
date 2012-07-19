package com.citygrid;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;

public class CGBaseReview implements CGReview, Serializable {
    private String reviewId;
    private URI url;
    private String title;
    private String author;
    private String text;
    private String pros;
    private String cons;
    private Date date;
    private int rating;
    private int helpful;
    private int unhelpful;
    private String attributionText;
    private URI attributionLogo;
    private int attributionSource;

    public CGBaseReview(Builder builder) {
        reviewId = builder.reviewId;
        url = builder.url;
        title = builder.title;
        author = builder.author;
        text = builder.text;
        pros = builder.pros;
        cons = builder.cons;
        date = builder.date;
        rating = builder.rating;
        helpful = builder.helpful;
        unhelpful = builder.unhelpful;
        attributionText = builder.attributionText;
        attributionLogo = builder.attributionLogo;
        attributionSource = builder.attributionSource;
    }

    public static class Builder {
        private String reviewId;
        private URI url;
        private String title;
        private String author;
        private String text;
        private String pros;
        private String cons;
        private Date date;
        private int rating;
        private int helpful;
        private int unhelpful;
        private String attributionText;
        private URI attributionLogo;
        private int attributionSource;

        public Builder reviewId(String reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public Builder url(URI url) {
            this.url = url;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder pros(String pros) {
            this.pros = pros;
            return this;
        }

        public Builder cons(String cons) {
            this.cons = cons;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Builder rating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder helpful(int helpful) {
            this.helpful = helpful;
            return this;
        }

        public Builder unhelpful(int unhelpful) {
            this.unhelpful = unhelpful;
            return this;
        }

        public Builder attributionText(String attributionText) {
            this.attributionText = attributionText;
            return this;
        }

        public Builder attributionLogo(URI attributionLogo) {
            this.attributionLogo = attributionLogo;
            return this;
        }

        public Builder attributionSource(int attributionSource) {
            this.attributionSource = attributionSource;
            return this;
        }

        public CGBaseReview build() {
            return  new CGBaseReview(this);
        }
    }

    public String getReviewId() {
        return reviewId;
    }

    public URI getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public String getPros() {
        return pros;
    }

    public String getCons() {
        return cons;
    }

    public Date getDate() {
        return date;
    }

    public int getRating() {
        return rating;
    }

    public int getHelpful() {
        return helpful;
    }

    public int getUnhelpful() {
        return unhelpful;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public URI getAttributionLogo() {
        return attributionLogo;
    }

    public int getAttributionSource() {
        return attributionSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CGBaseReview)) return false;

        CGBaseReview baseReview = (CGBaseReview) o;

        if (attributionSource != baseReview.attributionSource) return false;
        if (helpful != baseReview.helpful) return false;
        if (rating != baseReview.rating) return false;
        if (unhelpful != baseReview.unhelpful) return false;
        if (attributionLogo != null ? !attributionLogo.equals(baseReview.attributionLogo) : baseReview.attributionLogo != null)
            return false;
        if (attributionText != null ? !attributionText.equals(baseReview.attributionText) : baseReview.attributionText != null)
            return false;
        if (author != null ? !author.equals(baseReview.author) : baseReview.author != null) return false;
        if (cons != null ? !cons.equals(baseReview.cons) : baseReview.cons != null) return false;
        if (date != null ? !date.equals(baseReview.date) : baseReview.date != null) return false;
        if (pros != null ? !pros.equals(baseReview.pros) : baseReview.pros != null) return false;
        if (reviewId != null ? !reviewId.equals(baseReview.reviewId) : baseReview.reviewId != null) return false;
        if (text != null ? !text.equals(baseReview.text) : baseReview.text != null) return false;
        if (title != null ? !title.equals(baseReview.title) : baseReview.title != null) return false;
        if (url != null ? !url.equals(baseReview.url) : baseReview.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reviewId != null ? reviewId.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (pros != null ? pros.hashCode() : 0);
        result = 31 * result + (cons != null ? cons.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + helpful;
        result = 31 * result + unhelpful;
        result = 31 * result + (attributionText != null ? attributionText.hashCode() : 0);
        result = 31 * result + (attributionLogo != null ? attributionLogo.hashCode() : 0);
        result = 31 * result + attributionSource;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<").append(getClass().getSimpleName()).append(" ")
                .append("reviewId=").append(reviewId)
                .append(",url=").append(url)
                .append(",title=").append(title)
                .append(",author=").append(author)
                .append(",text=").append(text)
                .append(",pros=").append(pros)
                .append(",cons=").append(cons)
                .append(",date=").append(date)
                .append(",rating=").append(rating)
                .append(",helpful=").append(helpful)
                .append(",unhelpful=").append(unhelpful)
                .append(",attributionText=").append(attributionText)
                .append(",attributionLogo=").append(attributionLogo)
                .append(",attributionSource=").append(attributionSource)
                .append(">");
        return builder.toString();
    }



}
