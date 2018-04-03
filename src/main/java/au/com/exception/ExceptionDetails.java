package au.com.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by amitsjoshi on 03/04/18.
 */
public enum ExceptionDetails
{
    INVALID_REPORTER(HttpStatus.BAD_REQUEST, "An issue has to be reported by a valid user"),
    INVALID_ASSIGNEE(HttpStatus.BAD_REQUEST, "An issue has to be assigned to a valid user"),
    INVALID_ASSIGNEE_AND_ISSUE(HttpStatus.BAD_REQUEST, "A comment has to be assigned to a valid user and valid issue"),
    MANDATORY_INFORMATION_MISSING(HttpStatus.BAD_REQUEST, "Mandatory information missing");

    private HttpStatus httpStatus;
    private String message;

    ExceptionDetails(HttpStatus httpStatus, String message)
    {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus()
    {
        return httpStatus;
    }

    public String getMessage()
    {
        return message;
    }
}
