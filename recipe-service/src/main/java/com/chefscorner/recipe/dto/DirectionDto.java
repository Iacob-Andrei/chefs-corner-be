package com.chefscorner.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DirectionDto {

    private Integer id;
    private Integer order;
    private String instruction;
    private String video_name;
    private byte[] video_data;
}
