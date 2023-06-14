package com.chefscorner.ingredient.controller;

import com.chefscorner.ingredient.dto.IngredientDto;
import com.chefscorner.ingredient.dto.IngredientToRecipeDto;
import com.chefscorner.ingredient.dto.IngredientPriceDto;
import com.chefscorner.ingredient.service.IngredientService;
import com.chefscorner.ingredient.service.IngredientToRecipeService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/ingredient")
public class IngredientController {

    private final IngredientToRecipeService ingredientToRecipeService;
    private final IngredientService ingredientService;

//    @Operation(summary = "Find ingredients in recipe.")
    @GetMapping("from-recipe/{id}")
    public ResponseEntity<List<IngredientToRecipeDto>> getIngredientsByIdRecipe(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(ingredientToRecipeService.getIngredientsByIdRecipe(id));
    }

//    @Operation(summary = "Find ingredients for recipes.")
    @PostMapping("from-recipe/list")
    public ResponseEntity<Map<Integer,List<IngredientToRecipeDto>>> getIngredientsByIdsRecipes(@RequestBody List<Integer> ids){
        return ResponseEntity.ok().body(ingredientToRecipeService.getIngredientsByIdsRecipes(ids));
    }

//    @Operation(summary = "Find ingredients by pattern.")
    @GetMapping("/{pattern}")
    public ResponseEntity<List<IngredientDto>> ingredientsByNamePattern(@PathVariable("pattern") String pattern){
        return ResponseEntity.ok().body(ingredientService.findIngredientsByNamePattern(pattern));
    }

//    @Operation(summary = "Add ingredients to recipe.")
    @PostMapping("/add/{idRecipe}")
    public ResponseEntity<?> postIngredientsForRecipe(@PathVariable("idRecipe")Integer idRecipe, @RequestBody List<IngredientToRecipeDto> ingredients){
        ingredientToRecipeService.addIngredientsNeeded(idRecipe, ingredients);
        return ResponseEntity.ok().body("ok");
    }

//    @Operation(summary = "Get recommendation for ingredients.")
    @PostMapping("/recommendation")
    public ResponseEntity<Map<Integer,List<IngredientToRecipeDto>>> getRecommendationWithIngredients(@RequestBody List<Integer> ids){
        return ResponseEntity.ok().body(ingredientToRecipeService.getRecipesByIngredients(ids));
    }

//    @Operation(summary = "Add price for ingredient.")
    @PostMapping("/add-price")
    public ResponseEntity<IngredientPriceDto> addPriceForIngredientInRecipe(@RequestHeader("Authorization") String bearerToken,
                                                           @RequestBody IngredientPriceDto body) throws JSONException {
        return ResponseEntity.ok().body(ingredientToRecipeService.addPriceForIngredientInRecipe(bearerToken, body));
    }

//    @Operation(summary = "Patch price for ingredient.")
    @PatchMapping("/update-price")
    public ResponseEntity<?> updatePriceForIngredientInRecipe(@RequestHeader("Authorization") String bearerToken,
                                                           @RequestBody IngredientPriceDto body) throws JSONException {
        ingredientToRecipeService.updatePriceForIngredientInRecipe(bearerToken, body);
        return ResponseEntity.noContent().build();
    }

//    @Operation(summary = "Delete price for ingredient.")
    @DeleteMapping("/delete-price/{id}")
    public ResponseEntity<?> deletePriceForIngredientInRecipe(@RequestHeader("Authorization") String bearerToken,
                                                              @PathVariable Integer id) throws JSONException {
        ingredientToRecipeService.deletePriceForIngredientInRecipe(bearerToken, id);
        return ResponseEntity.noContent().build();
    }

//    @Operation(summary = "Find prices of user.")
    @GetMapping("/list-prices")
    public ResponseEntity<List<IngredientDto>> getPricesForIngredientOfUser(@RequestHeader("Authorization") String bearerToken) throws JSONException{
        return ResponseEntity.ok().body(ingredientService.getPricesForIngredientOfUser(bearerToken));
    }
}
