package com.springredispostgres.exception;

import org.springframework.util.Assert;

public class ClientBackendException extends RuntimeException {

    private static final long serialVersionUID = 2541463998888794608L;

    private final ErrorCode errorCode;

    public ClientBackendException(ErrorCode errorCode) {
        super();
        Assert.notNull(errorCode, "Error code required");
        this.errorCode = errorCode;
    }

    public ClientBackendException(ErrorCode errorCode, String message) {
        super(message);
        Assert.notNull(errorCode, "Error code required");
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
