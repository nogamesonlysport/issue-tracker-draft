package au.com.repository;

import au.com.domain.Issue;
import au.com.domain.User;
import org.springframework.data.domain.Page;
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
    List<Issue> findByAssignee(User assignee, Pageable pageable);

    List<Issue> findByReporter(User reporter, Pageable pageable);

    List<Issue> findByStatus(String status, Pageable pageable);

    List<Issue> findByAssigneeAndReporterAndStatus(User assignee, User reporter2, String open, Pageable pageable);

    List<Issue> findAllByOrderByCreatedAsc(Pageable pageable);

    List<Issue> findAllByOrderByCreatedDesc(Pageable pageable);

    List<Issue> findByCreatedBetween(Timestamp startDate, Timestamp endDate, Pageable pageable);

    List<Issue> findByAssigneeAndReporter(User assignee, User reporter, Pageable pageable);

    List<Issue> findByAssigneeAndStatus(User assignee, String status, Pageable pageable);

    List<Issue> findByReporterAndStatus(User reporter, String status, Pageable pageable);

    Page<Issue> findAll(Pageable pageable);
}
