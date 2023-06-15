package com.chefscorner.recipe.mapper;

import com.chefscorner.recipe.dto.DirectionDto;
import com.chefscorner.recipe.model.Direction;

public class DirectionMapper {

    public static DirectionDto directionToDirectionDto(Direction direction, byte[] video){
        return DirectionDto.builder()
                .id(direction.getId())
                .order(direction.getStep())
                .instruction(direction.getInstruction())
                .video_name(direction.getVideo())
                .video_data(video)
                .build();
    }
}
