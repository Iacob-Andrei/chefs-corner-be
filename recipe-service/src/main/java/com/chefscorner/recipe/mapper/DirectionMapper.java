package com.chefscorner.recipe.mapper;

import com.chefscorner.recipe.dto.DirectionDto;
import com.chefscorner.recipe.model.Direction;

public class DirectionMapper {

    public static DirectionDto directionToDirectionDto(Direction direction){
        return DirectionDto.builder()
                .id(direction.getId())
                .order(direction.getOrder())
                .instruction(direction.getInstruction())
                .build();
    }
}
