package jdev.users.repo;

import jdev.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository <User, Integer> {
    //User findOneByUsername(String username);

    User findOneByUsername (String username);
    User findFirstById(Integer id);
    List<User> findAllByUsernameNotNullOrderByUsername();

}
