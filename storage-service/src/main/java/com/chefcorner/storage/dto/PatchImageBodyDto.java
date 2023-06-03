package com.chefcorner.storage.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatchImageBodyDto {
    private Integer idRecipe;
    private String email;
    private String imageId;
    private Integer order;
}
