package com.chefscorner.recipe.controller;

import com.chefscorner.recipe.dto.PatchDataDto;
import com.chefscorner.recipe.service.DirectionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/direction")
public class DirectionController {

    private final DirectionService directionService;

    @Operation(summary = "Patch video instruction for direction.")
    @PatchMapping("/video")
    public ResponseEntity<String> patchDirectionVideo(@RequestBody PatchDataDto body) {
        directionService.patchDirectionVideo(body);
        return ResponseEntity.ok().body("ok");
    }
}