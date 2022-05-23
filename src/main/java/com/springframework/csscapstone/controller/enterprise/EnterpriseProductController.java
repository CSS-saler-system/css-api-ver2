package com.springframework.csscapstone.controller.enterprise;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.basic.ProductDto;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResponse;
import com.springframework.csscapstone.services.ProductService;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Product.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Product (Enterprise)")
@RequiredArgsConstructor
@RestController
public class EnterpriseProductController {
    private final ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @GetMapping(V2_LIST_PRODUCT)
    public ResponseEntity<?> getListProductDto(
            @RequestParam(value = "productName", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "inStock", required = false) Long inStock,
            @RequestParam(value = "price", required = false) Double minPrice,
            @RequestParam(value = "price", required = false) Double maxPrice,
            @RequestParam(value = "pointSale", required = false) Double minPointSale,
            @RequestParam(value = "pointSale", required = false) Double maxPointSale,
            @RequestParam(value = "status", required = false, defaultValue = "ACTIVE") ProductStatus productStatus,
            @RequestParam(value = "page_number", required = false) Integer pageNumber,
            @RequestParam(value = "page_size", required = false) Integer pageSize
    ) {

        PageImplResponse<ProductDto> result = productService.findAllProduct(
                name, brand, inStock, minPrice, maxPrice,
                minPointSale, maxPointSale, productStatus,
                pageNumber, pageSize);
        return ok(result);
    }

    @GetMapping(V2_GET_PRODUCT + "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") UUID id) throws ProductNotFoundException {
        return ok(productService.findById(id));
    }


    @PostMapping(value = V2_CREATE_PRODUCT,
            consumes = {"multipart/form-data"})
    public ResponseEntity<?> addNewProduct(
            @RequestPart(value = "type_image") @Valid MultipartFile[] typeImages,
            @RequestPart(value = "certification_image") @Valid MultipartFile[] certificationImages,
            @RequestPart(value = "product") String dto
    ) throws ProductInvalidException, AccountNotFoundException, IOException {
        List<MultipartFile> collect = Stream.of(typeImages).collect(Collectors.toList());
        List<MultipartFile> _collect = Stream.of(certificationImages).collect(Collectors.toList());
        ProductCreatorDto productCreatorDto = new ObjectMapper().readValue(dto, ProductCreatorDto.class);

        return ok(this.productService.createProduct(productCreatorDto, collect, _collect));
    }

    @PostMapping(value = V2_CREATE_TEST_PRODUCT, consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UUID> getMultipatt(
            @RequestPart("type_image") List<MultipartFile> typeImages,
            @RequestPart("certification_image") List<MultipartFile> certificationImages
    ) throws ProductInvalidException, AccountNotFoundException, IOException {
//        return ok(productService.createProduct(dto, typeImages, certificationImages));
        return ok(UUID.randomUUID());
    }

//
//    @PutMapping
//    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductUpdaterDto dto) {
//
//    }

    @DeleteMapping(V2_DELETE_PRODUCT + "/{id}")
    public ResponseEntity<String> disableProduct(@PathVariable("id") UUID id) {
        this.productService.disableProduct(id);
        return ResponseEntity.ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }
}
