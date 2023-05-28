package com.chefscorner.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RequestPermissionDto {
    private String requesterName;
    private String requesterEmail;
    private String recipeName;
}
