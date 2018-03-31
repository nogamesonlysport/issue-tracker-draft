package au.com.domain;

import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;

/**
 * Created by amitsjoshi on 31/03/18.
 */
@Entity
@Table(name = "COMMENT", schema = "ISSUE_TRACKER")
public class Comment
{
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @CreatedBy
    @JoinColumn(name = "AUTHOR")
    private User author;

    @ManyToOne
    @CreatedBy
    @JoinColumn(name = "ISSUE")
    private Issue issue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}
