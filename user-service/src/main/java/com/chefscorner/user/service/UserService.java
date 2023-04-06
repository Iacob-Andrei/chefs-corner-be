package com.chefscorner.user.service;

import com.chefscorner.user.dto.UserDto;
import com.chefscorner.user.exception.EmailNotFoundException;
import com.chefscorner.user.model.User;
import com.chefscorner.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserDto getUserByEmail(String email){
        Optional<User> user = repository.findByEmail(email);
        return user.map(UserDto::userToUserDto).orElseThrow(()-> new EmailNotFoundException("User not found with email : " + email));
    }
}
