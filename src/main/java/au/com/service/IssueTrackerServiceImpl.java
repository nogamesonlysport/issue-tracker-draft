package au.com.service;

import au.com.domain.Comment;
import au.com.domain.Issue;
import au.com.domain.User;
import au.com.dto.CommentDto;
import au.com.dto.IssueDto;
import au.com.exception.ResourceConstraintViolationException;
import au.com.repository.CommentRepository;
import au.com.repository.IssueRepository;
import au.com.repository.UserRepository;
import au.com.util.DateTimeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by amitsjoshi on 31/03/18.
 */
@Service
public class IssueTrackerServiceImpl implements IssueTrackerService
{
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    UserRepository userRepository;

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    CommentRepository commentRepository;

    @PostConstruct
    public void init()
    {
        modelMapper.createTypeMap(Issue.class, IssueDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getReporter().getId(), IssueDto::setReporter);
                    mapper.map(src -> src.getAssignee().getId(), IssueDto::setAssignee);
                });
        modelMapper.createTypeMap(Comment.class, CommentDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getAuthor().getId(), CommentDto::setAuthor);
                    mapper.map(src -> src.getIssue().getId(), CommentDto::setIssue);
                });
    }

    @Override
    public IssueDto createOrUpdateIssue(IssueDto issueDto) throws ResourceConstraintViolationException {
        Issue issue = toIssue(issueDto);

        User reporter = userRepository.findOne(issueDto.getReporter());
        if(reporter == null)
        {
            throw new ResourceConstraintViolationException(HttpStatus.BAD_REQUEST, "An issue has to be reported by a valid user");
        }
        issue.setReporter(reporter);

        if(issueDto.getAssignee() != null)
        {
            User assignee = userRepository.findOne(issueDto.getAssignee());
            if(reporter == null)
            {
                throw new ResourceConstraintViolationException(HttpStatus.BAD_REQUEST, "An issue has to be assigned to a valid user");
            }
            issue.setReporter(assignee);
        }
        Issue persistedIssue = issueRepository.save(issue);

        return toIssueDto(persistedIssue);
    }

    @Override
    public IssueDto getById(Long id)
    {
        return toIssueDto(issueRepository.findOne(id));
    }

    @Override
    public void deleteIssue(Long id) {
        issueRepository.delete(id);
    }

    @Override
    public List<IssueDto> filterByAssigneeReporterStatus(final Long assigneeId, final Long reporterId,
                                                         final String status, final Pageable pageable)
    {
        List<Issue> result = null;

        User assignee = null;
        if(assigneeId!=null)
        {
            assignee = userRepository.findOne(assigneeId);
        }

        User reporter = null;
        if(reporterId != null)
        {
            reporter = userRepository.findOne(reporterId);
        }

        if(assignee == null && reporter == null && status == null)
        {
            result = getAllIssuesWithPagination(pageable);
        }
        else if(assignee != null && reporter != null && status != null)
        {
            result = issueRepository.findByAssigneeAndReporterAndStatus(assignee, reporter, status, pageable);
        }
        else if(assignee != null && reporter != null)
        {
            result = issueRepository.findByAssigneeAndReporter(assignee, reporter, pageable);
        }
        else if(assignee != null && status != null)
        {
            result = issueRepository.findByAssigneeAndStatus(assignee, status, pageable);
        }
        else if(reporter != null && status != null)
        {
            result = issueRepository.findByReporterAndStatus(reporter, status, pageable);
        }
        else if(assignee != null)
        {
            result = issueRepository.findByAssignee(assignee, pageable);
        }
        else if(reporter != null)
        {
            result = issueRepository.findByReporter(reporter, pageable);
        }
        else if(status != null)
        {
            result = issueRepository.findByStatus(status, pageable);
        }
        return toIssueDto(result);
    }

    private List<Issue> getAllIssuesWithPagination(Pageable pageable) {
        List<Issue> result;Page<Issue> resultPages = issueRepository.findAll(pageable);
        Iterator iter = resultPages.iterator();
        result = new ArrayList<>();
        while(iter.hasNext())
        {
            result.add((Issue) iter.next());
        }
        return result;
    }

    @Override
    public List<IssueDto> filterByDateRange(String startDate, String endDate, Pageable pageable) throws ResourceConstraintViolationException {
        Timestamp startTime = DateTimeUtil.toTimestamp(startDate);
        Timestamp endTimeStamp = DateTimeUtil.toTimestamp(endDate);

        return toIssueDto(issueRepository.findByCreatedBetween(startTime, endTimeStamp, pageable));
    }

    @Override
    public CommentDto createComment(CommentDto commentDto) throws ResourceConstraintViolationException {
        Comment comment = toComment(commentDto);
        if(commentDto.getAuthor() == null || commentDto.getIssue() == null || commentDto.getBody() == null)
        {
            throw new ResourceConstraintViolationException(HttpStatus.BAD_REQUEST, "Mandatory information missing");
        }

        User author = userRepository.findOne(commentDto.getAuthor());
        Issue issue = issueRepository.findOne(commentDto.getIssue());

        if(author == null || issue == null)
        {
            throw new ResourceConstraintViolationException(HttpStatus.BAD_REQUEST, "An issue has to be assigned to a valid user and issue");
        }

        comment.setAuthor(author);
        comment.setIssue(issue);

        return toCommentDto(commentRepository.save(comment));
    }

    public List<IssueDto> toIssueDto(List<Issue> issueLst)
    {
        List<IssueDto> issueDtoLst = new ArrayList<>();
        for(Issue issue : issueLst)
        {
            issueDtoLst.add(toIssueDto(issue));
        }

        return issueDtoLst;
    }

    public List<CommentDto> toCommentDto(List<Comment> commentLst)
    {
        List<CommentDto> commentDtoLst = new ArrayList<>();
        for(Comment comment : commentLst)
        {
            commentDtoLst.add(toCommentDto(comment));
        }

        return commentDtoLst;
    }

    public IssueDto toIssueDto(Issue issue)
    {
        IssueDto issueDto = null;
        if(issue != null)
        {
            issueDto = modelMapper.map(issue, IssueDto.class);
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
        }
        return issueDto;
    }

    public Issue toIssue(IssueDto issueDto) throws ResourceConstraintViolationException {
        Issue issue = modelMapper.map(issueDto, Issue.class);
        issue.setCreated(DateTimeUtil.toTimestamp(issueDto.getCreated()));
        if(issueDto.getCompleted() != null)
        {
            issue.setCompleted(DateTimeUtil.toTimestamp(issueDto.getCompleted()));
        }
        return issue;
    }

    public CommentDto toCommentDto(Comment comment)
    {
        CommentDto commentDto = null;
        if(comment != null)
        {
            commentDto = modelMapper.map(comment, CommentDto.class);
            commentDto.setPosted(DateTimeUtil.toString(comment.getPosted()));
            commentDto.setAuthor(comment.getAuthor().getId());
            commentDto.setIssue(comment.getIssue().getId());
        }
        return commentDto;
    }

    public Comment toComment(CommentDto commentDto) throws ResourceConstraintViolationException {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPosted(DateTimeUtil.toTimestamp(commentDto.getPosted()));
        return comment;
    }
}
