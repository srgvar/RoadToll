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
    private Integer id;
    @Column(name = "username", nullable = false, length = 20)
    private String username;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Column(name = "fullname", length = 60)
    private String fullname;
    @Column(name = "password", nullable = false, length = 60)
    private String password;
    @Column(name = "enabled")
    private boolean enabled;

    public User(){}

    public User(String username){
        this.username = username;
        this.fullname = username;
    }

    public User(String username, String fullname){
        this.username = username;
        this.fullname = fullname;
    }

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

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public boolean isEnabled () {
        return enabled;
    }

    public void setEnabled (boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isNew(){
        return (this.id == null);
    }
}



