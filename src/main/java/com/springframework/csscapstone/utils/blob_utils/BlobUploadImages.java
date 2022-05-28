package com.springframework.csscapstone.utils.blob_utils;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class BlobUploadImages {

    @Value("${account_image_container}")
    private static String accountContainer;

    @Value("${connection-string}")
    private static String connectionString;

    @Value("${product_image_container}")
    private static String productContainer;


    /**
     * TODO Upload Image
     *
     * @param key
     * @param value
     */
    @SneakyThrows
    public static void azureAccountStorageHandler(String key, MultipartFile value) {
        BlobContainerClient container = new BlobContainerClientBuilder()
                .containerName(accountContainer)
                .connectionString(connectionString)
                .buildClient();
        BlobClient blobClient = container.getBlobClient(key);
        blobClient.upload(value.getInputStream(), value.getSize(), true);
    }
    @SneakyThrows
    public static void azureProductStorageHandler(String key, MultipartFile value) {
        BlobContainerClient container = new BlobContainerClientBuilder()
                .containerName(productContainer)
                .connectionString(connectionString)
                .buildClient();
        BlobClient blobClient = container.getBlobClient(key);
        blobClient.upload(value.getInputStream(), value.getSize(), true);
    }
}
