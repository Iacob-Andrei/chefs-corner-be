package com.chefscorner.auth.service;

import com.chefscorner.auth.dto.TokenDto;
import com.chefscorner.auth.dto.UserDto;
import com.chefscorner.auth.exception.EmailNotUniqueException;
import com.chefscorner.auth.model.User;
import com.chefscorner.auth.repository.UserInfoRepository;
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

    public TokenDto saveUser(UserDto user) {
        User userInfo = new User(user.getName(),
                                 user.getEmail(),
                                 passwordEncoder.encode(user.getPassword()),
                                 user.isBusiness());
        try {
            repository.save(userInfo);
        } catch (Exception e){
            throw new EmailNotUniqueException(userInfo.getEmail());
        }
        TokenDto token = generateToken(userInfo.getEmail());
        token.setId(userInfo.getId());
        return token;
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
