package com.chefscorner.user.service;

import com.chefscorner.user.dto.TokenDto;
import com.chefscorner.user.dto.UserDto;
import com.chefscorner.user.exception.EmailNotUniqueException;
import com.chefscorner.user.model.User;
import com.chefscorner.user.repository.UserInfoRepository;
import com.chefscorner.user.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public TokenDto saveUser(UserDto user, MultipartFile image) throws IOException {
        User userInfo = new User(user.getName(),
                                 user.getEmail(),
                                 passwordEncoder.encode(user.getPassword()),
                                 user.isBusiness(),
                                 ImageUtil.compressImage(image.getBytes()));
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

    public byte[] downloadImage(String email){
        Optional<User> imageData = repository.findByEmail(email);
        return ImageUtil.decompressImage(imageData.get().getData());
    }
}
