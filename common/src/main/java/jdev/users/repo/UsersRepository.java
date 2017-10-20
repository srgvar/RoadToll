package jdev.users.repo;

import jdev.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UsersRepository extends CrudRepository <User, Long> {
    //User findOneByUsername(String username);

    User findOneByUsername (String username);

}
