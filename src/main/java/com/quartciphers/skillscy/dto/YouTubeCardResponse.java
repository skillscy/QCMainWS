package com.quartciphers.skillscy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YouTubeCardResponse {

    private String title;
    private String description;
    private String publishedTime;
    private String videoPlayURL;
    private YouTubeThumbnails videoThumbnails;

    @JsonProperty("VideoTitle")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("VideoDescription")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("PublishTime")
    public String getPublishedTime() {
        return publishedTime;
    }

    @JsonProperty("publishTime")
    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    @JsonProperty("VideoURL")
    public String getVideoPlayURL() {
        return videoPlayURL;
    }

    public void setVideoPlayURL(String videoPlayURL) {
        this.videoPlayURL = "https://www.youtube.com/watch?v=".concat(videoPlayURL);
    }

    @JsonProperty("Thumbnails")
    public YouTubeThumbnails getVideoThumbnails() {
        return videoThumbnails;
    }

    @JsonProperty("thumbnails")
    public void setVideoThumbnails(YouTubeThumbnails videoThumbnails) {
        this.videoThumbnails = videoThumbnails;
    }
}
