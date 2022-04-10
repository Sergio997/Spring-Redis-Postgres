package com.springredispostgres.exception;

import lombok.Getter;
import lombok.Setter;

public enum ErrorCode {

    /* 404 */
    NOT_FOUND(
            "404-001",
            "Not found.",
            404),

    USER_NOT_FOUND(
            "404-002",
            "User not found",
            404),

    /* 400 */

    BAD_REQUEST(
            "400-001",
            "Bad request",
            400),

    /* 401 */

    NOT_AUTHORIZED(
            "401-001",
            "Not authorized",
            401),

    JWT_EXPIRE(
            "401-002",
            "JWT is expire",
            401),

    /* 409 */
    TEST_ERROR_CODE(
            "409-001",
            "Test.",
            409),

    /* 500 */
    UNKNOWN_SERVER_ERROR(
            "500-001",
            "Unforeseen and unhandled error",
            500),

    /* 501 */
    NOT_IMPLEMENTED(
            "501-001",
            "Method has not been implemented",
            501);


    @Getter
    private final Data data;

    ErrorCode(String code, String description, int httpResponseCode) {
        this.data = new Data(code, description, httpResponseCode);
    }

    /**
     * Bean class to make rendering this enums' content easier. For instance:
     *
     * @see {@link com.springredispostgres.exception.RestExceptionHandler}
     */
    public static final class Data {

        @Getter
        private final String code;

        @Getter
        @Setter
        private String description;

        @Getter
        private final int httpResponseCode;

        @Setter
        @Getter
        private String label;

        Data(String code, String description, int httpResponseCode) {
            this.code = code;
            this.description = description;
            this.httpResponseCode = httpResponseCode;
        }
    }

}
