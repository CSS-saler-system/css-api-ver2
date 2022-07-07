package com.springframework.csscapstone.controller;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
@PropertySource(value = "classpath:application-storage.properties")
@RequiredArgsConstructor
public class BlobTestController {

//    private final EmailServices emailServices;

    @Value("${product_image_container}")
    private String productContainer;

    @PostMapping(value = "/upload", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadImageTest(@RequestParam("file") List<MultipartFile> file) throws IOException {
        BlobContainerClient container = new BlobContainerClientBuilder()
                .containerName(this.productContainer)
                .buildClient();

        String blobName = UUID.randomUUID().toString();
        return ok(blobName);
    }
//
//    @GetMapping("/send-mail")
//    public ResponseEntity<?> sendEmail(
//            @RequestParam("name") String name,
//            @RequestParam("email") String email) {
//        emailServices.sendEmailNotification(new User(name, email));
//        return ok("sending email");
//    }

}
