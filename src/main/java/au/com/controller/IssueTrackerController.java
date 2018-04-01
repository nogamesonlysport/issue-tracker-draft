package au.com.controller;

import au.com.dto.IssueDto;
import au.com.exception.ResourceConstraintViolationException;
import au.com.service.IssueTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by amitsjoshi on 01/04/18.
 */
@RestController
@RequestMapping("/v1")
public class IssueTrackerController
{
    @Autowired
    private IssueTrackerService issueTrackerService;

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



    @ExceptionHandler(ResourceConstraintViolationException.class)
    public ResponseEntity<String> handleResourceConstraintViolationException(ResourceConstraintViolationException exception)
    {
        return new ResponseEntity<String>(exception.getMessage(),exception.getHttpStatus());
    }
}
