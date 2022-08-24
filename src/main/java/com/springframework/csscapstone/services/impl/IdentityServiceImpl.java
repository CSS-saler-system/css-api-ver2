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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class IdentityServiceImpl implements IdentityService {


    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<AccountFromIdentityResDto> extractInfoIdentityCard(MultipartFile identityCard) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.fpt.ai/vision/idr/vnm");
        httpPost.setHeader("api-key", "yJT1v7oFXGqcTUXkYV7loTW28SFXl873");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("image", identityCard.getBytes(),
                        ContentType.MULTIPART_FORM_DATA, identityCard.getOriginalFilename());
        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);

        CloseableHttpResponse response = client.execute(httpPost);
        String json = EntityUtils.toString(response.getEntity());
        AccountFromIdentityResDto dto = new ObjectMapper()
                .readValue(json, AccountFromIdentityResDto.class);

        client.close();
        return CompletableFuture.completedFuture(dto);
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<AccountFromDriverLicencesResDto> extractInfoDriveLicenseCard(MultipartFile identityCard) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.fpt.ai/vision/dlr/vnm");
        httpPost.setHeader("api-key", "yJT1v7oFXGqcTUXkYV7loTW28SFXl873");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("image", identityCard.getBytes(),
                        ContentType.MULTIPART_FORM_DATA, identityCard.getOriginalFilename());
        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);

        CloseableHttpResponse response = client.execute(httpPost);
        String json = EntityUtils.toString(response.getEntity());
        AccountFromDriverLicencesResDto dto = new ObjectMapper().readValue(json, AccountFromDriverLicencesResDto.class);

        client.close();
        return CompletableFuture.completedFuture(dto);
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<AccountFromPassportResDto> extractInfoPassport(MultipartFile identityCard) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.fpt.ai/vision/passport/vnm");
        httpPost.setHeader("api-key", "yJT1v7oFXGqcTUXkYV7loTW28SFXl873");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("image", identityCard.getBytes(),
                ContentType.MULTIPART_FORM_DATA, identityCard.getOriginalFilename());
        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);

        CloseableHttpResponse response = client.execute(httpPost);
        String json = EntityUtils.toString(response.getEntity());
        AccountFromPassportResDto dto = new ObjectMapper().readValue(json, AccountFromPassportResDto.class);

        client.close();
        return CompletableFuture.completedFuture(dto);
    }

}
