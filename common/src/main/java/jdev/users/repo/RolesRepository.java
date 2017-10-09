package jdev.users.repo;

import jdev.users.Role;
import org.springframework.data.repository.CrudRepository;

public interface RolesRepository extends CrudRepository <Role, Integer> {
}
