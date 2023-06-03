package com.chefscorner.user.service;

import com.chefscorner.user.dto.PatchImageBodyDto;
import com.chefscorner.user.dto.UserDto;
import com.chefscorner.user.exception.EmailNotFoundException;
import com.chefscorner.user.model.User;
import com.chefscorner.user.repository.UserRepository;
import com.chefscorner.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserDto getUserByEmail(String token) throws JSONException {
        String email = JwtUtil.getSubjectFromToken(token);

        Optional<User> user = repository.findByEmail(email);
        return user.map(UserDto::userToUserDto).orElseThrow(()-> new EmailNotFoundException("User not found with email : " + email));
    }

    public void updateUserImage(PatchImageBodyDto body) {
        Optional<User> userOptional = repository.findByEmail(body.getEmail());
        if(userOptional.isEmpty()) throw new EmailNotFoundException(body.getEmail());

        User user = userOptional.get();
        user.setData(body.getImageId());
        repository.save(user);
    }

    public List<String> getUsersByPattern(String pattern) {
        return repository.findUsersByEmailContainingIgnoreCase(pattern).stream().map(User::getEmail).collect(Collectors.toList());
    }
}
