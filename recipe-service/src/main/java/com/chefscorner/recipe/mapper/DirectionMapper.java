package com.chefscorner.recipe.mapper;

import com.chefscorner.recipe.dto.DirectionDto;
import com.chefscorner.recipe.model.Direction;
import com.chefscorner.recipe.util.ImageUtil;

public class DirectionMapper {

    public static DirectionDto directionToDirectionDto(Direction direction){
        return DirectionDto.builder()
                .id(direction.getId())
                .order(direction.getOrder())
                .instruction(direction.getInstruction())
                .video_data(ImageUtil.decompressImage(direction.getVideo_data()))
                .build();
    }
}
