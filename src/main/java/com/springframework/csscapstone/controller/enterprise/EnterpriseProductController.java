package com.springframework.csscapstone.controller.enterprise;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.ProductUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductCountOrderResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResDto;
import com.springframework.csscapstone.services.ProductService;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Product.*;
import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Product (Enterprise)")
@RestController
@RequiredArgsConstructor
public class EnterpriseProductController {
    private final ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
//    private final ProductCreatorConvertor productCreatorConvertor;

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
    @GetMapping(V2_PRODUCT_LIST + "/{enterpriseId}")
    public ResponseEntity<?> getListProductDto(
            @PathVariable("enterpriseId") UUID enterpriseId,
            @RequestParam(value = "productName", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "price", required = false) Double minPrice,
            @RequestParam(value = "price", required = false) Double maxPrice,
            @RequestParam(value = "status", required = false) ProductStatus productStatus,
            @RequestParam(value = "pointSale", required = false) Double minPointSale,
            @RequestParam(value = "pointSale", required = false) Double maxPointSale,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        PageImplResDto<ProductResDto> result = productService
                .findAllProductByIdEnterprise(
                        enterpriseId, name, brand, productStatus,
                        minPrice, maxPrice, minPointSale,
                        maxPointSale, pageNumber, pageSize);
        return ok(result);
    }

    @SneakyThrows
    @GetMapping(V2_PRODUCT_COUNT_LIST + "/{enterpriseId}")
    public ResponseEntity<?> getListTotalNumberOfProduct(
            @PathVariable("enterpriseId") UUID enterpriseId,

            @RequestParam(value = "startDate", required = false, defaultValue = "08/06/1999")
            @Valid @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
            String startDate,

            @RequestParam(value = "endDate", required = false, defaultValue = "08/06/2030")
            @Valid @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
            String endDate,

            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LOGGER.info("The start date {}", start);
        LOGGER.info("The end date {}", end);
        PageImplResDto<ProductCountOrderResDto> page =
                this.productService.getListProductWithCountOrder(enterpriseId, start, end, pageNumber, pageSize);
        return ok(page);
    }

    @GetMapping(V2_PRODUCT_GET + "/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") UUID idProduct) throws ProductNotFoundException {
        return ok(productService.findById(idProduct));
    }

    @PostMapping(value = V2_PRODUCT_CREATE, consumes = {"multipart/form-data"})
    public ResponseEntity<?> addNewProduct(
            @RequestPart(value = "typeImage") @Valid List<MultipartFile> typeImages,
            @RequestPart(value = "certificationImage") @Valid List<MultipartFile> certificationImages,
            @RequestPart(value = "product") String dto
    ) throws ProductInvalidException, AccountNotFoundException, IOException, ExecutionException, InterruptedException {
        ProductCreatorReqDto productCreatorReqDto = new ObjectMapper()
                .readValue(dto, ProductCreatorReqDto.class);
        if (isNull(productCreatorReqDto.getPointSale())) {
            throw new RuntimeException("The creator product is invalid!!!");
        }
        if (isNull(productCreatorReqDto.getName())) {
            throw new RuntimeException("The creator product is invalid!!!");
        }
        return ok(this.productService.createProduct(productCreatorReqDto, typeImages, certificationImages));
    }

    @PutMapping(
            value = V2_PRODUCT_UPDATE + "/{productId}",
            consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateProduct(
            @PathVariable("productId") UUID productId,
            @RequestPart String dto,
            @RequestPart(required = false) List<MultipartFile> normalType,
            @RequestPart(required = false) List<MultipartFile> certificationType)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        ProductUpdaterReqDto object = new ObjectMapper().readValue(dto, ProductUpdaterReqDto.class);
        return ok(this.productService.updateProductDto(productId, object, normalType, certificationType));
    }

    @DeleteMapping(V2_PRODUCT_DELETE + "/{productId}")
    public ResponseEntity<String> disableProduct(@PathVariable("productId") UUID id) {
        this.productService.disableProduct(id);
        return ResponseEntity.ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }
}
