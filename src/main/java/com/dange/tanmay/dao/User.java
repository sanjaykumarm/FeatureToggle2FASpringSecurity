package com.dange.tanmay.dao;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Table(name="USERDETAILS")
@Entity
@Data
public class User {

    @Id
    @Column(name="ID")
    public int userId;

    @Column(name="USERNAME")
    public String username;

    @Column(name="PASSWORD")
    public String password;

    @Column(name="ROLE")
    public String role;

    @Column(name="MFA_ENABLED")
    public Boolean mfaEnabled;

    @Column(name="FORCE_ENABLED")
    public Boolean forceEnabled;

    @Column(name="SECRET_KEY")
    public String code;



    public UserDetails castUserToUserDetails(){
        return org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                        .username(username)
                        .password(password)
                        .roles(role)
                        .build();
    }

}
