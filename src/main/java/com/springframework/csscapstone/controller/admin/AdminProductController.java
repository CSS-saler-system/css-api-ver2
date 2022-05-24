package com.springframework.csscapstone.controller.admin;

import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.basic.ProductDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResponse;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResponseDto;
import com.springframework.csscapstone.services.ProductService;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Product.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product (Admin)")
public class AdminProductController {
    private final ProductService productService;

    @GetMapping(V1_LIST_PRODUCT)
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
    @GetMapping(V1_GET_PRODUCT + "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") UUID id) throws ProductNotFoundException {
        return ok(productService.findById(id));
    }

    @PutMapping(V1_ACTIVE_PRODUCT + "/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable("id") UUID id, @RequestParam("product_status") ProductStatus status) throws ProductNotFoundException, ProductInvalidException {
        productService.changeStatusProduct(id, status);
        return ok("If product id exists in our database,status of product will changed");
    }
//
//    @PostMapping(V1_CREATE_PRODUCT)
//    public ResponseEntity<UUID> addNewProduct(@RequestBody ProductCreatorDto dto) throws ProductNotFoundException, ProductInvalidException {
//        return ok(productService.createProduct(dto));
//    }
//
//    @DeleteMapping(V1_DELETE_PRODUCT + "/{id}")
//    public ResponseEntity<String> disableProduct(@PathVariable("id") UUID id) {
//        this.productService.disableProduct(id);
//        return ResponseEntity.ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
//    }

}
