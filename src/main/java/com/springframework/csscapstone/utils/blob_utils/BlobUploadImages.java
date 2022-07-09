package com.springframework.csscapstone.utils.blob_utils;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

import static com.springframework.csscapstone.utils.exception_catch_utils.ExceptionCatchHandler.wrapVoid;

@Transactional
@Component
@RequiredArgsConstructor
public class BlobUploadImages {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${connection-string}")
    private String connectionString;

    @Value("${product_image_container}")
    private String productContainer;

    @Value("${account_image_container}")
    private String accountContainer;

    @Value("${prize_image_container}")
    private String prizeContainer;

    @Value("${campaign_image_container}")
    private String campaignContainer;

    @Value("${transaction_image_container}")
    private String transactionContainer;

    /**
     * TODO Upload Image
     * @param key
     * @param value
     */
    @Async("threadPoolTaskExecutor")
    public void azureAccountStorageHandler(String key, MultipartFile value) {
        BlobContainerClient container = new BlobContainerClientBuilder()
                .containerName(accountContainer)
                .connectionString(connectionString)
                .buildClient();
        BlobClient blobClient = container.getBlobClient(key);
        CompletableFuture.runAsync(wrapVoid(() -> blobClient.upload(value.getInputStream(), value.getSize(), true)));
    }

//    @SneakyThrows
    @Async("threadPoolTaskExecutor")
    public void azureProductStorageHandler(String key, MultipartFile value) {
        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        BlobContainerClient container = new BlobContainerClientBuilder()
                .containerName(productContainer)
                .connectionString(connectionString)
                .buildClient();
        BlobClient blobClient = container.getBlobClient(key);
        CompletableFuture.runAsync(wrapVoid(() -> blobClient.upload(value.getInputStream(), value.getSize(), true)));
    }

    @Async("threadPoolTaskExecutor")
    public void azureCampaignStorageHandler(String key, MultipartFile image) {
        BlobContainerClient container = new BlobContainerClientBuilder()
                .containerName(campaignContainer)
                .connectionString(connectionString)
                .buildClient();
        BlobClient blobClient = container.getBlobClient(key);
        CompletableFuture.runAsync(wrapVoid(() -> blobClient.upload(image.getInputStream(), image.getSize(), true)));
    }

    @Async("threadPoolTaskExecutor")
    public void azureTransactionStorageHandler(String key, MultipartFile image) {
        BlobContainerClient container = new BlobContainerClientBuilder()
                .containerName(transactionContainer)
                .connectionString(connectionString)
                .buildClient();
        BlobClient blobClient = container.getBlobClient(key);
        CompletableFuture.runAsync(wrapVoid(() -> blobClient.upload(image.getInputStream(), image.getSize(), true)));
    }
}
