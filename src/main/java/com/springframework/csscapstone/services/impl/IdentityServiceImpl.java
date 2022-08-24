package com.springframework.csscapstone.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.response_dto.AccountFromIdentityResDto;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class IdentityServiceImpl implements IdentityService {

    /**
     * curl -X POST https://api.fpt.ai/vision/idr/vnm -H "api-key: yJT1v7oFXGqcTUXkYV7loTW28SFXl873" -F "image=@"
     * @param identityCard
     * @return
     */
    @Override
    public AccountFromIdentityResDto extractInfoIdentityCard(MultipartFile identityCard) throws IOException {
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

//        System.out.println(dto);
//        System.out.println(vietVietnameseToEnglish(dto.getData().get(0).address));

        client.close();
        return dto;
    }

}
