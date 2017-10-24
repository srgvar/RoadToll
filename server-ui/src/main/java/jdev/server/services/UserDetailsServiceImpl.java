package jdev.server.services;

/*
 * Created by srgva on 16.10.2017.
 */
import jdev.users.User;
import jdev.users.UserRole;
import jdev.users.repo.RolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import jdev.users.repo.UsersRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private UsersRepository usersRepository;

    private RolesRepository rolesRepository;

    public UserDetailsServiceImpl(@Autowired UsersRepository usersRepository, @Autowired RolesRepository rolesRepository){
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findOneByUsername(username);
            if (user == null) throw new UsernameNotFoundException("User with name" + username + " not found");
        if(user.isEnabled()) {
            Set<UserRole> roles = rolesRepository.findAllByUser_id(user.getId());
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), buildUserAuthority(roles));
        }else
            return null;
    }

    private Collection<? extends GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
        Set<GrantedAuthority> setAuthorities = new HashSet<>();

        for (UserRole userRole : userRoles) {
            setAuthorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRole()));
        }
      return setAuthorities;
     }
}