package com.quartciphers.skillscy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qc.skillscy.commons.dto.StatusIndicator;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class YouTubeVideoResponse extends StatusIndicator {

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
