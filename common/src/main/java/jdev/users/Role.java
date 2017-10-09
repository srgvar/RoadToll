package jdev.users;


import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

//@Entity
//@Table(name = "roles")
public class Role {
    private Integer id;
    private String roleName;

    public Role(Integer id, String roleName){
        this.id = id;
        this.roleName = roleName;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return roleName;
    }

    public void setRole(String roleName) {
        this.roleName = roleName;
    }
}

