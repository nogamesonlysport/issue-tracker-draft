package au.com.domain;


import javax.persistence.*;
import java.util.List;


/**
 * Created by amitsjoshi on 31/03/18.
 */
@Entity
@Table(name = "USER", schema = "ISSUE_TRACKER")
public class User
{
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "username")
    private String userName;

    @OneToMany(mappedBy = "reporter")
    private List<Issue> reportedIssues;

    @OneToMany(mappedBy = "assignee")
    private List<Issue> assignedIssues;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Issue> getReportedIssues() {
        return reportedIssues;
    }

    public void setReportedIssues(List<Issue> reportedIssues) {
        this.reportedIssues = reportedIssues;
    }

    public List<Issue> getAssignedIssues() {
        return assignedIssues;
    }

    public void setAssignedIssues(List<Issue> assignedIssues) {
        this.assignedIssues = assignedIssues;
    }
}
