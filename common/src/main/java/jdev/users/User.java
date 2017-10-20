package jdev.users;

//import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "users")
public class User implements Serializable {

    public static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "username", nullable = false, length = 40)
    private String username;
    @Column(name = "password", nullable = false, length = 40)
    private String password;
    @Column(name = "enabled")
    private boolean enabled;




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public boolean isEnabled () {
        return enabled;
    }

    public void setEnabled (boolean enabled) {
        this.enabled = enabled;
    }
}



