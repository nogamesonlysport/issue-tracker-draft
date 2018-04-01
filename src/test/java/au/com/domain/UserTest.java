package au.com.domain;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by amitsjoshi on 31/03/18.
 */
public class UserTest extends TestCase
{
    @Test
    public void testUserIdAndName()
    {
        final User user = new User();
        user.setId(1L);
        user.setUserName("John");

        assertEquals(1L, user.getId().longValue());
        assertEquals("John", user.getUserName());
    }

   /* @Test
    public void testIssueReportingAndAssignment()
    {
        final User reporter = new User();
        reporter.setId(1L);
        reporter.setUserName("John");

        assertEquals(1L, reporter.getId());
        assertEquals("John", reporter.getUserName());

        final Issue issue = new Issue();

        issue.setId(1L);
        issue.setTitle("Issue number one");
        issue.setDescription("This is issue number one");
        issue.setStatus("backlog");
        Date creationDate = new Date(Calendar.getInstance().getTime().getTime());
        issue.setCreated(creationDate);

        Date completionDate = new Date(Calendar.getInstance().getTime().getTime());
        issue.setCompleted(completionDate);

        issue.setReporter(reporter);

        User assignee = new User();
        assignee.setId(2L);
        assignee.setUserName("Jack");

        issue.setAssignee(assignee);

        assertEquals(issue, reporter.getReportedIssues().get(0));
        assertEquals(issue, assignee.getAssignedIssues().get(0));

    }*/

}
