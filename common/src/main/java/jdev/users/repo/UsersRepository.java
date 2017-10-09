package jdev.users.repo;

import jdev.users.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository <User, Long> {
}
