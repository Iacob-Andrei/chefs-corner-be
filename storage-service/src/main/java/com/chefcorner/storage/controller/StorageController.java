package com.chefcorner.storage.controller;

import com.chefcorner.storage.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("api/file")
public class StorageController {

    private final StorageService storageService;

    @Operation(summary = "Download file.")
    @GetMapping("")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("id")String id){
        return ResponseEntity.ok().body(storageService.downloadFile(id));
    }

    @Operation(summary = "Upload direction video for recipe and order number.")
    @PatchMapping("/video/{idRecipe},{order}")
    public ResponseEntity<?> uploadDirectionVideo(@PathVariable("idRecipe")Integer idRecipe,
                                                  @PathVariable("order")Integer order,
                                                  @RequestParam MultipartFile video) throws IOException {
        storageService.uploadDirectionVideo(idRecipe, order, video);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Upload profile picture for user.")
    @PatchMapping("/profile/{email}")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable("email")String email,
                                                  @RequestParam MultipartFile image) throws IOException {
        storageService.uploadProfileImage(email, image);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Upload descriptive picture for recipe.")
    @PatchMapping("/recipe/{idRecipe}")
    public ResponseEntity<?> uploadRecipePicture(@PathVariable("idRecipe")Integer idRecipe,
                                                  @RequestParam MultipartFile image) throws IOException {
        storageService.uploadRecipeImage(idRecipe, image);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete file.")
    @DeleteMapping("/delete/{fileName}")
    public void deleteFile(@PathVariable("fileName")String fileName){
        storageService.deleteFile(fileName);
    }
}
