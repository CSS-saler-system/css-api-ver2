package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.payload.basic.CategoryBasicDto;
import com.springframework.csscapstone.payload.response_dto.admin.CategoryResDto;
import com.springframework.csscapstone.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Category.V2_CATEGORY_LIST;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Category (Enterprise)")
public class EnterpriseCategoryController {

    private final CategoryService categoryService;


//    @GetMapping(V2_CATEGORY_LIST + "/{enterpriseId}")
    @GetMapping(V2_CATEGORY_LIST)
    public ResponseEntity<?> getAllCategory() {
        List<CategoryBasicDto> result = categoryService.getCategoryBasicResDto();
        return ok(result);
    }



}
