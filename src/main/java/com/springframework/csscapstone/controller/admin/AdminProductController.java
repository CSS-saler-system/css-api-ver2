package com.springframework.csscapstone.controller.admin;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.css_business.services.ProductService;
import com.springframework.csscapstone.css_business.model_dto.basic.ProductDto;
import com.springframework.csscapstone.css_business.model_dto.custom.creator_model.ProductCreatorDto;
import com.springframework.csscapstone.css_data.status.ProductStatus;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Product.*;
import static com.springframework.csscapstone.utils.request_utils.RequestUtils.getRequestParam;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product (Admin)")
public class AdminProductController {
    public static final String PRODUCT = "product";
    private final ProductService service;

    @GetMapping(V1_LIST_PRODUCT)
    public ResponseEntity<List<ProductDto>> getListDto(
            @RequestParam(value = "productName", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "weight", required = false) Double weight,
            @RequestParam(value = "shortDescription", required = false) String shortDescription,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "inStock", required = false, defaultValue = "0") Long inStock,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "pointSale", required = false) Double pointSale,
            @RequestParam(value = "status", required = false, defaultValue = "ACTIVE") ProductStatus productStatus
    ) {
        name = getRequestParam(name);
        brand = getRequestParam(brand);
        weight = Objects.nonNull(weight) ? weight : 0.0;
        shortDescription = getRequestParam(shortDescription);
        description = getRequestParam(description);
        inStock = Objects.nonNull(inStock) ? inStock : 0L;
        price = Objects.nonNull(price) ? price : 0.0;
        pointSale = Objects.nonNull(pointSale) ? pointSale : 0.0;
        productStatus = Objects.nonNull(productStatus) ? productStatus : ProductStatus.IN_STOCK;

        List<ProductDto> result = service.findAllProduct(name, brand, weight, shortDescription, description,
                inStock, price, pointSale, productStatus);

        return ok(result);

    }

    @GetMapping(V1_GET_PRODUCT + "/{id}")
    public ResponseEntity<ProductDto> getAccountById(@PathVariable("id") UUID id) throws ProductNotFoundException {
        return ok(service.findById(id));
    }

    @PutMapping(V1_UPDATE_PRODUCT)
    public ResponseEntity<UUID> updateAccount(@RequestBody ProductDto dto) throws ProductNotFoundException, ProductInvalidException {
        return ok(service.updateProductDto(dto));
    }

    @PostMapping(V1_CREATE_PRODUCT)
    public ResponseEntity<UUID> addNewAccount(@RequestBody ProductCreatorDto dto) throws ProductNotFoundException, ProductInvalidException {
        return ok(service.createProduct(dto));
    }

    @DeleteMapping(V1_DELETE_PRODUCT + "/{id}")
    public ResponseEntity<String> disableAccount(@PathVariable("id") UUID id) {
        this.service.disableProduct(id);
        return ResponseEntity.ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }

}
