package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductCountOrderResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResDto;
import com.springframework.csscapstone.services.ProductService;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Product.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product (Collaborator)")
public class CollaboratorProductController {
    private final ProductService productService;

    /**
     * todo Controller get list Product by enterprise id
     * @param enterpriseId
     * @param name
     * @param brand
     * @param minPrice
     * @param maxPrice
     * @param minPointSale
     * @param maxPointSale
     * @param pageNumber
     * @param pageSize
     * @return
     */

    @SneakyThrows
    @GetMapping(V3_PRODUCT_List_By_ENTERPRISE + "/{enterpriseId}")
    public ResponseEntity<?> getListProductDto(
            @PathVariable("enterpriseId") UUID enterpriseId,
            @RequestParam(value = "productName", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "price", required = false) Double minPrice,
            @RequestParam(value = "price", required = false) Double maxPrice,
            @RequestParam(value = "pointSale", required = false) Double minPointSale,
            @RequestParam(value = "pointSale", required = false) Double maxPointSale,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        PageImplResDto<ProductResDto> result = productService
                .findAllProductByIdEnterprise(
                        enterpriseId, name, brand,
                        minPrice, maxPrice, minPointSale,
                        maxPointSale, pageNumber, pageSize);
        return ok(result);
    }

    @GetMapping(V3_LIST_PRODUCT)
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
    @GetMapping(V3_GET_PRODUCT + "/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") UUID id) throws ProductNotFoundException {
        return ok(productService.findById(id));
    }
}
