package com.chefscorner.user.dto;

import com.chefscorner.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
    private int id;
    private String name;
    private String email;
    private boolean business;
    private byte[] image;

    public static UserDto userToUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .business(user.isBusiness())
                .image(user.getData())
                .build();
    }
}
