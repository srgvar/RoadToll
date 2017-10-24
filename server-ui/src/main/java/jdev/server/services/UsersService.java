package jdev.server.services;


import jdev.users.User;
import jdev.users.UserRole;
import jdev.users.repo.RolesRepository;
import jdev.users.repo.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Set;

@Service("usersService")
@Transactional
public class UsersService {
    private static final Logger log = LoggerFactory.getLogger(UsersService.class);
    private UsersRepository usersRepository;
    private RolesRepository rolesRepository;

    UsersService(@Autowired UsersRepository usersRepository,
                 @Autowired RolesRepository rolesRepository){
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }

    public void save(User user) {
        if(user.isNew()) {
            User savedUser = usersRepository.save(user);
            rolesRepository.save(new UserRole(savedUser, "CLIENT"));
        } else {
            usersRepository.save(user);
        }
    }

    public void delete(Integer user_id) {
        User fUser = usersRepository.findFirstById(user_id);
        Set<UserRole> rolesFUser = rolesRepository.findAllByUser_id(user_id);
        rolesRepository.delete(rolesFUser);
        usersRepository.delete(fUser);
    }
    @Transactional(readOnly = true)
    public User getById(Integer id) {
        return usersRepository.findFirstById(id);
    }
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return usersRepository.findOneByUsername(username);
    }
    @Transactional(readOnly = true)
    public Iterable<User> getAll() {
        return usersRepository.findAllByUsernameNotNullOrderByUsername();
    }
}
