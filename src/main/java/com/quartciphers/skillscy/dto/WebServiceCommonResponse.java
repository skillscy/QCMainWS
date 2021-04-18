package com.quartciphers.skillscy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebServiceCommonResponse {

    private boolean success;
    private ErrorResponse error;
    private Object data;

    public WebServiceCommonResponse(Object data) {
        this.data = data;
    }

    @JsonProperty("Success")
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @JsonProperty("Error")
    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    @JsonProperty("Data")
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseEntity<WebServiceCommonResponse> response() {
        this.setSuccess(true);
        return new ResponseEntity<>(this, HttpStatus.OK);
    }

}
