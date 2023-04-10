package com.chefscorner.user.controller;

import com.chefscorner.user.dto.UserDto;
import com.chefscorner.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/user")
public class UserController {

    private final UserService service;

    @GetMapping("{email}")
    public ResponseEntity<UserDto> getUserDataByEmail(@PathVariable("email")String email){
        return ResponseEntity.ok().body(service.getUserByEmail(email));
    }

    @PatchMapping("{email}/image")
    public void updateUserImage(@PathVariable("email")String email, @RequestParam MultipartFile image) throws IOException {
        service.updateUserImage(email, image);
    }

    @GetMapping("{email}/image")
    public ResponseEntity<byte[]> getUserImage(@PathVariable("email")String email){
        return ResponseEntity.ok().body(service.getUserImageByEmail(email));
    }
}
