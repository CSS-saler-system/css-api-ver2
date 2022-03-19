package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.services.business.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Product.V3_LIST_PRODUCT;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product (collaborator)")
public class CollaboratorProductController {
    private final ProductService service;

    /**
     * This method just get product own of collaborator
     * @return
     */
    @GetMapping(V3_LIST_PRODUCT + "{id}")
    public ResponseEntity<?> getProducts(@PathVariable("id") UUID idAccount) throws AccountNotFoundException {
        return ok(service.findByIdAccount(idAccount));
    }

}
