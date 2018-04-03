package au.com.helper;

import au.com.domain.Issue;
import au.com.domain.User;
import au.com.dto.CommentDto;
import au.com.dto.IssueDto;
import au.com.exception.ResourceConstraintViolationException;
import au.com.util.DateTimeUtil;
import com.google.gson.Gson;

import java.sql.Timestamp;

/**
 * Created by amitsjoshi on 01/04/18.
 */
public class TestHelper
{
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

    public static Issue createIssue1() throws ResourceConstraintViolationException {
        Issue issue = new Issue();
        issue.setTitle("Issue number one");
        issue.setDescription("This is issue number one");
        issue.setStatus("new");
        issue.setCreated(DateTimeUtil.toTimestamp(DATE1));
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

    public static IssueDto createIssueDtoWithoutId1()
    {
        IssueDto issue = new IssueDto();
        issue.setStatus("new");
        issue.setTitle("New Issue One");
        issue.setDescription("This Is A New Issue Number One");
        issue.setCreated(DATE1);
        issue.setReporter(1L);
        return issue;
    }

    public static IssueDto createIssueDtoWithId1()
    {
        IssueDto issueDto = createIssueDtoWithoutId1();
        issueDto.setId(1L);
        return issueDto;
    }

    public static String toJson(Object obj)
    {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static IssueDto fromJson(String issueDtoJson)
    {
        Gson gson = new Gson();
        return gson.fromJson(issueDtoJson, IssueDto.class);
    }

    public static CommentDto createComment()
    {
        CommentDto commentDto = new CommentDto();
        commentDto.setIssue(1L);
        commentDto.setAuthor(1L);
        commentDto.setPosted(DATE1);
        commentDto.setBody("First comment ever!");
        return commentDto;
    }

    public static CommentDto createCommentWithId1()
    {
        CommentDto commentDto = createComment();
        commentDto.setId(1L);
        return commentDto;
    }
}
