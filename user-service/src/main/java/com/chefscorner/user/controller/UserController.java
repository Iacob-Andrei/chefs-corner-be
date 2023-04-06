package com.chefscorner.user.controller;

import com.chefscorner.user.dto.UserDto;
import com.chefscorner.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("{email}")
    public ResponseEntity<UserDto> getUserDataByEmail(@PathVariable("email")String email){
        return ResponseEntity.ok().body(service.getUserByEmail(email));
    }
}
