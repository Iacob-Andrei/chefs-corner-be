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
public class MailRequestBodyDto {
    private String token;
    private String ownerName;
    private String ownerMail;
    private String requesterName;
    private String requesterMail;
    private String recipeName;

    public MailRequestBodyDto(String token, User owner, User requester, String recipeName){
        this.token = token;
        this.recipeName = recipeName;
        this.ownerName = owner.getName();
        this.ownerMail = owner.getEmail();
        this.requesterName = requester.getName();
        this.requesterMail = requester.getEmail();
    }
}
