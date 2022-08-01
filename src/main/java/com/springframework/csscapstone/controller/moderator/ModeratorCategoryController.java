package com.springframework.csscapstone.controller.moderator;

import com.springframework.csscapstone.data.status.CategoryStatus;
import com.springframework.csscapstone.payload.request_dto.admin.CategoryCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.admin.CategoryResDto;
import com.springframework.csscapstone.services.CategoryService;
import com.springframework.csscapstone.payload.request_dto.admin.CategorySearchReqDto;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryInvalidException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryNotFoundException;
import com.springframework.csscapstone.utils.request_utils.RequestUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Category.*;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Category (Moderator)")
@RestController
@RequiredArgsConstructor
public class ModeratorCategoryController {
    private final CategoryService services;

    @GetMapping(V4_GET_CATEGORY + "/{categoryId}")
    public ResponseEntity<CategoryResDto> getCategory(@PathVariable("categoryId") UUID id) throws CategoryNotFoundException, EntityNotFoundException {
        CategoryResDto category = this.services.findCategoryById(id);
        return ok(category);
    }

    @GetMapping(V4_LIST_CATEGORY)
    public ResponseEntity<?> listCategory(
            @RequestParam(value = "categoryName", required = false) String name,
            @RequestParam(value = "categoryStatus", required = false, defaultValue = "ACTIVE") CategoryStatus status
    ) {
        String _name = RequestUtils.getRequestParam(name);
        status = Objects.nonNull(status) ? status : CategoryStatus.ACTIVE;
        List<CategoryResDto> categories = this.services.findCategories(new CategorySearchReqDto(_name, status));
        return ok(categories);
    }

    @PostMapping(V4_CREATE_CATEGORY)
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryCreatorReqDto dto) throws CategoryInvalidException {
        UUID category = this.services.createCategory(dto);
        return ok(category);
    }

}
