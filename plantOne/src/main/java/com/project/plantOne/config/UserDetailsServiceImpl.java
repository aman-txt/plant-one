package com.project.plantOne.config;

import com.project.plantOne.user.User;
import com.project.plantOne.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        UserDetails userDetails;
        if (user != null) {
            userDetails = new MyUserDetail(user);
            return userDetails;
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}
