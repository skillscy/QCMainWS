package com.quartciphers.skillscy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YouTubeCardResponse {

    private String videoTitle;
    private String videoDescription;
    private String publishTime;
    private String videoURL;
    private YouTubeThumbnails thumbnails;

    @JsonProperty("VideoTitle")
    public String getVideoTitle() {
        return videoTitle;
    }

    @JsonProperty("title")
    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    @JsonProperty("VideoDescription")
    public String getVideoDescription() {
        return videoDescription;
    }

    @JsonProperty("description")
    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    @JsonProperty("PublishTime")
    public String getPublishTime() {
        return publishTime;
    }

    @JsonProperty("publishTime")
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    @JsonProperty("VideoURL")
    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = "https://www.youtube.com/watch?v=".concat(videoURL);
    }

    @JsonProperty("Thumbnails")
    public YouTubeThumbnails getThumbnails() {
        return thumbnails;
    }

    @JsonProperty("thumbnails")
    public void setThumbnails(YouTubeThumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }
}
