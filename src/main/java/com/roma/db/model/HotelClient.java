package com.roma.db.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "hotel_client")
public class HotelClient implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "client_role", joinColumns = @JoinColumn(name = "client_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public HotelClient(String firstName, String lastName, String login, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.roles = roles;
    }

    public HotelClient() {
    }

    public  HotelClient(String firstName, String lastName, String login, String password, Set<Role> es) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.roles = es;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final SimpleGrantedAuthority simpleGrantedAuthority;
        if (roles.contains(Role.ADMIN))
            simpleGrantedAuthority= new SimpleGrantedAuthority(Role.ADMIN.name());
        else if (roles.contains(Role.MAID))
            simpleGrantedAuthority= new SimpleGrantedAuthority(Role.MAID.name());
        else
            simpleGrantedAuthority = new SimpleGrantedAuthority(Role.USER.name());

        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
