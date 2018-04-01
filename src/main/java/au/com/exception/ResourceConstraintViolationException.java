package au.com.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by amitsjoshi on 01/04/18.
 */
public class ResourceConstraintViolationException extends Exception
{
    private String message;

    private HttpStatus httpStatus;

    public ResourceConstraintViolationException(HttpStatus httpStatus, String messsage)
    {
        this.httpStatus = httpStatus;
        this.message = messsage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
