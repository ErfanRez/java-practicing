package com.danacup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApiError {

    public static class Custom extends ResponseStatusException {
        public Custom(String message, HttpStatus status) {
            super(status, message);
        }
    }

    public static class BadRequest extends ResponseStatusException {
        public BadRequest() {
            super(HttpStatus.BAD_REQUEST, "Bad Request");
        }
        public BadRequest(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
    }

    public static class Conflict extends ResponseStatusException {
        public Conflict() {
            super(HttpStatus.CONFLICT, "Conflict");
        }
        public Conflict(String message) {
            super(HttpStatus.CONFLICT, message);
        }
    }

    public static class NotFound extends ResponseStatusException {
        public NotFound() {
            super(HttpStatus.NOT_FOUND, "Not Found Error");
        }
        public NotFound(String message) {
            super(HttpStatus.NOT_FOUND, message);
        }
    }

    public static class Unauthorized extends ResponseStatusException {
        public Unauthorized() {
            super(HttpStatus.UNAUTHORIZED, "Unauthorized Request");
        }
        public Unauthorized(String message) {
            super(HttpStatus.UNAUTHORIZED, message);
        }
    }

    public static class Forbidden extends ResponseStatusException {
        public Forbidden() {
            super(HttpStatus.FORBIDDEN, "Forbidden (Invalid access to this section)");
        }
        public Forbidden(String message) {
            super(HttpStatus.FORBIDDEN, message);
        }
    }
}
