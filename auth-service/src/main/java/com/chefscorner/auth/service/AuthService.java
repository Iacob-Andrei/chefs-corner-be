package com.chefscorner.auth.service;

import com.chefscorner.auth.dto.TokenDto;
import com.chefscorner.auth.dto.UserDto;
import com.chefscorner.auth.exception.EmailNotConfirmedException;
import com.chefscorner.auth.exception.EmailNotUniqueException;
import com.chefscorner.auth.model.ConfirmationToken;
import com.chefscorner.auth.model.User;
import com.chefscorner.auth.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserInfoRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ConfirmationTokenService confirmationTokenService;

    @Transactional
    public void saveUser(UserDto user) {
        User userInfo = new User(user.getName(),
                                 user.getEmail(),
                                 passwordEncoder.encode(user.getPassword()),
                                 user.isBusiness());
        try {
            repository.save(userInfo);
        } catch (Exception e){
            throw new EmailNotUniqueException();
        }

        confirmationTokenService.saveConfirmationToken(userInfo);
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

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);

        if (confirmationToken.getConfirmed() != null) {
            return "Email already confirmed!";
        }

        if(confirmationToken.getExpires().isBefore(LocalDateTime.now())){
            throw new RuntimeException("token expired");
        }

        confirmationTokenService.setConfirmedAt(confirmationToken);
        User user = confirmationToken.getUser();
        user.setConfirmed(true);
        repository.save(user);

        return "Account confirmed successfully!";
    }

    public void isConfirmed(String email) {
        User user = repository.findByEmail(email).get();
        if(!user.isConfirmed()) throw new EmailNotConfirmedException();
    }
}
