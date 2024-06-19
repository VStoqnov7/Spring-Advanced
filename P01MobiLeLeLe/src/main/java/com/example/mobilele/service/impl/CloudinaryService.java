package com.example.mobilele.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "YOUR_CLOUD_NAME",
                "api_key", "YOUR_API_KEY",
                "api_secret", "YOUR_API_SECRET",
                "secure", true));
    }

    public String saveImage(MultipartFile multipartFile) {
        String imageId = UUID.randomUUID().toString();
        Map<String, Object> params = ObjectUtils.asMap(
                "public_id", imageId,
                "overwrite", true,
                "resource_type", "image");

        File tmpFile = new File(imageId);
        try {
            Files.write(tmpFile.toPath(), multipartFile.getBytes());
            Map<String, Object> uploadResult = cloudinary.uploader().upload(tmpFile, params);
            Files.delete(tmpFile.toPath());
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

//@Service
//public class PictureService {
//
//    private final CloudinaryService cloudinaryService;
//    private final PictureRepository pictureRepository;
//
//    @Autowired
//    public PictureService(CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
//        this.cloudinaryService = cloudinaryService;
//        this.pictureRepository = pictureRepository;
//    }
//
//    public Picture uploadImage(MultipartFile file) throws IOException {
//        // Качване на изображението в Cloudinary и получаване на URL-то
//        String imageUrl = cloudinaryService.saveImage(file);
//
//        // Създаване на нов обект Picture и съхранение в базата данни
//        Picture picture = new Picture();
//        picture.setUrl(imageUrl);
//        return pictureRepository.save(picture);
//    }
//}