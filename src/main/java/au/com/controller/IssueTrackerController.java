package au.com.controller;

import au.com.dto.CommentDto;
import au.com.dto.IssueDto;
import au.com.exception.ResourceConstraintViolationException;
import au.com.service.IssueTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by amitsjoshi on 01/04/18.
 */
@RestController
@RequestMapping("/v1")
public class IssueTrackerController
{
    @Autowired
    private IssueTrackerService issueTrackerService;

    // Part 1
    @RequestMapping(value = "/issue/{id}", method = RequestMethod.GET)
    public ResponseEntity<IssueDto> getIssueById(@PathVariable(value="id") long id)
    {
        IssueDto result = issueTrackerService.getById(id);
        HttpStatus httpStatus = result != null ? HttpStatus.FOUND : HttpStatus.NOT_FOUND;
        return new ResponseEntity<IssueDto>(result, httpStatus);
    }

    @RequestMapping(value = "/issue", method = RequestMethod.POST)
    public ResponseEntity<IssueDto> createOrUpdateIssue(@RequestBody IssueDto issueDto) throws ResourceConstraintViolationException {
        IssueDto result = issueTrackerService.createOrUpdateIssue(issueDto);
        return  new ResponseEntity<IssueDto>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/issue/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteIssue(@PathVariable(value="id") long id) {
        issueTrackerService.deleteIssue(id);
        return  new ResponseEntity<String>(HttpStatus.OK);
    }

    //  Part 2
    @RequestMapping(value = "/issues", method = RequestMethod.GET)
    public ResponseEntity<Object> getSortedIssueByCreatedFilteredByProperties(
            @RequestParam (value = "assignee", required = false) Long assignee,
            @RequestParam (value = "reporter", required = false) Long reporter,
            @RequestParam (value = "status", required = false) String status,
            @RequestParam (value = "sort", required = false) String sort,
            @RequestParam (value = "pageNo", required = false) Integer pageNo,
            @RequestParam (value = "pageCount", required = false) Integer pageCount)
    {
        Pageable pageable = null;
        pageNo = pageNo != null ? pageNo : 0;
        pageCount = pageCount != null ? pageCount : 50;
        if(sort != null)
        {
            if(sort.equalsIgnoreCase("ASC"))
            {
                pageable = new PageRequest(pageNo, pageCount, Sort.Direction.ASC, "created");
            }
            else
            {
                pageable = new PageRequest(pageNo, pageCount, Sort.Direction.DESC, "created");
            }
        }
        List<IssueDto> result = issueTrackerService.filterByAssigneeReporterStatus(assignee, reporter, status, pageable);
        HttpStatus httpStatus = !result.isEmpty() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Object>(result, httpStatus);
    }

    @RequestMapping(value = "/issues/{startDate}/{endDate}", method = RequestMethod.GET)
    public ResponseEntity<Object> getIssueByDateRange(
            @PathVariable(value="startDate") String startDate,
            @PathVariable(value="endDate") String endDate,
            @RequestParam (value = "startPage", required = false) Integer pageNo,
            @RequestParam (value = "pageCount", required = false) Integer pageCount) throws ResourceConstraintViolationException {
        Pageable pageable = null;
        if(pageNo != null && pageCount != null)
        {
            pageable = new PageRequest(pageNo, pageCount, null);
        }
        List<IssueDto> result = issueTrackerService.filterByDateRange(startDate, endDate, pageable);
        HttpStatus httpStatus = !result.isEmpty() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Object>(result, httpStatus);
    }

    //Part 3
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResponseEntity<CommentDto> createOrUpdateIssue(@RequestBody CommentDto commentDto) throws ResourceConstraintViolationException {
        CommentDto result = issueTrackerService.createComment(commentDto);
        return  new ResponseEntity<CommentDto>(result, HttpStatus.OK);

    }

    @ExceptionHandler(ResourceConstraintViolationException.class)
    public ResponseEntity<String> handleResourceConstraintViolationException(ResourceConstraintViolationException exception)
    {
        return new ResponseEntity<String>(exception.getMessage(),exception.getHttpStatus());
    }

    /*
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException exception)
    {

    }*/
}
