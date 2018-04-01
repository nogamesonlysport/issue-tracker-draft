package au.com.service;

import au.com.domain.Issue;
import au.com.domain.User;
import au.com.dto.IssueDto;
import au.com.exception.ResourceConstraintViolationException;
import au.com.repository.IssueRepository;
import au.com.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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

    @PostConstruct
    public void init()
    {
        modelMapper.createTypeMap(Issue.class, IssueDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getReporter().getId(), IssueDto::setReporter);
                    mapper.map(src -> src.getAssignee().getId(), IssueDto::setAssignee);
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
        User assignee = userRepository.findOne(assigneeId);
        User reporter = userRepository.findOne(reporterId);
        if(assignee != null && reporter != null && status != null)
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

    @Override
    public List<IssueDto> getAllSortedByCreatedAsc(final Pageable pageable) {
        return toIssueDto(issueRepository.findAllByOrderByCreatedAsc(pageable));
    }

    @Override
    public List<IssueDto> getAllSortedByCreatedDesc(final Pageable pageable) {
        return toIssueDto(issueRepository.findAllByOrderByCreatedAsc(pageable));
    }

    public List<IssueDto> toIssueDto(List<Issue> issueLst)
    {
        List<IssueDto> issueDtoLst = new ArrayList<>();
        for(Issue issue : issueLst)
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
            issueDtoLst.add(issueDto);
        }

        return issueDtoLst;
    }

    public IssueDto toIssueDto(Issue issue)
    {
        IssueDto issueDto = null;
        if(issue != null)
        {
            issueDto = modelMapper.map(issue, IssueDto.class);
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
        }
        return issueDto;
    }

    public Issue toIssue(IssueDto issueDto)
    {
        Issue issue = modelMapper.map(issueDto, Issue.class);
        issue.setCreated(IssueDto.toTimestamp(issueDto.getCreated()));
        if(issueDto.getCompleted() != null)
        {
            issue.setCompleted(IssueDto.toTimestamp(issueDto.getCompleted()));
        }
        return issue;
    }
}
