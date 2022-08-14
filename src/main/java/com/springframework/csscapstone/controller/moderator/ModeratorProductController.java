package com.springframework.csscapstone.controller.moderator;

import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.ProductForModeratorResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResDto;
import com.springframework.csscapstone.services.ProductService;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Product.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product (Moderator)")
public class ModeratorProductController {
    private final ProductService productService;

    @GetMapping(V4_LIST_PRODUCT + "/{enterpriseId}")
    public ResponseEntity<?> getListProductDto(
            @PathVariable("enterpriseId") UUID enterpriseId,
            @RequestParam(value = "productName", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "status", required = false) ProductStatus productStatus,
            @RequestParam(value = "price", required = false) Double minPrice,
            @RequestParam(value = "price", required = false) Double maxPrice,
            @RequestParam(value = "pointSale", required = false) Double minPointSale,
            @RequestParam(value = "pointSale", required = false) Double maxPointSale,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {

        PageImplResDto<ProductResDto> result = productService.findAllProductByIdEnterprise(
                enterpriseId, name, brand, productStatus, minPrice,
                maxPrice, minPointSale, maxPointSale, pageNumber, pageSize);
        return ok(result);
    }
    @GetMapping(V4_GET_PRODUCT + "/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") UUID id) throws ProductNotFoundException {
        return ok(productService.findById(id));
    }

    @PutMapping(V4_ACTIVE_PRODUCT + "/{productId}")
    public ResponseEntity<String> activeProduct(
            @PathVariable("productId") UUID productId) throws ProductNotFoundException, ProductInvalidException {
        productService.changeStatusProduct(productId, ProductStatus.ACTIVE);
        return ok("If product id exists in our database,status of product will changed");
    }

    @PutMapping(V4_DISABLE_PRODUCT +"/{productId}")
    public ResponseEntity<?> disableProduct(@PathVariable("productId") UUID productId) {
        productService.changeStatusProduct(productId, ProductStatus.DISABLED);
        return ok("If product id exists in our database,status of product will changed");
    }

    @GetMapping(V4_LIST_PRODUCT)
    public ResponseEntity<?> getAllProduct(
            @RequestParam(value = "productName", required = false) String name,
            @RequestParam(value = "enterpriseName", required = false) String enterpriseName,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        PageImplResDto<ProductForModeratorResDto> result = this.productService
                .pageAllForProductForModerator(name, enterpriseName, brand, pageNumber, pageSize);
        return ok(result);
    }

}
