package com.springframework.csscapstone.controller.enterprise;


import com.springframework.csscapstone.controller.domain.Product;
import com.springframework.csscapstone.data.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Product.V2_GET_PRODUCT;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product (Enterprise)")
public class EnterpriseProductController {

    private final ProductRepository repositories;

    @GetMapping(V2_GET_PRODUCT)
    public ResponseEntity<List<Product>> getList() {
        return ok(null);
    }


}
