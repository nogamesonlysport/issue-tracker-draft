package au.com.domain;

import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by amitsjoshi on 31/03/18.
 */
@Entity
@Table(name = "ISSUE", schema = "ISSUE_TRACKER")
public class Issue
{
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private String status;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "REPORTER")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "ASSIGNEE")
    private User assignee;

    private Timestamp created;

    private Timestamp completed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getCompleted() {
        return completed;
    }

    public void setCompleted(Timestamp completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        if (id != null ? !id.equals(issue.id) : issue.id != null) return false;
        if (!title.equals(issue.title)) return false;
        return description.equals(issue.description);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
