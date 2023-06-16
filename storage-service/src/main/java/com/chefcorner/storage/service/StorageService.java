package com.chefcorner.storage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.chefcorner.storage.dto.PatchImageBodyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${aws.data.bucketname}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private WebService webService;

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String extension = "." + Objects.requireNonNull(multipartFile.getContentType()).split("/")[1];
        String fileName = UUID.randomUUID() + extension;
        File file = convert(multipartFile);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        s3Client.putObject(putObjectRequest);
        file.delete();

        return fileName;
    }

    public void uploadDirectionVideo(Integer idRecipe, Integer order, MultipartFile video) throws IOException {
        String imageId = uploadFile(video);

        PatchImageBodyDto body = PatchImageBodyDto.builder()
                .idRecipe(idRecipe)
                .imageId(imageId)
                .order(order)
                .build();
        webService.patchVideoDirection(body);
    }

    public void uploadRecipeImage(Integer idRecipe, MultipartFile image) throws IOException {
        String imageId = uploadFile(image);
        PatchImageBodyDto body = PatchImageBodyDto.builder()
                .imageId(imageId)
                .idRecipe(idRecipe)
                .build();
        webService.patchRecipeImage(body);
    }

    public void uploadProfileImage(String email, MultipartFile image) throws IOException {
        String imageId = uploadFile(image);
        PatchImageBodyDto body = PatchImageBodyDto.builder().imageId(imageId).email(email).build();
        webService.patchProfileImage(body);
    }

    public void deleteFile(String fileName){
        s3Client.deleteObject(bucketName, fileName);
    }

    public static File convert(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("temp", null);
        multipartFile.transferTo(file);
        return file;
    }

    public byte[] downloadFile(String fileName){
        byte[] content = new byte[0];
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            content = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
