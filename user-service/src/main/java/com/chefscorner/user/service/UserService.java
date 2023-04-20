package com.chefscorner.user.service;

import com.chefscorner.user.dto.UserDto;
import com.chefscorner.user.exception.EmailNotFoundException;
import com.chefscorner.user.model.User;
import com.chefscorner.user.repository.UserRepository;
import com.chefscorner.user.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserDto getUserByEmail(String email){
        Optional<User> user = repository.findByEmail(email);
        return user.map(UserDto::userToUserDto).orElseThrow(()-> new EmailNotFoundException("User not found with email : " + email));
    }

    public byte[] getUserImageByEmail(String email) {
        Optional<User> userOptional = repository.findByEmail(email);
        if(userOptional.isEmpty()) return null;

        User user = userOptional.get();
        return ImageUtil.decompressImage(user.getData());
    }

    public void updateUserImage(String email, MultipartFile image) throws IOException {
        Optional<User> userOptional = repository.findByEmail(email);
        if(userOptional.isEmpty()) return; //TODO: return exception

        User user = userOptional.get();
        user.setData(ImageUtil.compressImage(image.getBytes()));
        repository.save(user);
    }
}
