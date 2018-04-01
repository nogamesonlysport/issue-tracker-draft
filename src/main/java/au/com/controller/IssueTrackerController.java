package au.com.controller;

import au.com.dto.IssueDto;
import au.com.service.IssueTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @ResponseBody
    public IssueDto getIssueById(@PathVariable(value="id") long id)
    {
        return issueTrackerService.getById(id);
    }

    @RequestMapping(value = "/issue", method = RequestMethod.POST)
    @ResponseBody
    public IssueDto createOrUpdateIssue(@RequestBody IssueDto issueDto)
    {
        return issueTrackerService.createOrUpdateIssue(issueDto);
    }
}
