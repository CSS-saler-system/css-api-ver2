package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.basic.ProductDto;
import com.springframework.csscapstone.services.ProductService;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Product.V3_GET_PRODUCT;
import static com.springframework.csscapstone.config.constant.ApiEndPoint.Product.V3_LIST_PRODUCT;
import static com.springframework.csscapstone.utils.request_utils.RequestUtils.getRequestParam;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product (Collaborator)")
public class CollaboratorProductController {
    public static final String PRODUCT = "product";
    private final ProductService service;

    @GetMapping(V3_LIST_PRODUCT)
    public ResponseEntity<List<ProductDto>> getListProductDto(
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
    @GetMapping(V3_GET_PRODUCT + "/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") UUID id) throws ProductNotFoundException {
        return ok(service.findById(id));
    }

}
