package au.com.controller;

import au.com.service.IssueTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by amitsjoshi on 01/04/18.
 */
@RestController
@RequestMapping("/v1")
public class IssueTrackerController
{
    @Autowired
    private IssueTrackerService issueTrackerService;
}
