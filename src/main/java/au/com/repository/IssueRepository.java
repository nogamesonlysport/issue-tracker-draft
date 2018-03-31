package au.com.repository;

import au.com.domain.Issue;
import au.com.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by amitsjoshi on 31/03/18.
 */
@Repository
public interface IssueRepository extends CrudRepository<Issue, Long>
{
    public Issue findById(Long id);

    List<Issue> findByAssignee(User assignee);

    List<Issue> findByReporter(User reporter);

    List<Issue> findByStatus(String status);

    List<Issue> findByAssigneeAndReporterAndStatus(User assignee, User reporter2, String open);

    List<Issue> findAllByOrderByCreatedAsc();

    List<Issue> findAllByOrderByCreatedDesc(Pageable pageable);

    List<Issue> findByCreatedBetween(Timestamp startDate, Timestamp endDate);

}
