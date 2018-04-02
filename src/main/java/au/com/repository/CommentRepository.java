package au.com.repository;

import au.com.domain.Comment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by amitsjoshi on 02/04/18.
 */
public interface CommentRepository extends CrudRepository<Comment, Long>
{
}
