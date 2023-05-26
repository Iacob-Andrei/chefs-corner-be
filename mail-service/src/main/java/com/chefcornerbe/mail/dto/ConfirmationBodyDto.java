package com.chefcornerbe.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConfirmationBodyDto {
    private String to;
    private String token;
    private String name;
}
