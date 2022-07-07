package com.springframework.csscapstone.controller;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.springframework.csscapstone.data.domain.User;
import com.springframework.csscapstone.services.EmailServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
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
