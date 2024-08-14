package com.chuwa.exception;

import com.chuwa.exception.CommonException;

public class BadRequestException extends CommonException {

    public BadRequestException(String message) {
        super(message, 400);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause, 400);
    }

    public BadRequestException(Throwable cause) {
        super(cause, 400);
    }
}
