package com.quartciphers.skillscy.vo;

public enum ApplicationCodesLocal {

    // Error codes 1100 - 1149
    FIREBASE_GOOGLE_CREDENTIALS_STREAM_ERROR(1100, "Error occurred during parsing Google Credentials strings");

    private int appCode;
    private String appCodeDescription;

    ApplicationCodesLocal(int appCode, String appCodeDescription) {
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
