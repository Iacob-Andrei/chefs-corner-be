package com.chefscorner.auth.controller;

import com.chefscorner.auth.dto.AuthRequest;
import com.chefscorner.auth.dto.TokenDto;
import com.chefscorner.auth.dto.UserDto;
import com.chefscorner.auth.exception.InvalidTokenException;
import com.chefscorner.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
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


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(@RequestBody UserDto user) {
        service.saveUser(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam("token")String token){
        return ResponseEntity.ok().body(service.confirmToken(token));
    }

    @PostMapping("/token")
    public ResponseEntity<TokenDto> getToken(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if(authenticate.isAuthenticated()){
            service.isConfirmed(authRequest.getEmail());
            return ResponseEntity.ok().body(service.generateToken(authRequest.getEmail()));
        }else{
            throw new InvalidTokenException();
        }
    }

    @GetMapping("/validate")
    public void validateToken(@RequestParam("token")String token){
        service.validateToken(token);
    }
}
