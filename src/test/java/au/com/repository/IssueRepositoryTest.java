package au.com.repository;

import au.com.domain.Issue;
import au.com.domain.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static au.com.helper.TestHelper.*;

/**
 * Created by amitsjoshi on 31/03/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class IssueRepositoryTest extends TestCase
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testIssueCreationAndRetrieval()
    {
        Issue issue = createIssue1();

        User reporter = userRepository.findOne(1L);
        issue.setReporter(reporter);

        issue = entityManager.persist(issue);
        entityManager.flush();

        Issue issueFromDb = issueRepository.findOne(issue.getId());

        assertEquals(issue.getId(), issueFromDb.getId());
        assertEquals(issue.getTitle(),issueFromDb.getTitle());
        assertEquals(issue.getDescription(),issueFromDb.getDescription());
        assertEquals(issue.getStatus(),issueFromDb.getStatus());
        assertEquals(reporter.getUserName(),issueFromDb.getReporter().getUserName());

        entityManager.remove(issue);
    }

    @Test
    public void testIssueRetrievalWithIncorrectId() {
        Issue issueFromDb = issueRepository.findOne(100L);

        assertNull(issueFromDb);
    }

    @Test
    public void testUpdationOfIssue()
    {
        Issue issue = createIssue1();

        User reporter = userRepository.findOne(1L);
        issue.setReporter(reporter);

        issue = entityManager.persist(issue);
        entityManager.flush();

        Issue issueFromDb = issueRepository.findOne(issue.getId());
        issueFromDb.setDescription("New updated description");
        issueFromDb.setTitle("New updated title");
        issueFromDb.setStatus("backlog");

        issueFromDb = issueRepository.save(issueFromDb);

        assertEquals(issue.getId(), issueFromDb.getId());
        assertEquals("New updated title",issueFromDb.getTitle());
        assertEquals("New updated description",issueFromDb.getDescription());
        assertEquals("backlog",issueFromDb.getStatus());
        assertEquals(reporter.getUserName(),issueFromDb.getReporter().getUserName());

        entityManager.remove(issue);
    }

    @Test
    public void testDeletionOfIssue()
    {
        Issue issue = createIssue1();

        User reporter = userRepository.findOne(1L);
        issue.setReporter(reporter);

        issue = entityManager.persist(issue);
        entityManager.flush();

        issueRepository.delete(issue.getId());

        Issue issueFromDb = issueRepository.findOne(issue.getId());

        assertEquals(null, issueFromDb);
    }

    @Test
    public void testFilterByAssignee()
    {
        Issue issue1 = createIssue1();

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();

        issue2.setReporter(reporter);

        issue2.setAssignee(assignee);

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        List<Issue> assignedIssues = issueRepository.findByAssignee(assignee, null);

        assertEquals(2,assignedIssues.size());
        assertEquals(issue1.getId(), assignedIssues.get(0).getId());
        assertEquals(issue1.getAssignee().getId(), assignedIssues.get(0).getAssignee().getId());
        assertEquals(issue2.getId(), assignedIssues.get(1).getId());
        assertEquals(issue2.getAssignee().getId(), assignedIssues.get(1).getAssignee().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);

    }

    @Test
    public void testFilterByAssigneeWithPagination()
    {
        Issue issue1 = createIssue1();

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();

        issue2.setReporter(reporter);

        issue2.setAssignee(assignee);

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        List<Issue> assignedIssues = issueRepository.findByAssignee(assignee, new PageRequest(0, 2, null));

        assertEquals(2,assignedIssues.size());
        assertEquals(issue1.getId(), assignedIssues.get(0).getId());
        assertEquals(issue1.getAssignee().getId(), assignedIssues.get(0).getAssignee().getId());
        assertEquals(issue2.getId(), assignedIssues.get(1).getId());
        assertEquals(issue2.getAssignee().getId(), assignedIssues.get(1).getAssignee().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);

    }

    @Test
    public void testFilterByReporter()
    {
        Issue issue1 = createIssue1();

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();

        issue2.setReporter(reporter);

        issue2.setAssignee(assignee);

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        List<Issue> assignedIssues = issueRepository.findByReporter(reporter, null);

        assertEquals(2,assignedIssues.size());
        assertEquals(issue1.getId(), assignedIssues.get(0).getId());
        assertEquals(issue1.getReporter().getId(), assignedIssues.get(0).getReporter().getId());
        assertEquals(issue2.getId(), assignedIssues.get(1).getId());
        assertEquals(issue2.getReporter().getId(), assignedIssues.get(1).getReporter().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);

    }

    @Test
    public void testFilterByReporterWithPagination()
    {
        Issue issue1 = createIssue1();

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();

        issue2.setReporter(reporter);

        issue2.setAssignee(assignee);

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        List<Issue> assignedIssues = issueRepository.findByReporter(reporter, new PageRequest(0, 1, null));

        assertEquals(1,assignedIssues.size());
        assertEquals(issue1.getId(), assignedIssues.get(0).getId());
        assertEquals(issue1.getReporter().getId(), assignedIssues.get(0).getReporter().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);

    }

    @Test
    public void testFilterByStatus()
    {
        Issue issue1 = createIssue1();

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();
        issue2.setReporter(reporter);
        issue2.setAssignee(assignee);

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        List<Issue> assignedIssues = issueRepository.findByStatus("new", null);

        assertEquals(1,assignedIssues.size());
        assertEquals(issue1.getId(), assignedIssues.get(0).getId());
        assertEquals(issue1.getTitle(), assignedIssues.get(0).getTitle());
        assertEquals(issue1.getReporter().getId(), assignedIssues.get(0).getReporter().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);
    }

    @Test
    public void testFilterByStatusByPagination()
    {
        Issue issue1 = createIssue1();

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();
        issue2.setReporter(reporter);
        issue2.setAssignee(assignee);
        issue2.setStatus("new");

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        List<Issue> assignedIssues = issueRepository.findByStatus("new", new PageRequest(0, 2, null));

        assertEquals(2,assignedIssues.size());
        assertEquals(issue1.getId(), assignedIssues.get(0).getId());
        assertEquals(issue1.getTitle(), assignedIssues.get(0).getTitle());
        assertEquals(issue1.getReporter().getId(), assignedIssues.get(0).getReporter().getId());
        assertEquals(issue2.getId(), assignedIssues.get(1).getId());
        assertEquals(issue2.getTitle(), assignedIssues.get(1).getTitle());
        assertEquals(issue2.getReporter().getId(), assignedIssues.get(1).getReporter().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);
    }

    @Test
    public void testFilterByAssigneeReporterStatus()
    {
        Issue issue1 = createIssue1();

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();

        User reporter2 = new User();
        reporter2.setUserName("AJ");
        reporter2 = entityManager.persist(reporter2);
        issue2.setReporter(reporter2);

        issue2.setAssignee(assignee);

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        List<Issue> assignedIssues = issueRepository.findByAssigneeAndReporterAndStatus(assignee, reporter2, "open", null);

        assertEquals(1,assignedIssues.size());
        assertEquals(issue2.getId(), assignedIssues.get(0).getId());
        assertEquals(issue2.getTitle(), assignedIssues.get(0).getTitle());
        assertEquals(issue2.getReporter().getId(), assignedIssues.get(0).getReporter().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);
    }

    @Test
    public void testFilterByAssigneeReporterStatusWithPagination()
    {
        Issue issue1 = createIssue1();

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();

        User reporter2 = new User();
        reporter2.setUserName("AJ");
        reporter2 = entityManager.persist(reporter2);
        issue2.setReporter(reporter2);

        issue2.setAssignee(assignee);

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        List<Issue> assignedIssues = issueRepository.findByAssigneeAndReporterAndStatus(assignee, reporter2, "open", new PageRequest(0, 2, null));

        assertEquals(1,assignedIssues.size());
        assertEquals(issue2.getId(), assignedIssues.get(0).getId());
        assertEquals(issue2.getTitle(), assignedIssues.get(0).getTitle());
        assertEquals(issue2.getReporter().getId(), assignedIssues.get(0).getReporter().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);
    }

    @Test
    public void testSortIssuesByCreationDateAsc()
    {
        Issue issue1 = createIssue1();

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();

        issue2.setReporter(reporter);

        issue2.setAssignee(assignee);

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        List<Issue> assignedIssues = issueRepository.findAllByOrderByCreatedAsc(null);

        assertEquals(7,assignedIssues.size());
        assertEquals(issue1.getId(), assignedIssues.get(5).getId());
        assertEquals(issue1.getTitle(), assignedIssues.get(5).getTitle());
        assertEquals(issue1.getReporter().getId(), assignedIssues.get(5).getReporter().getId());
        assertEquals(issue2.getId(), assignedIssues.get(6).getId());
        assertEquals(issue2.getTitle(), assignedIssues.get(6).getTitle());
        assertEquals(issue2.getReporter().getId(), assignedIssues.get(6).getReporter().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);
    }

    @Test
    public void testSortIssuesByCreationDateAscWithPagination()
    {
        Issue issue1 = createIssue1();

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();

        issue2.setReporter(reporter);

        issue2.setAssignee(assignee);

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        List<Issue> assignedIssues = issueRepository.findAllByOrderByCreatedAsc(new PageRequest(3, 2, null));

        assertEquals(1,assignedIssues.size());
        assertEquals(issue2.getId(), assignedIssues.get(0).getId());
        assertEquals(issue2.getTitle(), assignedIssues.get(0).getTitle());
        assertEquals(issue2.getReporter().getId(), assignedIssues.get(0).getReporter().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);
    }


    @Test
    public void testSortIssuesByCreationDateDsc() {

        Issue issue1 = createIssue1();

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();

        issue2.setReporter(reporter);

        issue2.setAssignee(assignee);

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        List<Issue> assignedIssues = issueRepository.findAllByOrderByCreatedDesc(null);

        assertEquals(7,assignedIssues.size());
        assertEquals(issue2.getId(), assignedIssues.get(0).getId());
        assertEquals(issue2.getTitle(), assignedIssues.get(0).getTitle());
        assertEquals(issue2.getReporter().getId(), assignedIssues.get(0).getReporter().getId());
        assertEquals(issue1.getId(), assignedIssues.get(1).getId());
        assertEquals(issue1.getTitle(), assignedIssues.get(1).getTitle());
        assertEquals(issue1.getReporter().getId(), assignedIssues.get(1).getReporter().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);
    }

    @Test
    public void testSortIssuesByCreationDateDscWithPagination() {

        Issue issue1 = createIssue1();

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();

        issue2.setReporter(reporter);

        issue2.setAssignee(assignee);

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        List<Issue> assignedIssues = issueRepository.findAllByOrderByCreatedDesc(new PageRequest(0, 2, null));

        assertEquals(2,assignedIssues.size());
        assertEquals(issue2.getId(), assignedIssues.get(0).getId());
        assertEquals(issue2.getTitle(), assignedIssues.get(0).getTitle());
        assertEquals(issue2.getReporter().getId(), assignedIssues.get(0).getReporter().getId());
        assertEquals(issue1.getId(), assignedIssues.get(1).getId());
        assertEquals(issue1.getTitle(), assignedIssues.get(1).getTitle());
        assertEquals(issue1.getReporter().getId(), assignedIssues.get(1).getReporter().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);
    }

    @Test
    public void testFindIssuesInDateRange() {
        Issue issue1 = createIssue1();

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        issue1.setCreated(new Timestamp(yesterday.getTimeInMillis()));

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();

        Calendar tomorrow = Calendar.getInstance();
        issue2.setCreated(new Timestamp(tomorrow.getTimeInMillis()));

        issue2.setReporter(reporter);

        issue2.setAssignee(assignee);

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        yesterday.add(Calendar.DATE, -1);
        Timestamp startDate = new Timestamp(yesterday.getTimeInMillis());
        tomorrow.add(Calendar.DATE, 1);
        Timestamp endDate = new Timestamp(tomorrow.getTimeInMillis());

        List<Issue> assignedIssues = issueRepository.findByCreatedBetween(startDate, endDate, null);

        assertEquals(2,assignedIssues.size());
        assertEquals(issue1.getId(), assignedIssues.get(0).getId());
        assertEquals(issue1.getTitle(), assignedIssues.get(0).getTitle());
        assertEquals(issue1.getReporter().getId(), assignedIssues.get(0).getReporter().getId());
        assertEquals(issue2.getId(), assignedIssues.get(1).getId());
        assertEquals(issue2.getTitle(), assignedIssues.get(1).getTitle());
        assertEquals(issue2.getReporter().getId(), assignedIssues.get(1).getReporter().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);
    }

    @Test
    public void testFindIssuesInDateRangeWithPagination() {
        Issue issue1 = createIssue1();

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        issue1.setCreated(new Timestamp(yesterday.getTimeInMillis()));

        User reporter = createUser1();
        reporter = entityManager.persist(reporter);
        issue1.setReporter(reporter);

        User assignee = createUser2();
        assignee = entityManager.persist(assignee);
        issue1.setAssignee(assignee);

        issue1 = entityManager.persist(issue1);
        entityManager.flush();

        Issue issue2 = createIssue2();

        Calendar tomorrow = Calendar.getInstance();
        issue2.setCreated(new Timestamp(tomorrow.getTimeInMillis()));

        issue2.setReporter(reporter);

        issue2.setAssignee(assignee);

        issue2 = entityManager.persist(issue2);
        entityManager.flush();

        yesterday.add(Calendar.DATE, -1);
        Timestamp startDate = new Timestamp(yesterday.getTimeInMillis());
        tomorrow.add(Calendar.DATE, 1);
        Timestamp endDate = new Timestamp(tomorrow.getTimeInMillis());

        List<Issue> assignedIssues = issueRepository.findByCreatedBetween(startDate, endDate, new PageRequest(0, 1, null));

        assertEquals(1,assignedIssues.size());
        assertEquals(issue1.getId(), assignedIssues.get(0).getId());
        assertEquals(issue1.getTitle(), assignedIssues.get(0).getTitle());
        assertEquals(issue1.getReporter().getId(), assignedIssues.get(0).getReporter().getId());

        entityManager.remove(reporter);
        entityManager.remove(assignee);
        entityManager.remove(issue1);
        entityManager.remove(issue2);
    }
}
