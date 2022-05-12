package com.project.plantOne.config;

import com.project.plantOne.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MyUserDetail implements UserDetails {

    private UUID user_id;
    private String userName;
    private String password;
    private boolean active;
    private List<GrantedAuthority> grantedAuthorityList;

    public MyUserDetail(User user){
        this.user_id = user.getUser_id();
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.active = user.isActiveUser();
        this.grantedAuthorityList = Arrays.stream(user.getUser_role().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return active;
    }

    public UUID getUser_id() {
        return user_id;
    }
}
