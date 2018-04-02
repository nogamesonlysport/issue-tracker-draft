package au.com.util;

import au.com.exception.ResourceConstraintViolationException;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amitsjoshi on 02/04/18.
 */
public class DateTimeUtil
{
    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Timestamp toTimestamp(final String dateString) throws ResourceConstraintViolationException {
        Timestamp timestamp = null;
        try {
            Date date = dateFormat.parse(dateString);
            timestamp = new Timestamp(dateFormat.parse(dateString).getTime());
        } catch (ParseException e) {
            throw new ResourceConstraintViolationException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return timestamp;
    }

    public static String toString(final Timestamp timestamp)
    {
        Date date = new Date(timestamp.getTime());
        return dateFormat.format(date);
    }
}
