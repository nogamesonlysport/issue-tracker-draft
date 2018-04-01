package au.com.domain;

import au.com.dto.IssueDto;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.Timestamp;

/**
 * Created by amitsjoshi on 31/03/18.
 */
public class IssueTest extends TestCase
{
    @Test
    public void testIssueAttributes()
    {
        Issue issue = new Issue();
        issue.setId(1L);
        issue.setTitle("Issue number one");
        issue.setDescription("This is issue number one");
        issue.setStatus("backlog");
        Timestamp creationDate = IssueDto.toTimestamp("2018-04-01 10:43:30.209");
        issue.setCreated(creationDate);

        Timestamp completionDate = IssueDto.toTimestamp("2018-04-01 11:43:30.209");
        issue.setCompleted(completionDate);

        User reporter = new User();
        reporter.setId(1L);
        reporter.setUserName("John");
        issue.setReporter(reporter);

        User assignee = new User();
        assignee.setId(2L);
        assignee.setUserName("John");
        issue.setAssignee(assignee);

        assertEquals(1L, issue.getId().longValue());
        assertEquals("Issue number one", issue.getTitle());
        assertEquals("This is issue number one", issue.getDescription());
        assertEquals("backlog", issue.getStatus());
        assertEquals(creationDate, issue.getCreated());
        assertEquals(completionDate, issue.getCompleted());
        assertEquals(reporter, issue.getReporter());
        assertEquals(assignee, issue.getAssignee());


    }
}
