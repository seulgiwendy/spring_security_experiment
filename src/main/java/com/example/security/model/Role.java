package com.example.security.model;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
@Table(name = "tbl_account_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fno;

    private String roleName;

    public Role() {

    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Long getFno() {
        return fno;
    }

    public void setFno(Long fno) {
        this.fno = fno;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return String.format("role : %s" , this.roleName);
    }
}
