package com.chefscorner.auth.service;

import com.chefscorner.auth.exception.InvalidTokenException;
import com.chefscorner.auth.model.ConfirmationToken;
import com.chefscorner.auth.model.User;
import com.chefscorner.auth.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MailService mailService;

    public void saveConfirmationToken(User user){
        String tokenStr = UUID.randomUUID().toString();
        ConfirmationToken token = new ConfirmationToken(
                tokenStr,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                user
                );
        confirmationTokenRepository.save(token);
        mailService.sendConfirmationMail(user.getEmail(), tokenStr, user.getName());
    }

    public ConfirmationToken getToken(String token) {
        Optional<ConfirmationToken> optional = confirmationTokenRepository.findConfirmationTokenByToken(token);
        if(optional.isEmpty()) throw new InvalidTokenException();

        return optional.get();
    }

    public void setConfirmedAt(ConfirmationToken confirmationToken) {
        confirmationToken.setConfirmed(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken);
    }
}
