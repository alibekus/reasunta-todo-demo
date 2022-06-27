package kz.akbar.reasunta.todoproject.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    TASK_NOT_FOUND("error.task_not_found", HttpStatus.NOT_FOUND);

    private final String errorCode;
    private final HttpStatus status;
    ErrorType(String errorCode, HttpStatus status) {
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
