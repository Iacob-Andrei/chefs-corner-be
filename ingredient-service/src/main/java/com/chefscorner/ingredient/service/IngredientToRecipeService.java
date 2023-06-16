package com.chefscorner.ingredient.service;

import com.chefscorner.ingredient.dto.IngredientToRecipeDto;
import com.chefscorner.ingredient.dto.IngredientPriceDto;
import com.chefscorner.ingredient.exception.IngredientNotFoundException;
import com.chefscorner.ingredient.mapper.IngredientMapper;
import com.chefscorner.ingredient.mapper.IngredientToRecipeMapper;
import com.chefscorner.ingredient.model.Ingredient;
import com.chefscorner.ingredient.model.IngredientPrice;
import com.chefscorner.ingredient.model.IngredientToRecipe;
import com.chefscorner.ingredient.repository.IngredientPriceRepository;
import com.chefscorner.ingredient.repository.IngredientRepository;
import com.chefscorner.ingredient.repository.IngredientToRecipeRepository;
import com.chefscorner.ingredient.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class IngredientToRecipeService {

    private final IngredientToRecipeRepository ingredientToRecipeRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientPriceRepository ingredientPriceRepository;

    public List<IngredientToRecipeDto> getIngredientsByIdRecipe(Integer id) {
        List<IngredientToRecipe> ingredientsList= ingredientToRecipeRepository.findIngredientsByIdRecipe(id);
        return ingredientsList.stream().map(IngredientToRecipeMapper::ingredientToRecipeToIngredientToRecipeDto).collect(Collectors.toList());
    }

    @Transactional
    public void addIngredientsNeeded(Integer idRecipe, List<IngredientToRecipeDto> ingredients) {
        for(IngredientToRecipeDto ingredientDto : ingredients){
            Optional<Ingredient> optional = ingredientRepository.findByName(ingredientDto.getName());
            if(optional.isEmpty()) throw new IngredientNotFoundException(ingredientDto.getName());

            IngredientToRecipe ingredientToRecipe = new IngredientToRecipe(
                    idRecipe,
                    optional.get(),
                    ingredientDto.getAmount(),
                    ingredientDto.getDescription(),
                    ingredientDto.getUnit(),
                    ingredientDto.getGrams()
            );
            ingredientToRecipeRepository.save(ingredientToRecipe);
        }
    }

    public Map<Integer,List<IngredientToRecipeDto>> getIngredientsByIdsRecipes(List<Integer> ids) {
        List<IngredientToRecipe> response = ingredientToRecipeRepository.findIngredientToRecipesByRecipeIsIn(ids);
        Map<Integer, List<IngredientToRecipeDto>> mapIngredients = new HashMap<>();

        for(Integer id : ids){
            mapIngredients.put(
                    id,
                    response.stream()
                            .filter(el -> Objects.equals(el.getRecipe(), id))
                            .map(IngredientToRecipeMapper::ingredientToRecipeToIngredientToRecipeDto).collect(Collectors.toList())
            );
        }
        return mapIngredients;
    }

    public Map<Integer,List<IngredientToRecipeDto>> getRecipesByIngredients(List<Integer> ids) {
        //TODO: de adaugat
        List<Integer> result = ingredientToRecipeRepository.findRecipesContainingIngredients(ids, ids.size());
        Collections.shuffle(result);
        int max = Math.min(result.size(), 10);
        return this.getIngredientsByIdsRecipes(result.subList(0,max));
    }

    public IngredientPriceDto addPriceForIngredientInRecipe(String bearerToken, IngredientPriceDto body) throws JSONException {
        Optional<Ingredient> optional = ingredientRepository.findById(body.getIdIngredient());
        if(optional.isEmpty()) throw new IngredientNotFoundException(body.getIdIngredient().toString());

        Ingredient ingredient = optional.get();
        String owner = JwtUtil.getSubjectFromToken(bearerToken);

        return IngredientMapper.priceToPriceDto(
                ingredientPriceRepository.save(new IngredientPrice(owner, body.getSeller(), body.getPrice(), ingredient))
        );
    }

    public void updatePriceForIngredientInRecipe(String bearerToken, IngredientPriceDto body) throws JSONException {
        Optional<IngredientPrice> optional = ingredientPriceRepository.findById(body.getId());
        if(optional.isEmpty()) throw new IngredientNotFoundException(body.getIdIngredient().toString());

        IngredientPrice price = optional.get();
        String owner = JwtUtil.getSubjectFromToken(bearerToken);
        if(!price.getOwner().equals(owner)) throw new IngredientNotFoundException(body.getIdIngredient().toString());

        price.setPrice(body.getPrice());
        price.setSeller(body.getSeller());
        ingredientPriceRepository.save(price);
    }

    @Transactional
    public void deletePriceForIngredientInRecipe(String bearerToken, Integer id) throws JSONException {
        Optional<IngredientPrice> optional = ingredientPriceRepository.findById(id);
        if(optional.isEmpty()) throw new IngredientNotFoundException(id.toString());

        IngredientPrice price = optional.get();
        String owner = JwtUtil.getSubjectFromToken(bearerToken);
        if(!price.getOwner().equals(owner)) throw new IngredientNotFoundException(id.toString());

        ingredientPriceRepository.deleteIngredientPriceById(price.getId());
    }

    @Transactional
    public void patchIngredientsNeeded(Integer idRecipe, List<IngredientToRecipeDto> ingredients) {
        ingredientToRecipeRepository.deleteIngredientToRecipesByRecipe(idRecipe);
        addIngredientsNeeded(idRecipe, ingredients);
    }
}
