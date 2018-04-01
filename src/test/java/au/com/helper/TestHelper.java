package au.com.helper;

import au.com.domain.Issue;
import au.com.domain.User;
import au.com.dto.IssueDto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by amitsjoshi on 01/04/18.
 */
public class TestHelper
{
    private static final SimpleDateFormat dateFormat
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String DATE1 = "2018-03-29 09:30:10";

    public static User createUser1()
    {
        User user = new User();
        user.setUserName("Amit");
        return user;
    }

    public static User createUserWithId1()
    {
        User user = new User();
        user.setUserName("Amit");
        user.setId(new Long(1));
        return user;
    }

    public static User createUser2()
    {
        User user = new User();
        user.setUserName("Joshi");
        return user;
    }

    public static User createUserWithId2()
    {
        User user = new User();
        user.setUserName("Joshi");
        user.setId(new Long(2));
        return user;
    }

    public static Issue createIssue1() {
        Issue issue = new Issue();
        issue.setTitle("Issue number one");
        issue.setDescription("This is issue number one");
        issue.setStatus("new");
        issue.setCreated(IssueDto.toTimestamp(DATE1));
        return issue;
    }

    public static Issue createIssueWithId1()
    {
        Issue issue = new Issue();
        issue.setId(1L);
        issue.setStatus("new");
        issue.setTitle("New Issue One");
        issue.setDescription("This Is A New Issue Number One");
        issue.setCreated(new Timestamp(System.currentTimeMillis()));
        return issue;
    }

    public static Issue createIssue2()
    {
        Issue issue2 = new Issue();
        issue2.setTitle("Issue number two");
        issue2.setDescription("This is issue number two");
        issue2.setStatus("open");
        issue2.setCreated(new Timestamp(System.currentTimeMillis()));
        return issue2;
    }

    public static IssueDto getIssueDtoWithoutId1()
    {
        IssueDto issue = new IssueDto();
        issue.setStatus("new");
        issue.setTitle("New Issue One");
        issue.setDescription("This Is A New Issue Number One");
        issue.setCreated(DATE1);
        issue.setReporter(1L);
        return issue;
    }
}
