package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.DirectionDto;
import com.chefscorner.recipe.dto.PatchDataDto;
import com.chefscorner.recipe.exception.DirectionNotFoundException;
import com.chefscorner.recipe.model.Direction;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.repository.DirectionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DirectionService {

    private final DirectionRepository directionRepository;

    public void saveDirections(List<DirectionDto> directions, Recipe recipe) {
        for(DirectionDto directionDto : directions){
            Direction direction = new Direction(
                    recipe,
                    directionDto.getOrder(),
                    directionDto.getInstruction(),
                    directionDto.getVideo_name()
            );
            direction.setRecipe(recipe);
            directionRepository.save(direction);
        }
    }

    public void patchDirectionVideo(PatchDataDto body) {
        Optional<Direction> directionOptional = directionRepository.findByOrderAndRecipe(body.getOrder(), body.getIdRecipe());
        if(directionOptional.isEmpty()) throw new DirectionNotFoundException(body.getIdRecipe(), body.getOrder());

        Direction direction = directionOptional.get();
        direction.setVideo(body.getImageId());
        directionRepository.save(direction);
    }

    @Transactional
    public void patchDirectionRecipes(Recipe recipe, List<DirectionDto> newDirections) {
        directionRepository.deleteDirectionsByRecipe(recipe);
        saveDirections(newDirections, recipe);
    }
}
