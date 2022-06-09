package com.springframework.csscapstone.utils.blob_utils;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class BlobUploadImages {



    @Value("${connection-string}")
    private String connectionString;

    @Value("${product_image_container}")
    private String productContainer;

    @Value("${account_image_container}")
    private String accountContainer;

    @Value("${prize_image_container}")
    private String prizeContainer;

    /**
     * TODO Upload Image
     *
     * @param key
     * @param value
     */
    @SneakyThrows
    public void azureAccountStorageHandler(String key, MultipartFile value) {
        BlobContainerClient container = new BlobContainerClientBuilder()
                .containerName(accountContainer)
                .connectionString(connectionString)
                .buildClient();
        BlobClient blobClient = container.getBlobClient(key);
        blobClient.upload(value.getInputStream(), value.getSize(), true);
    }
    @SneakyThrows
    public void azureProductStorageHandler(String key, MultipartFile value) {
        BlobContainerClient container = new BlobContainerClientBuilder()
                .containerName(productContainer)
                .connectionString(connectionString)
                .buildClient();
        BlobClient blobClient = container.getBlobClient(key);
        blobClient.upload(value.getInputStream(), value.getSize(), true);
    }

    @SneakyThrows
    public void azurePrizeStorageHandler(String key, MultipartFile image) {
        BlobContainerClient container = new BlobContainerClientBuilder()
                .containerName(prizeContainer)
                .connectionString(connectionString)
                .buildClient();
        BlobClient blobClient = container.getBlobClient(key);
        blobClient.upload(image.getInputStream(), image.getSize(), true);
    }
}
