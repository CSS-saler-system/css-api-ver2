package com.springframework.csscapstone.controller.moderator;

import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResDto;
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
@Tag(name = "Product (Moderator)")
public class ModeratorProductController {
    private final ProductService productService;

    @GetMapping(V4_LIST_PRODUCT)
    public ResponseEntity<?> getListProductDto(
            @RequestParam(value = "productName", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "inStock", required = false) Long inStock,
            @RequestParam(value = "price", required = false) Double minPrice,
            @RequestParam(value = "price", required = false) Double maxPrice,
            @RequestParam(value = "pointSale", required = false) Double minPointSale,
            @RequestParam(value = "pointSale", required = false) Double maxPointSale,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {

        PageImplResDto<ProductResDto> result = productService.findAllProductByCollaborator(
                name, brand, inStock, minPrice, maxPrice,
                minPointSale, maxPointSale,
                pageNumber, pageSize);
        return ok(result);
    }
    @GetMapping(V4_GET_PRODUCT + "/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") UUID id) throws ProductNotFoundException {
        return ok(productService.findById(id));
    }

    @PutMapping(V4_ACTIVE_PRODUCT + "/{productId}")
    public ResponseEntity<String> updateProduct(
            @PathVariable("productId") UUID id, @RequestParam("product_status") ProductStatus status) throws ProductNotFoundException, ProductInvalidException {
        productService.changeStatusProduct(id, status);
        return ok("If product id exists in our database,status of product will changed");
    }

}
