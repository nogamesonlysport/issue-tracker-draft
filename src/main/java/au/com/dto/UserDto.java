package au.com.dto;

import java.util.List;

/**
 * Created by amitsjoshi on 01/04/18.
 */
public class UserDto
{
    private Long id;

    private String userName;

    private List<IssueDto> reportedIssues;

    private List<IssueDto> assignedIssues;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<IssueDto> getReportedIssues() {
        return reportedIssues;
    }

    public void setReportedIssues(List<IssueDto> reportedIssues) {
        this.reportedIssues = reportedIssues;
    }

    public List<IssueDto> getAssignedIssues() {
        return assignedIssues;
    }

    public void setAssignedIssues(List<IssueDto> assignedIssues) {
        this.assignedIssues = assignedIssues;
    }
}
