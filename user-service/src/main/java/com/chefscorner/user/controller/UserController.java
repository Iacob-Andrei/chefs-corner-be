package com.chefscorner.user.controller;

import com.chefscorner.user.dto.PatchImageBodyDto;
import com.chefscorner.user.dto.UserDto;
import com.chefscorner.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getUserDataByEmail(@RequestHeader("Authorization") String bearerToken) throws JSONException {
        return ResponseEntity.ok().body(userService.getUserByEmail(bearerToken));
    }

    @PatchMapping("/image")
    public ResponseEntity<String> updateUserImage(@RequestBody PatchImageBodyDto body) {
        userService.updateUserImage(body);
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/data/{pattern}")
    public ResponseEntity<List<String>> getUsersByPattern(@PathVariable("pattern")String pattern){
        return ResponseEntity.ok().body(userService.getUsersByPattern(pattern));
    }
}
