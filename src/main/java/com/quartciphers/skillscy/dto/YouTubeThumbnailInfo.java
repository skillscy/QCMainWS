package com.quartciphers.skillscy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YouTubeThumbnailInfo {

    private String pictureURL;
    private int width;
    private int height;

    @JsonProperty("PictureURL")
    public String getPictureURL() {
        return pictureURL;
    }

    @JsonProperty("url")
    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    @JsonProperty("Width")
    public int getWidth() {
        return width;
    }

    @JsonProperty("width")
    public void setWidth(int width) {
        this.width = width;
    }

    @JsonProperty("Height")
    public int getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(int height) {
        this.height = height;
    }
}
