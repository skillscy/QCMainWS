package com.quartciphers.skillscy.vo;

public enum HTTPCodes {

    SUCCESS(200, "Success"),
    NO_CONTENT(204, "Success - No content available to respond"),
    BAD_REQUEST(400, "Bad request"),
    UNAUTHORISED(401, "Unauthorised request"),
    FORBIDDEN(403, "Forbidden request"),
    NOT_FOUND(404, "Requested data not found"),
    PRECONDITION_FAILED(412, "Invalid input parameters"),
    INTERNAL_ERROR(500, "Internal server error"),
    SERVICE_UNAVAILABLE(503, "Service unavailable");

    private int httpCode;
    private String httpCodeDescription;

    HTTPCodes(int httpCode, String httpCodeDescription) {
        this.httpCode = httpCode;
        this.httpCodeDescription = httpCodeDescription;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getHttpCodeDescription() {
        return httpCodeDescription;
    }
}
