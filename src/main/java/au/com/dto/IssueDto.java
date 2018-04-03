package au.com.dto;

import java.util.List;

/**
 * Created by amitsjoshi on 01/04/18.
 */
public class IssueDto
{
    private Long id;

    private String title;

    private String description;

    private String status;

    private Long reporter;

    private Long assignee;

    private String created;

    private String completed;

    private List<CommentDto> comments;

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

    public Long getReporter() {
        return reporter;
    }

    public void setReporter(Long reporter) {
        this.reporter = reporter;
    }

    public Long getAssignee() {
        return assignee;
    }

    public void setAssignee(Long assignee) {
        this.assignee = assignee;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueDto issueDto = (IssueDto) o;

        if (id != null ? !id.equals(issueDto.id) : issueDto.id != null) return false;
        if (!title.equals(issueDto.title)) return false;
        if (!description.equals(issueDto.description)) return false;
        if (!reporter.equals(issueDto.reporter)) return false;
        return created.equals(issueDto.created);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + reporter.hashCode();
        result = 31 * result + created.hashCode();
        return result;
    }
}
