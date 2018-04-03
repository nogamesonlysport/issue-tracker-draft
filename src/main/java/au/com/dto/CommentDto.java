package au.com.dto;

/**
 * Created by amitsjoshi on 02/04/18.
 */
public class CommentDto
{
    private Long id;

    private Long author;

    private Long issue;

    private String posted;

    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public Long getIssue() {
        return issue;
    }

    public void setIssue(Long issue) {
        this.issue = issue;
    }

    public String getPosted() {
        return posted;
    }

    public void setPosted(String posted) {
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

        CommentDto that = (CommentDto) o;

        if (!author.equals(that.author)) return false;
        if (!issue.equals(that.issue)) return false;
        if (!posted.equals(that.posted)) return false;
        return body.equals(that.body);

    }

    @Override
    public int hashCode() {
        int result = author.hashCode();
        result = 31 * result + issue.hashCode();
        result = 31 * result + posted.hashCode();
        result = 31 * result + body.hashCode();
        return result;
    }
}
