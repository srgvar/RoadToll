package jdev.users.repo;

import jdev.users.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by srgva on 16.10.2017.
 */
//@NoRepositoryBean
@Repository
public interface RolesRepository extends CrudRepository <UserRole, Long> {

    //List<UserRole> findAllByUser_id(Long user_id);
    Set<UserRole> findAllByUser_id(Long user_id);

}
