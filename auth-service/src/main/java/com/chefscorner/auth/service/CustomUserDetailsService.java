package com.chefscorner.auth.service;

import com.chefscorner.auth.model.CustomUserDetails;
import com.chefscorner.auth.model.User;
import com.chefscorner.auth.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> credential = repository.findByEmail(email);
        return credential.map(CustomUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("User not found with email : " + email));
    }
}
