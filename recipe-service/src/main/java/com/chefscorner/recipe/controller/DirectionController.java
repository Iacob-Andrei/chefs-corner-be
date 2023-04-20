package com.chefscorner.recipe.controller;

import com.chefscorner.recipe.service.DirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/direction")
public class DirectionController {

    private final DirectionService directionService;

    @PatchMapping("/video/{idRecipe},{order}")
    public void uploadDirectionVideo(@PathVariable("idRecipe")Integer idRecipe,
                                     @PathVariable("order")Integer order,
                                     @RequestParam MultipartFile video) throws IOException {
        directionService.uploadDirectionVideo(idRecipe, order, video);
    }
}
