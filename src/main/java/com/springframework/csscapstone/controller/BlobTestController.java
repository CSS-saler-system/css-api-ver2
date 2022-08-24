package com.springframework.csscapstone.controller;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.springframework.csscapstone.payload.response_dto.fpt_ai.AccountFromDriverLicencesResDto;
import com.springframework.csscapstone.payload.response_dto.fpt_ai.AccountFromIdentityResDto;
import com.springframework.csscapstone.payload.response_dto.fpt_ai.AccountFromPassportResDto;
import com.springframework.csscapstone.services.IdentityService;
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
import java.util.concurrent.ExecutionException;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
@PropertySource(value = "classpath:application-storage.properties")
@RequiredArgsConstructor
public class BlobTestController {

//    private final EmailServices emailServices;
    private final IdentityService identityService;

    @Value("${product_image_container}")
    private String productContainer;

    @PostMapping(value = "/upload", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadImageTest(@RequestParam("file") List<MultipartFile> file) {
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

    @PostMapping(
            value = "/extract-information/by-id-card",
            consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> extractInformationIdentityCard(
            @RequestParam("identity_card") MultipartFile identityCard) throws IOException, ExecutionException, InterruptedException {
        AccountFromIdentityResDto accountFromIdentityResDto = identityService.extractInfoIdentityCard(identityCard).get();
        return ok(accountFromIdentityResDto);
    }

    @PostMapping(
            value = "/extract-information/by-drive-licences",
            consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> extractInformationDriverLicencesCard(
            @RequestParam("identity_card") MultipartFile identityCard) throws IOException, ExecutionException, InterruptedException {
        AccountFromDriverLicencesResDto res = identityService.extractInfoDriveLicenseCard(identityCard).get();
        return ok(res);
    }

    @PostMapping(
            value = "/extract-information/by-passport",
            consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> extractInformationPassport(
            @RequestParam("identity_card") MultipartFile identityCard) throws IOException, ExecutionException, InterruptedException {
        AccountFromPassportResDto res = identityService.extractInfoPassport(identityCard).get();
        return ok(res);
    }

}
