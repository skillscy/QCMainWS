package com.quartciphers.skillscy.vo;

import com.quartciphers.skillscy.dto.ErrorResponse;
import com.quartciphers.skillscy.dto.WebServiceCommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class WebServiceException extends Exception {

    private ApplicationCodes applicationCode;
    private HTTPCodes httpCode;
    private WebExceptionType exceptionType;

    public WebServiceException(ApplicationCodes appCode, HTTPCodes httpCode) {
        this.applicationCode = appCode;
        this.httpCode = httpCode;
        this.exceptionType = WebExceptionType.INTERNAL_ERROR;
    }

    public WebServiceException(ApplicationCodes appCode, HTTPCodes httpCode, WebExceptionType exceptionType) {
        this.applicationCode = appCode;
        this.httpCode = httpCode;
        this.exceptionType = exceptionType;
    }

    public ResponseEntity<WebServiceCommonResponse> response() {
        WebServiceCommonResponse commonResponse = new WebServiceCommonResponse();
        ErrorResponse errorResponse = new ErrorResponse();

        commonResponse.setSuccess(false);
        errorResponse.setErrorCode(httpCode.getHttpCode());
        errorResponse.setErrorMessage(httpCode.getHttpCodeDescription());
        commonResponse.setError(errorResponse);

        if (this.exceptionType.equals(WebExceptionType.VALIDATION))
            return new ResponseEntity<>(commonResponse, HttpStatus.PRECONDITION_FAILED);

        return new ResponseEntity<>(commonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
