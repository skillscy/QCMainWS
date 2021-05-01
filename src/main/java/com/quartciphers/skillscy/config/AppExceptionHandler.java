package com.quartciphers.skillscy.config;

import com.qc.skillscy.commons.codes.HTTPCodes;
import com.qc.skillscy.commons.dto.ErrorResponse;
import com.qc.skillscy.commons.dto.StatusIndicator;
import com.qc.skillscy.commons.exceptions.WebExceptionType;
import com.qc.skillscy.commons.exceptions.WebServiceException;
import com.qc.skillscy.commons.loggers.CommonLogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(WebServiceException.class)
    public ResponseEntity<StatusIndicator> webServiceException(WebServiceException ex) {
        StatusIndicator apiResponse = ex.response();
        if (ex.getExceptionType().equals(WebExceptionType.VALIDATION)) {
            CommonLogger.error(this.getClass(), "Validation Exception -> ".concat(HttpStatus.PRECONDITION_FAILED.toString()));
            return new ResponseEntity<>(apiResponse, HttpStatus.PRECONDITION_FAILED);
        } else if (ex.getExceptionType().equals(WebExceptionType.CIRCUIT_BROKEN)) {
            CommonLogger.error(this.getClass(), "Circuit Broken -> ".concat(HttpStatus.CONFLICT.toString()));
            return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StatusIndicator> genericException(Exception ex) {
        StatusIndicator apiResponse = new StatusIndicator();
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setErrorCode(HTTPCodes.INTERNAL_ERROR.getHttpCode());
        errorResponse.setErrorMessage(HTTPCodes.INTERNAL_ERROR.getHttpCodeDescription());
        apiResponse.setError(errorResponse);

        CommonLogger.error(this.getClass(), "Exception -> ".concat(HttpStatus.INTERNAL_SERVER_ERROR.toString()));
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
