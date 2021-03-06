package au.com.service;

import au.com.domain.Comment;
import au.com.domain.Issue;
import au.com.domain.User;
import au.com.dto.IssueDto;
import au.com.exception.ResourceConstraintViolationException;
import au.com.helper.TestHelper;
import au.com.repository.CommentRepository;
import au.com.repository.IssueRepository;
import au.com.repository.UserRepository;
import au.com.util.DateTimeUtil;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.ParseException;

/**
 * Created by amitsjoshi on 31/03/18.
 */
@RunWith(SpringRunner.class)
public class IssueTrackerServiceTest extends TestCase
{
    ModelMapper modelMapper = new ModelMapper();

    @TestConfiguration
    static class IssueTrackerServiceTestContextConfiguration
    {
        @Bean
        public IssueTrackerService issueTrackerService()
        {
            return new IssueTrackerServiceImpl();
        }
    }

    @Autowired
    private IssueTrackerService issueTrackerService;

    @MockBean
    private IssueRepository issueRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CommentRepository commentRepository;


    @Before
    public void setUp() throws ParseException, ResourceConstraintViolationException {
        User user1 = TestHelper.createUserWithId1();
        Mockito.when(userRepository.findOne(1L)).thenReturn(user1);

        User user2 = TestHelper.createUserWithId2();
        Mockito.when(userRepository.findOne(2L)).thenReturn(user2);

        IssueDto issueDto = TestHelper.createIssueDtoWithoutId1();
        Issue issueFromRequestDto = toIssue(issueDto);
        issueFromRequestDto.setReporter(user1);
        Issue issueWithId = TestHelper.createIssueWithId1();
        issueWithId.setReporter(user1);

        Mockito.when(issueRepository.save(issueFromRequestDto)).thenReturn(issueWithId);

        Mockito.when(issueRepository.findOne(1L)).thenReturn(issueWithId);

        IssueDto issueUpdateDto = TestHelper.createIssueDtoWithoutId1();
        issueUpdateDto.setReporter(1L);
        issueUpdateDto.setAssignee(2L);
        Issue issueFromUpdateRequestDto = toIssue(issueUpdateDto);
        Issue issueWithAssigneeAndReporter = TestHelper.createIssueWithId1();
        issueWithAssigneeAndReporter.setReporter(user1);
        issueWithAssigneeAndReporter.setAssignee(user2);
        Mockito.when(issueRepository.save(issueFromUpdateRequestDto)).thenReturn(issueWithAssigneeAndReporter);

        Comment comment  = new Comment();
        comment.setAuthor(user1);
        comment.setIssue(issueWithId);
        String dateTime = "2018-04-01 00:10:05";
        Timestamp dateTimeTimestamp = DateTimeUtil.toTimestamp(dateTime);
        comment.setPosted(dateTimeTimestamp);
        final String commentBody = "This is the first comment ever";
        comment.setBody(commentBody);
    }

    @Test
    public void testIssueCreation() throws ResourceConstraintViolationException {
        IssueDto issueDto = TestHelper.createIssueDtoWithoutId1();

        IssueDto issueDtoWithId = issueTrackerService.createOrUpdateIssue(issueDto);

        Issue mockIssue = TestHelper.createIssueWithId1();
        mockIssue.setReporter(TestHelper.createUserWithId1());

        assertEquals(mockIssue.getId(), issueDtoWithId.getId());
        assertEquals(mockIssue.getTitle(), issueDtoWithId.getTitle());
        assertEquals(mockIssue.getDescription(), issueDtoWithId.getDescription());
        assertEquals(mockIssue.getReporter().getId(), issueDtoWithId.getReporter());
    }

    @Test
    public void testIssueGetById()
    {
        IssueDto issueDtoWithId = issueTrackerService.getById(1L);

        Issue mockIssue = TestHelper.createIssueWithId1();
        mockIssue.setReporter(TestHelper.createUserWithId1());

        assertEquals(mockIssue.getId(), issueDtoWithId.getId());
        assertEquals(mockIssue.getTitle(), issueDtoWithId.getTitle());
        assertEquals(mockIssue.getDescription(), issueDtoWithId.getDescription());
        assertEquals(mockIssue.getReporter().getId(), issueDtoWithId.getReporter());
    }

    @Test
    public void testIssueGetByMissingId()
    {
        IssueDto issueDtoWithId = issueTrackerService.getById(100L);

        assertNull(issueDtoWithId);
    }

    @Test
    public void testIssueUpdate() throws ResourceConstraintViolationException {
        IssueDto issueDto = TestHelper.createIssueDtoWithoutId1();
        issueDto.setAssignee(2L);

        IssueDto issueDtoWithId = issueTrackerService.createOrUpdateIssue(issueDto);

        Issue mockIssue = TestHelper.createIssueWithId1();
        mockIssue.setReporter(TestHelper.createUserWithId1());
        mockIssue.setAssignee(TestHelper.createUserWithId2());

        assertEquals(mockIssue.getId(), issueDtoWithId.getId());
        assertEquals(mockIssue.getTitle(), issueDtoWithId.getTitle());
        assertEquals(mockIssue.getDescription(), issueDtoWithId.getDescription());
        assertEquals(mockIssue.getReporter().getId(), issueDtoWithId.getReporter());
        assertEquals(mockIssue.getAssignee().getId(), issueDtoWithId.getAssignee());
    }

    private IssueDto toIssueDto(Issue issue)
    {
        IssueDto issueDto = modelMapper.map(issue, IssueDto.class);
        issueDto.setCreated(DateTimeUtil.toString(issue.getCreated()));
        if(issue.getCompleted() != null)
        {
            issueDto.setCompleted(DateTimeUtil.toString(issue.getCreated()));
        }
        issueDto.setReporter(issue.getReporter().getId());
        if(issue.getAssignee()!=null)
        {
            issueDto.setAssignee(issue.getAssignee().getId());
        }
        return issueDto;
    }

    private Issue toIssue(IssueDto issueDto) throws ResourceConstraintViolationException {
        Issue issue = modelMapper.map(issueDto, Issue.class);
        issue.setCreated(DateTimeUtil.toTimestamp(issueDto.getCreated()));
        if(issueDto.getCompleted() != null)
        {
            issue.setCompleted(DateTimeUtil.toTimestamp(issueDto.getCompleted()));
        }
        return issue;
    }
}
