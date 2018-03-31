package au.com.repository;

import au.com.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by amitsjoshi on 31/03/18.
 */
public interface UserRepository extends CrudRepository<User, Long>
{
}
