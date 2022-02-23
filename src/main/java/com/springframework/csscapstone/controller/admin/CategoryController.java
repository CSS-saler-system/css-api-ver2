package com.springframework.csscapstone.controller.admin;

import com.springframework.csscapstone.css_business.services.CategoryService;
import com.springframework.csscapstone.css_business.model_dto.basic.CategoryDto;
import com.springframework.csscapstone.css_business.model_dto.custom.creator_model.CategoryCreatorDto;
import com.springframework.csscapstone.css_business.model_dto.custom.return_model.category.CategoryReturnDto;
import com.springframework.csscapstone.css_business.model_dto.custom.search_model.CategorySearchDto;
import com.springframework.csscapstone.css_data.status.CategoryStatus;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryInvalidException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryNotFoundException;
import com.springframework.csscapstone.utils.request_utils.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Category.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final CategoryService services;


    @GetMapping(V1_GET_CATEGORY + "{id}")
    public ResponseEntity<CategoryReturnDto> getCategory(@PathVariable("id") UUID id) throws CategoryNotFoundException, EntityNotFoundException {
        CategoryReturnDto category = this.services.findCategoryById(id);
        return ok(category);
    }

    @GetMapping(V1_LIST_CATEGORY)
    public ResponseEntity<List<CategoryDto>> listCategory(
            @RequestParam(value = "categoryName", required = false) String name,
            @RequestParam(value = "categoryStatus", required = false, defaultValue = "ACTIVE") CategoryStatus status
    ) {
        String _name = RequestUtils.getRequestParam(name);
        status = Objects.nonNull(status) ? status : CategoryStatus.ACTIVE;
        List<CategoryDto> categories = this.services.findCategories(new CategorySearchDto(_name, status));
        return ok(categories);
    }

    @PostMapping(V1_CREATE_CATEGORY)
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryCreatorDto dto) throws CategoryInvalidException {
        UUID category = this.services.createCategory(dto);
        return ok(category);
    }

//    @PutMapping(V1_UPDATE_CATEGORY)
//    public ResponseEntity<UUID> updateCategory(@RequestBody CategoryUpdaterDto dto) {
//
//    }
//
//    @DeleteMapping(V1_DELETE_CATEGORY)

}
