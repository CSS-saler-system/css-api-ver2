package com.springframework.csscapstone.controller;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user/blob")
public class BlobTestController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    //https://csssalersystem.blob.core.windows.net/css-image
    //azure-blob://<your-container-name>/<your-blob-name>
    @Value("azure-blob://css-image/Screenshot (375).png")
    private Resource blobFile;

    @PostMapping("/writeBlobFile")
    public String writeBlobFile(@RequestBody String data) throws IOException {
        try (OutputStream os = ((WritableResource) this.blobFile).getOutputStream()) {
            os.write(data.getBytes());
        }
        return "file was updated";
    }

    @PostMapping(value = "/upload", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadImageTest(
            @RequestParam("file") MultipartFile file) throws IOException {
        String consStr = "DefaultEndpointsProtocol=https;AccountName=csssalersystem;AccountKey=jCb20BfSP2CkB1IduJlPAxcQWX+GgwrBp+aobpk5ggaUpKa2dSGf9iSH4QggdFb9Nwjm/o+un2X3ScNdjrpovA==;EndpointSuffix=core.windows.net";
        BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString(consStr)
                .containerName("css-image")
                .buildClient();

        String blobName = UUID.randomUUID().toString();
        BlobClient blobClient = container.getBlobClient(blobName);
        blobClient.upload(file.getInputStream(), file.getSize(), true);
        return ok(blobName);

    }

    @GetMapping("/readBlobFile")
    public String readBlobFile() throws IOException {
        return StreamUtils.copyToString(this.blobFile.getInputStream(), Charset.defaultCharset());
    }

}
