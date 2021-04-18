package com.quartciphers.skillscy.dto.YouTubeAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class YouTubeAPIResponse {

    private List<ItemInfo> videoList;

    public List<ItemInfo> getVideoList() {
        return videoList;
    }

    @JsonProperty("items")
    public void setVideoList(List<ItemInfo> videoList) {
        this.videoList = videoList;
    }
}
