package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.DirectionDto;
import com.chefscorner.recipe.mapper.DirectionMapper;
import com.chefscorner.recipe.model.Direction;
import com.chefscorner.recipe.repository.DirectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DirectionService {
    private final DirectionRepository directionRepository;

    public List<DirectionDto> getDirectionForRecipeByid(Integer id) {
        List<Direction> directionList = directionRepository.findByRecipeId(id);
        return directionList.stream().map(DirectionMapper::directionToDirectionDto).collect(Collectors.toList());
    }
}
