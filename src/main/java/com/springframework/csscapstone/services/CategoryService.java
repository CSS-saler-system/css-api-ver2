package com.springframework.csscapstone.services;

import com.springframework.csscapstone.payload.request_dto.admin.CategoryCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.admin.CategorySearchReqDto;
import com.springframework.csscapstone.payload.request_dto.admin.CategoryUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.admin.CategoryResDto;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryInvalidException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryNotFoundException;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryResDto> findCategories(CategorySearchReqDto dto);

    CategoryResDto findCategoryById(UUID id) throws CategoryNotFoundException, EntityNotFoundException;

    UUID createCategory(CategoryCreatorReqDto dto) throws CategoryInvalidException;

    UUID updateCategory(CategoryUpdaterReqDto dto) throws CategoryInvalidException;

    void deleteCategory(UUID id) throws CategoryNotFoundException, EntityNotFoundException;

}
