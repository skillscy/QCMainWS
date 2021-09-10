package com.quartciphers.skillscy.config;

import com.qc.skillscy.commons.codes.HTTPCodes;
import com.qc.skillscy.commons.dto.ErrorResponse;
import com.qc.skillscy.commons.dto.StatusIndicator;
import com.qc.skillscy.commons.exceptions.WebExceptionType;
import com.qc.skillscy.commons.exceptions.WebServiceException;
import com.qc.skillscy.commons.loggers.CommonLogger;
import com.qc.skillscy.commons.misc.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AppExceptionHandler {

    @Autowired
    private HttpServletRequest httpServletRequest;

    private boolean checkStackTrace() {
        String value = httpServletRequest.getHeader("stack_trace");
        if ("true".equalsIgnoreCase(value)) {
            CommonLogger.info(AppExceptionHandler.class, "[stack_trace] header has value 'true'");
            return true;
        } else if (value == null || "false".equalsIgnoreCase(value)) {
            CommonLogger.info(AppExceptionHandler.class,"[stack_trace] header is not found or has value 'false'");
            return false;
        } else {
            CommonLogger.warning(AppExceptionHandler.class, "[stack_trace] headers doesn't contain a value among true/ false");
            return false;
        }
    }

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

        if (checkStackTrace()) {
            ex.printStackTrace();
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StatusIndicator> genericException(Exception ex) {
        CommonLogger.error(this.getClass(), "Exception occurred [".concat(Validator.ignoreNullByString(ex.getMessage())).concat("]"));
        StatusIndicator apiResponse = new StatusIndicator();
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setErrorCode(HTTPCodes.INTERNAL_ERROR.getHttpCode());
        errorResponse.setErrorMessage(HTTPCodes.INTERNAL_ERROR.getHttpCodeDescription());
        apiResponse.setError(errorResponse);

        CommonLogger.error(this.getClass(), "Exception -> ".concat(HttpStatus.INTERNAL_SERVER_ERROR.toString()));

        if (checkStackTrace()) {
            ex.printStackTrace();
        }

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
