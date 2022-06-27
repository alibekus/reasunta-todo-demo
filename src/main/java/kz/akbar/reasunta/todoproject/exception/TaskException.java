package kz.akbar.reasunta.todoproject.exception;

public class TaskException extends RuntimeException{

    private final ErrorType errorType;

    public TaskException(ErrorType errorType) {
        super(String.format("type=%s, status=%s", errorType.getErrorCode(), errorType.getStatus()));
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return this.errorType;
    }

}
