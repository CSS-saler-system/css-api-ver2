package com.springframework.csscapstone.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.response_dto.fpt_ai.AccountFromDriverLicencesResDto;
import com.springframework.csscapstone.payload.response_dto.fpt_ai.AccountFromIdentityResDto;
import com.springframework.csscapstone.payload.response_dto.fpt_ai.AccountFromPassportResDto;
import com.springframework.csscapstone.services.IdentityService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:application-fptai.properties")
public class IdentityServiceImpl implements IdentityService {
    @Value("${request.key}")
    private String requestKey;

    @Value("${request.body}")
    private String requestBody;

    @Value("${fpt.secret.key}")
    private String fptSecretKey;

    @Value("${identity.url}")
    private String identityUrl;

    @Value("${driverLicences.url}")
    private String driverLicencesUrl;

    @Value("${passport.url}")
    private String passportUrl;

    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<AccountFromIdentityResDto> extractInfoIdentityCard(MultipartFile identityCard) throws IOException {
        AccountFromIdentityResDto dto;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(identityUrl);
            httpPost.setHeader(requestKey, fptSecretKey);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody(requestBody, identityCard.getBytes(),
                    ContentType.MULTIPART_FORM_DATA, identityCard.getOriginalFilename());
            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);

            CloseableHttpResponse response = client.execute(httpPost);
            String json = EntityUtils.toString(response.getEntity());
            dto = new ObjectMapper().readValue(json, AccountFromIdentityResDto.class);
        }
        return CompletableFuture.completedFuture(dto);
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<AccountFromDriverLicencesResDto> extractInfoDriveLicenseCard(MultipartFile identityCard) throws IOException {
        AccountFromDriverLicencesResDto dto;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(driverLicencesUrl);
            httpPost.setHeader(requestKey, fptSecretKey);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody(requestBody, identityCard.getBytes(),
                    ContentType.MULTIPART_FORM_DATA, identityCard.getOriginalFilename());
            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);

            CloseableHttpResponse response = client.execute(httpPost);
            String json = EntityUtils.toString(response.getEntity());
            dto = new ObjectMapper().readValue(json, AccountFromDriverLicencesResDto.class);

        }
        return CompletableFuture.completedFuture(dto);
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<AccountFromPassportResDto> extractInfoPassport(MultipartFile identityCard) throws IOException {
        AccountFromPassportResDto dto;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(passportUrl);
            httpPost.setHeader(requestKey, fptSecretKey);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody(requestBody, identityCard.getBytes(),
                    ContentType.MULTIPART_FORM_DATA, identityCard.getOriginalFilename());
            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);
            CloseableHttpResponse response = client.execute(httpPost);
            String json = EntityUtils.toString(response.getEntity());
            dto = new ObjectMapper().readValue(json, AccountFromPassportResDto.class);
        }
        return CompletableFuture.completedFuture(dto);
    }

}
