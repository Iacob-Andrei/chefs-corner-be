package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.DirectionDto;
import com.chefscorner.recipe.mapper.DirectionMapper;
import com.chefscorner.recipe.model.Direction;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.repository.DirectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DirectionService {
    private final DirectionRepository directionRepository;

    public List<DirectionDto> getDirectionForRecipeById(Integer id) {
        List<Direction> directionList = directionRepository.findByRecipeId(id);
        return directionList.stream().map(DirectionMapper::directionToDirectionDto).collect(Collectors.toList());
    }

    public void saveDirections(List<DirectionDto> directions, Recipe recipe) {
        for(DirectionDto directionDto : directions){
            Direction direction = new Direction(
                    recipe,
                    directionDto.getOrder(),
                    directionDto.getInstruction()
            );
            direction.setRecipe(recipe);
            directionRepository.save(direction);
        }
    }
}
