package com.quartciphers.skillscy.vo;

public class Validator {

    private Validator() {}

    public static void notNull(Object value) throws WebServiceException {
        if (value == null)
            throw new WebServiceException(ApplicationCodes.VALIDATION_NULL_FOUND, HTTPCodes.BAD_REQUEST, WebExceptionType.VALIDATION);
    }

}
