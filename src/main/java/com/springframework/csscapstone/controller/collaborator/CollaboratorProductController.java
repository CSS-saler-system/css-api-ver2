package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.basic.ProductDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResponse;
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

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Product.*;
import static com.springframework.csscapstone.utils.request_utils.RequestUtils.getRequestParam;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product (Collaborator)")
public class CollaboratorProductController {
    private final ProductService service;

    @GetMapping(V3_LIST_PRODUCT)
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

        PageImplResponse<ProductDto> result = service.findAllProduct(
                name, brand, inStock, minPrice, maxPrice,
                minPointSale, maxPointSale, productStatus,
                pageNumber, pageSize);
        return ok(result);
    }
    @GetMapping(V3_GET_PRODUCT + "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") UUID id) throws ProductNotFoundException {
        return ok(service.findById(id));
    }
}
