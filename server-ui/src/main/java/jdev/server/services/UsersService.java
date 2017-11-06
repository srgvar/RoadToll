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
import java.util.Iterator;
import java.util.Set;

@Service("usersService")
@Transactional
public class UsersService {
    public static final int ALL_USERS = 0;
    public static final int CLIENTS = 1;
    public static final int MANAGERS = 2;
    private static final ThreadLocal<Logger> LOG = ThreadLocal.withInitial(() -> LoggerFactory.getLogger(UsersService.class));
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

    public void toClients(int id){
        User user = usersRepository.findFirstById(id);
        Set<UserRole> roles = rolesRepository.findAllByUser_id(id);
        for(UserRole role : roles){
            if(role.getRole().equals("MANAGER"))
                rolesRepository.delete(role);
        }
    }

    public void toManagers(int id){
        User user = usersRepository.findFirstById(id);
        rolesRepository.save(new UserRole(user,"MANAGER"));
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

    @Transactional
    public ArrayList <User> getUsers(int clientsType){
        ArrayList<User> all, clients, managers;
        clients = new ArrayList<>();
        managers = new ArrayList<>();
        boolean isManager;
        all = (ArrayList<User>) usersRepository.findAllByUsernameNotNullOrderByUsername();
            if (clientsType == 0)
                return all;

        for(User client: all)
        {
            isManager = false;
            Set<UserRole> clientRoles = rolesRepository.findAllByUser_id(client.getId());
            for( UserRole role: clientRoles){
                if(role.getRole().equals("MANAGER") ||
                   role.getRole().equals("ROOT")){
                    isManager = true;
                }
            }
            if (!isManager)
                clients.add(client);
            if (isManager)
                managers.add(client);
        }
     if(clientsType == CLIENTS)
         return clients;
        else
            return managers;
    }
}
