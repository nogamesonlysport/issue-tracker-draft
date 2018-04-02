package au.com.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by amitsjoshi on 31/03/18.
 */
@Entity
@Table(name = "COMMENT", schema = "ISSUE_TRACKER")
public class Comment
{
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "AUTHOR")
    private User author;

    @ManyToOne
    @JoinColumn(name = "ISSUE")
    private Issue issue;

    private Timestamp posted;

    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Timestamp getPosted() {
        return posted;
    }

    public void setPosted(Timestamp posted) {
        this.posted = posted;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (!id.equals(comment.id)) return false;
        if (!author.equals(comment.author)) return false;
        if (!issue.equals(comment.issue)) return false;
        if (posted != null ? !posted.equals(comment.posted) : comment.posted != null) return false;
        return body != null ? body.equals(comment.body) : comment.body == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + issue.hashCode();
        result = 31 * result + (posted != null ? posted.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }
}
