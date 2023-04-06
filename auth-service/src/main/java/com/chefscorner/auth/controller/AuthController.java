package com.chefscorner.auth.controller;

import com.chefscorner.auth.dto.AuthRequest;
import com.chefscorner.auth.dto.TokenDto;
import com.chefscorner.auth.dto.UserDto;
import com.chefscorner.auth.exception.InvalidTokenException;
import com.chefscorner.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<TokenDto> addNewUser(UserDto user, @RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok().body(service.saveUser(user, image));
    }

    @PostMapping("/token")
    public ResponseEntity<TokenDto> getToken(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if(authenticate.isAuthenticated()){
            return ResponseEntity.ok().body(service.generateToken(authRequest.getEmail()));
        }else{
            throw new InvalidTokenException();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token")String token){
        return ResponseEntity.ok().body(service.validateToken(token));
    }
}
