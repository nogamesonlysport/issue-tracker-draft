package au.com.service;

import au.com.domain.Issue;
import au.com.domain.User;
import au.com.dto.IssueDto;
import au.com.helper.TestHelper;
import au.com.repository.IssueRepository;
import au.com.repository.UserRepository;
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


    @Before
    public void setUp() throws ParseException {
        User user1 = TestHelper.createUserWithId1();
        Mockito.when(userRepository.findOne(1L)).thenReturn(user1);

        User user2 = TestHelper.createUserWithId2();
        Mockito.when(userRepository.findOne(2L)).thenReturn(user2);

        IssueDto issueDto = TestHelper.getIssueDtoWithoutId1();
        Issue issueFromRequestDto = toIssue(issueDto);
        issueFromRequestDto.setReporter(user1);
        Issue issueWithId = TestHelper.createIssueWithId1();
        issueWithId.setReporter(user1);

        Mockito.when(issueRepository.save(issueFromRequestDto)).thenReturn(issueWithId);

        Mockito.when(issueRepository.findOne(1L)).thenReturn(issueWithId);

        IssueDto issueUpdateDto = TestHelper.getIssueDtoWithoutId1();
        issueUpdateDto.setReporter(1L);
        issueUpdateDto.setAssignee(2L);
        Issue issueFromUpdateRequestDto = toIssue(issueUpdateDto);
        Issue issueWithAssigneeAndReporter = TestHelper.createIssueWithId1();
        issueWithAssigneeAndReporter.setReporter(user1);
        issueWithAssigneeAndReporter.setAssignee(user2);
        Mockito.when(issueRepository.save(issueFromUpdateRequestDto)).thenReturn(issueWithAssigneeAndReporter);
    }

    @Test
    public void testIssueCreation()
    {
        IssueDto issueDto = TestHelper.getIssueDtoWithoutId1();

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
    public void testIssueGetByAssignee()
    {
        IssueDto issueDto = TestHelper.getIssueDtoWithoutId1();
        issueDto.setReporter(1L);
        issueDto.setAssignee(2L);

        IssueDto issueDtoWithId = issueTrackerService.filterByAssigneeReporterStatus(issueDto);

        Issue mockIssue = TestHelper.createIssueWithId1();
        mockIssue.setReporter(TestHelper.createUserWithId1());
        mockIssue.setAssignee(TestHelper.createUserWithId2());

        assertEquals(mockIssue.getId(), issueDtoWithId.getId());
        assertEquals(mockIssue.getTitle(), issueDtoWithId.getTitle());
        assertEquals(mockIssue.getDescription(), issueDtoWithId.getDescription());
        assertEquals(mockIssue.getReporter().getId(), issueDtoWithId.getReporter());
        assertEquals(mockIssue.getAssignee().getId(), issueDtoWithId.getAssignee());
    }

    @Test
    public void testIssueUpdate()
    {
        IssueDto issueDto = TestHelper.getIssueDtoWithoutId1();
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
        issueDto.setCreated(IssueDto.toString(issue.getCreated()));
        if(issue.getCompleted() != null)
        {
            issueDto.setCompleted(IssueDto.toString(issue.getCreated()));
        }
        issueDto.setReporter(issue.getReporter().getId());
        if(issue.getAssignee()!=null)
        {
            issueDto.setAssignee(issue.getAssignee().getId());
        }
        return issueDto;
    }

    private Issue toIssue(IssueDto issueDto) {
        Issue issue = modelMapper.map(issueDto, Issue.class);
        issue.setCreated(IssueDto.toTimestamp(issueDto.getCreated()));
        if(issueDto.getCompleted() != null)
        {
            issue.setCompleted(IssueDto.toTimestamp(issueDto.getCompleted()));
        }
        return issue;
    }
}
