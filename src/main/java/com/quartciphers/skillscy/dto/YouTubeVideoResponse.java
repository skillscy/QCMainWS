package com.quartciphers.skillscy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class YouTubeVideoResponse {

    private List<YouTubeCardResponse> videos;

    public YouTubeVideoResponse(List<YouTubeCardResponse> videos) {
        this.videos = videos;
    }

    @JsonProperty("Videos")
    public List<YouTubeCardResponse> getVideos() {
        return videos;
    }

    public void setVideos(List<YouTubeCardResponse> videos) {
        this.videos = videos;
    }
}
