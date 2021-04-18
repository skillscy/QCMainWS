package com.quartciphers.skillscy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YouTubeThumbnails {

    private YouTubeThumbnailInfo defaultThumbnail;
    private YouTubeThumbnailInfo mediumThumbnail;
    private YouTubeThumbnailInfo highThumbnail;

    @JsonProperty("Default")
    public YouTubeThumbnailInfo getDefaultThumbnail() {
        return defaultThumbnail;
    }

    @JsonProperty("default")
    public void setDefaultThumbnail(YouTubeThumbnailInfo defaultThumbnail) {
        this.defaultThumbnail = defaultThumbnail;
    }

    @JsonProperty("Medium")
    public YouTubeThumbnailInfo getMediumThumbnail() {
        return mediumThumbnail;
    }

    @JsonProperty("medium")
    public void setMediumThumbnail(YouTubeThumbnailInfo mediumThumbnail) {
        this.mediumThumbnail = mediumThumbnail;
    }

    @JsonProperty("High")
    public YouTubeThumbnailInfo getHighThumbnail() {
        return highThumbnail;
    }

    @JsonProperty("high")
    public void setHighThumbnail(YouTubeThumbnailInfo highThumbnail) {
        this.highThumbnail = highThumbnail;
    }
}
