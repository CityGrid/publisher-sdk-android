package com.citygrid.content.places.detail;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;

public class CGPlacesDetailEditorial implements Serializable {
    private String editorialId;
    private URI url;
    private String title;
    private String author;
    private String review;
    private String pros;
    private String cons;
    private Date date;
    private int reviewRating;
    private int helpfulnessTotal;
    private int helpfulness;
    private int unhelpfulness;
    private String attributionText;
    private URI attributionLogo;
    private int attributionSource;

    private CGPlacesDetailEditorial(Builder builder) {
        editorialId = builder.editorialId;
        url = builder.url;
        title = builder.title;
        author = builder.author;
        review = builder.review;
        pros = builder.pros;
        cons = builder.cons;
        date = builder.date;
        reviewRating = builder.reviewRating;
        helpfulnessTotal = builder.helpfulnessTotal;
        helpfulness = builder.helpfulness;
        unhelpfulness = builder.unhelpfulness;
        attributionText = builder.attributionText;
        attributionLogo = builder.attributionLogo;
        attributionSource = builder.attributionSource;
    }

    public static class Builder {
        private String editorialId;
        private URI url;
        private String title;
        private String author;
        private String review;
        private String pros;
        private String cons;
        private Date date;
        private int reviewRating;
        private int helpfulnessTotal;
        private int helpfulness;
        private int unhelpfulness;
        private String attributionText;
        private URI attributionLogo;
        private int attributionSource;

        public Builder editorialId(String editorialId) {
            this.editorialId = editorialId;
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

        public Builder review(String review) {
            this.review = review;
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

        public Builder reviewRating(int reviewRating) {
            this.reviewRating = reviewRating;
            return this;
        }

        public Builder helpfulnessTotal(int helpfulnessTotal) {
            this.helpfulnessTotal = helpfulnessTotal;
            return this;
        }

        public Builder helpfulness(int helpfulness) {
            this.helpfulness = helpfulness;
            return this;
        }

        public Builder unhelpfulness(int unhelpfulness) {
            this.unhelpfulness = unhelpfulness;
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

        public CGPlacesDetailEditorial build() {
            return new CGPlacesDetailEditorial(this);
        }
    }

    public String getEditorialId() {
        return editorialId;
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

    public String getReview() {
        return review;
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

    public int getReviewRating() {
        return reviewRating;
    }

    public int getHelpfulnessTotal() {
        return helpfulnessTotal;
    }

    public int getHelpfulness() {
        return helpfulness;
    }

    public int getUnhelpfulness() {
        return unhelpfulness;
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
        if (!(o instanceof CGPlacesDetailEditorial)) return false;

        CGPlacesDetailEditorial that = (CGPlacesDetailEditorial) o;

        if (attributionSource != that.attributionSource) return false;
        if (helpfulness != that.helpfulness) return false;
        if (helpfulnessTotal != that.helpfulnessTotal) return false;
        if (reviewRating != that.reviewRating) return false;
        if (unhelpfulness != that.unhelpfulness) return false;
        if (attributionLogo != null ? !attributionLogo.equals(that.attributionLogo) : that.attributionLogo != null)
            return false;
        if (attributionText != null ? !attributionText.equals(that.attributionText) : that.attributionText != null)
            return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (cons != null ? !cons.equals(that.cons) : that.cons != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (editorialId != null ? !editorialId.equals(that.editorialId) : that.editorialId != null) return false;
        if (pros != null ? !pros.equals(that.pros) : that.pros != null) return false;
        if (review != null ? !review.equals(that.review) : that.review != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = editorialId != null ? editorialId.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (review != null ? review.hashCode() : 0);
        result = 31 * result + (pros != null ? pros.hashCode() : 0);
        result = 31 * result + (cons != null ? cons.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + reviewRating;
        result = 31 * result + helpfulnessTotal;
        result = 31 * result + helpfulness;
        result = 31 * result + unhelpfulness;
        result = 31 * result + (attributionText != null ? attributionText.hashCode() : 0);
        result = 31 * result + (attributionLogo != null ? attributionLogo.hashCode() : 0);
        result = 31 * result + attributionSource;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("<").append(getClass().getSimpleName()).append(" ");
        sb.append("editorialId=").append(editorialId);
        sb.append(",url=").append(url);
        sb.append(",title=").append(title);
        sb.append(",author=").append(author);
        sb.append(",review=").append(review);
        sb.append(",pros=").append(pros);
        sb.append(",cons=").append(cons);
        sb.append(",date=").append(date);
        sb.append(",reviewRating=").append(reviewRating);
        sb.append(",helpfulnessTotal=").append(helpfulnessTotal);
        sb.append(",helpfulness=").append(helpfulness);
        sb.append(",unhelpfulness=").append(unhelpfulness);
        sb.append(",attributionText=").append(attributionText);
        sb.append(",attributionLogo=").append(attributionLogo);
        sb.append(",attributionSource=").append(attributionSource);
        sb.append('>');
        return sb.toString();
    }
}
