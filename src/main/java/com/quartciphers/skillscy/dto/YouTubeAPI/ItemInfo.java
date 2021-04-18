package com.quartciphers.skillscy.dto.YouTubeAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quartciphers.skillscy.dto.YouTubeCardResponse;

import java.util.Map;

public class ItemInfo {

    private String videoID;
    private YouTubeCardResponse snippetInfo;

    public String getVideoID() {
        return videoID;
    }

    @JsonProperty("id")
    public void setVideoID(Map<String, String> videoID) {
        this.videoID = videoID.get("videoId");
    }

    public YouTubeCardResponse getSnippetInfo() {
        return snippetInfo;
    }

    @JsonProperty("snippet")
    public void setSnippetInfo(YouTubeCardResponse snippetInfo) {
        this.snippetInfo = snippetInfo;
    }
}
