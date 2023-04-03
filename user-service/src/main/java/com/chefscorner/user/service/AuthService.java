package com.chefscorner.user.service;

import com.chefscorner.user.dto.TokenDto;
import com.chefscorner.user.exception.EmailNotUniqueException;
import com.chefscorner.user.model.User;
import com.chefscorner.user.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public TokenDto saveUser(User userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        try {
            repository.save(userInfo);
        } catch (Exception e){
            throw new EmailNotUniqueException(userInfo.getEmail());
        }
        return generateToken(userInfo.getEmail());
    }

    public TokenDto generateToken(String email){
        String token = jwtService.generateToken(email);
        return TokenDto.builder()
                .token(token)
                .build();
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
