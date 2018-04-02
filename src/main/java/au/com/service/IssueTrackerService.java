package au.com.service;

import au.com.domain.Issue;
import au.com.dto.CommentDto;
import au.com.dto.IssueDto;
import au.com.exception.ResourceConstraintViolationException;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by amitsjoshi on 31/03/18.
 */
public interface IssueTrackerService
{
    IssueDto createOrUpdateIssue(IssueDto issueDto) throws ResourceConstraintViolationException;

    IssueDto getById(Long id);

    void deleteIssue(Long id);

    public List<IssueDto> filterByAssigneeReporterStatus(final Long assigneeId, final Long reporterId,
                                                         final String status, final Pageable pageable);

    public List<IssueDto> filterByDateRange(final String startDate, final String endDate, final Pageable pageable)
            throws ResourceConstraintViolationException;

    public IssueDto toIssueDto(Issue issue);

    public Issue toIssue(IssueDto issueDto) throws ResourceConstraintViolationException;

    public CommentDto createComment(CommentDto commentDto) throws ResourceConstraintViolationException;
}
