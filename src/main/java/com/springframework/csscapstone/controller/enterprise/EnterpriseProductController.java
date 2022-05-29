package com.springframework.csscapstone.controller.enterprise;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.config.constant.ApiEndPoint;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.ProductUpdaterDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResponse;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResponseDto;
import com.springframework.csscapstone.services.ProductService;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
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
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Product.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Product (Enterprise)")
@RestController
@RequiredArgsConstructor
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

        PageImplResponse<ProductResponseDto> result = productService.findAllProduct(
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


    @PutMapping(value = V2_UPDATE_PRODUCT, consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateProduct(
            @RequestPart String dto,
            @RequestPart List<MultipartFile> normalType,
            @RequestPart List<MultipartFile> certificationType) throws JsonProcessingException {
        ProductUpdaterDto object = new ObjectMapper().readValue(dto, ProductUpdaterDto.class);
        return ok(this.productService.updateProductDto(object, normalType, certificationType));
    }


    @DeleteMapping(V2_DELETE_PRODUCT + "/{id}")
    public ResponseEntity<String> disableProduct(@PathVariable("id") UUID id) {
        this.productService.disableProduct(id);
        return ResponseEntity.ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }
}
