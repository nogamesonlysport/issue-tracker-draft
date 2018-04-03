package au.com.controller;

import au.com.dto.CommentDto;
import au.com.dto.IssueDto;
import au.com.exception.ExceptionDetails;
import au.com.exception.ResourceConstraintViolationException;
import au.com.helper.TestHelper;
import au.com.service.IssueTrackerService;
import junit.framework.TestCase;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by amitsjoshi on 01/04/18.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(IssueTrackerController.class)
public class IssueTrackerControllerTest extends TestCase
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private IssueTrackerService issueTrackerService;

    @Test
    public void testGetIssueById_success() throws Exception
    {
        IssueDto issueDto = TestHelper.createIssueDtoWithId1();

        given(issueTrackerService.getById(1L)).willReturn(issueDto);

        (mvc.perform(MockMvcRequestBuilders.get("/v1/issue/1")))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.id", Matchers.is(issueDto.getId().intValue())))
                .andExpect(jsonPath("$.title", Matchers.is(issueDto.getTitle())))
                .andExpect(jsonPath("$.description", Matchers.is(issueDto.getDescription())))
                .andExpect(jsonPath("$.reporter", Matchers.is(issueDto.getReporter().intValue())))
                .andExpect(jsonPath("$.created", Matchers.is(issueDto.getCreated())));
    }

    @Test
    public void testGetIssueById_failure() throws Exception
    {
        IssueDto issueDto = TestHelper.createIssueDtoWithId1();

        given(issueTrackerService.getById(100L)).willReturn(null);

        (mvc.perform(MockMvcRequestBuilders.get("/v1/issue/1")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateIssue_success() throws Exception
    {
        IssueDto issueDtoWithoutId = TestHelper.createIssueDtoWithoutId1();
        IssueDto issueDto = TestHelper.createIssueDtoWithId1();

        given(issueTrackerService.createOrUpdateIssue(issueDtoWithoutId)).willReturn(issueDto);

        (mvc.perform(MockMvcRequestBuilders.post("/v1/issue")
                .content(TestHelper.toJson(issueDtoWithoutId))
                .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(issueDto.getId().intValue())))
                .andExpect(jsonPath("$.title", Matchers.is(issueDto.getTitle())))
                .andExpect(jsonPath("$.description", Matchers.is(issueDto.getDescription())))
                .andExpect(jsonPath("$.reporter", Matchers.is(issueDto.getReporter().intValue())))
                .andExpect(jsonPath("$.created", Matchers.is(issueDto.getCreated())));
    }

    @Test
    public void testCreateIssue_Failure_Invalid_Reporter() throws Exception
    {
        IssueDto issueDtoWithoutId = TestHelper.createIssueDtoWithoutId1();
        issueDtoWithoutId.setReporter(100L);

        given(issueTrackerService.createOrUpdateIssue(issueDtoWithoutId))
                .willThrow(new ResourceConstraintViolationException(ExceptionDetails.INVALID_REPORTER));

        (mvc.perform(MockMvcRequestBuilders.post("/v1/issue")
                .content(TestHelper.toJson(issueDtoWithoutId))
                .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateIssue_Failure_Invalid_Assignee() throws Exception
    {
        IssueDto issueDtoWithoutId = TestHelper.createIssueDtoWithoutId1();
        issueDtoWithoutId.setAssignee(100L);

        given(issueTrackerService.createOrUpdateIssue(issueDtoWithoutId))
                .willThrow(new ResourceConstraintViolationException(ExceptionDetails.INVALID_ASSIGNEE));

        (mvc.perform(MockMvcRequestBuilders.post("/v1/issue")
                .content(TestHelper.toJson(issueDtoWithoutId))
                .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateComment_success() throws Exception
    {
        CommentDto commentDto = TestHelper.createComment();
        CommentDto commentDtoCreated = TestHelper.createCommentWithId1();

        given(issueTrackerService.createComment(commentDto)).willReturn(commentDtoCreated);

        (mvc.perform(MockMvcRequestBuilders.post("/v1/comment")
                .content(TestHelper.toJson(commentDto))
                .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(commentDtoCreated.getId().intValue())))
                .andExpect(jsonPath("$.author", Matchers.is(commentDtoCreated.getAuthor().intValue())))
                .andExpect(jsonPath("$.issue", Matchers.is(commentDtoCreated.getIssue().intValue())))
                .andExpect(jsonPath("$.posted", Matchers.is(commentDtoCreated.getPosted())))
                .andExpect(jsonPath("$.body", Matchers.is(commentDtoCreated.getBody())));
    }

    @Test
    public void testCreateComment_failure() throws Exception
    {
        CommentDto commentDto = TestHelper.createComment();
        commentDto.setIssue(100L);

        given(issueTrackerService.createComment(commentDto))
                .willThrow(new ResourceConstraintViolationException(ExceptionDetails.INVALID_ASSIGNEE_AND_ISSUE));

        (mvc.perform(MockMvcRequestBuilders.post("/v1/comment")
                .content(TestHelper.toJson(commentDto))
                .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(status().isBadRequest());
    }


    @Configuration
    @ComponentScan(basePackageClasses = { au.com.controller.IssueTrackerController.class })
    public static class TestConf {}
}
