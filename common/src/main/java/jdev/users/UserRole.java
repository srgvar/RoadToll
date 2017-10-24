package jdev.users;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by srgva on 16.10.2017.
 */
@Entity
@Table(name = "user_roles")
public class UserRole implements Serializable {

    public static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="role_id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String role;
    public UserRole(){}

    public UserRole(User user) {
        this.user = user;
    }

    public UserRole(User user, String role){
        this.user = user;
        this.role = role;
    }


    public String getRole () {
        return role;
    }

    public void setRole (String role) {
        this.role = role;
    }

    public Integer getRoleId () {
        return id;
    }

    public void setRoleId (Integer id) {
        this.id = id;
    }

    public User getUser () {
        return user;
    }

    public void setUser (User user) {
        this.user = user;
    }
}
