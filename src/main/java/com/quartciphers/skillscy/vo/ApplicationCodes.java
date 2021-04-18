package com.quartciphers.skillscy.vo;

public enum ApplicationCodes {

    NO_BODY_FOUND(1051, "Null response body found"),

    /* '/youtube' */
    INVALID_OR_NO_VIDEO_CHANNEL(1100, "The requested channel is invalid or it doesn't contain any video");

    private int appCode;
    private String appCodeDescription;

    ApplicationCodes(int appCode, String appCodeDescription) {
        this.appCode = appCode;
        this.appCodeDescription = appCodeDescription;
    }

    public int getAppCode() {
        return appCode;
    }

    public String getAppCodeDescription() {
        return appCodeDescription;
    }
}
