package au.com.repository;

import au.com.domain.Comment;
import au.com.domain.Issue;
import au.com.domain.User;
import au.com.exception.ResourceConstraintViolationException;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

import static au.com.helper.TestHelper.createIssue1;

/**
 * Created by amitsjoshi on 02/04/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class CommentRepositoryTest extends TestCase
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testIssueCreationAndRetrieval() throws ResourceConstraintViolationException {
        Issue issue = createIssue1();

        User reporter = userRepository.findOne(1L);
        issue.setReporter(reporter);

        issue = entityManager.persist(issue);
        entityManager.flush();

        Comment comment  = new Comment();
        comment.setAuthor(reporter);
        comment.setIssue(issue);
        Timestamp posted = new Timestamp(System.currentTimeMillis());
        comment.setPosted(posted);
        final String commentBody = "This is the first comment ever";
        comment.setBody(commentBody);

        comment = entityManager.persist(comment);
        entityManager.flush();

        Comment commentFromDb = commentRepository.findOne(1L);

        assertEquals(comment.getId(), commentFromDb.getId());
        assertEquals(reporter, commentFromDb.getAuthor());

        entityManager.remove(issue);
        entityManager.remove(comment);
    }
}
