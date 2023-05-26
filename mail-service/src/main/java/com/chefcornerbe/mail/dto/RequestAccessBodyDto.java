package com.chefcornerbe.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestAccessBodyDto {
    private String token;
    private String ownerName;
    private String ownerMail;
    private String requesterName;
    private String requesterMail;
    private String recipeName;
}
