package com.springframework.csscapstone.controller.enterprise;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.ProductUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductCountOrderResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResDto;
import com.springframework.csscapstone.services.ProductService;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.converter_mapper.ProductCreatorConvertor;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Product.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Product (Enterprise)")
@RestController
@RequiredArgsConstructor
public class EnterpriseProductController {
    private final ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final ProductCreatorConvertor productCreatorConvertor;

    @GetMapping(V2_PRODUCT_LIST + "/{idEnterprise}")
    public ResponseEntity<?> getListProductDto(
            @PathVariable("idEnterprise") UUID idEnterprise,
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
        PageImplResDto<ProductResDto> result = productService
                .findAllProductByIdEnterprise(
                        idEnterprise, name, brand, inStock, minPrice, maxPrice,
                        minPointSale, maxPointSale, productStatus, pageNumber, pageSize);
        return ok(result);
    }

    @SneakyThrows
    @GetMapping(V2_PRODUCT_COUNT_LIST + "/{idEnterprise}")
    public ResponseEntity<?> getListTotalNumberOfProduct(
            @PathVariable("enterprise_id") UUID enterpriseId,

            @RequestParam(value = "startDate", required = false, defaultValue = "08-06-1999")
            @Valid @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$")
            String startDate,

            @RequestParam(value = "endDate", required = false, defaultValue = "08-06-2030")
            @Valid @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$")
            String endDate,

            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LOGGER.info("The start date {}", start);
        LOGGER.info("The end date {}", end);
        PageImplResDto<ProductCountOrderResDto> page =
                this.productService.getListProductWithCountOrder(enterpriseId, start, end, pageNumber, pageSize);
        return ok(page);

    }

    @GetMapping(V2_PRODUCT_GET + "/{idProduct}")
    public ResponseEntity<?> getProductById(@PathVariable("idProduct") UUID idProduct) throws ProductNotFoundException {
        return ok(productService.findById(idProduct));
    }

    @PostMapping(value = V2_PRODUCT_CREATE, consumes = {"multipart/form-data"})
    public ResponseEntity<?> addNewProduct(
            @RequestPart(value = "type_image") @Valid MultipartFile[] typeImages,
            @RequestPart(value = "certification_image") @Valid MultipartFile[] certificationImages,
            @RequestPart(value = "product") String dto
    ) throws ProductInvalidException, AccountNotFoundException, IOException {
        List<MultipartFile> types = Stream.of(typeImages).collect(Collectors.toList());
        List<MultipartFile> certifications = Stream.of(certificationImages).collect(Collectors.toList());
        ProductCreatorReqDto productCreatorReqDto = this.productCreatorConvertor.convert(dto);
        return ok(this.productService.createProduct(productCreatorReqDto, types, certifications));
    }

    @PutMapping(value = V2_PRODUCT_UPDATE, consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateProduct(
            @RequestPart String dto, @RequestPart List<MultipartFile> normalType,
            @RequestPart List<MultipartFile> certificationType) throws JsonProcessingException {
        ProductUpdaterReqDto object = new ObjectMapper().readValue(dto, ProductUpdaterReqDto.class);
        return ok(this.productService.updateProductDto(object, normalType, certificationType));
    }

    @DeleteMapping(V2_PRODUCT_DELETE + "/{idProduct}")
    public ResponseEntity<String> disableProduct(@PathVariable("idProduct") UUID id) {
        this.productService.disableProduct(id);
        return ResponseEntity.ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }
}
