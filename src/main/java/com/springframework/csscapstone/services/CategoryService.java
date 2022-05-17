package com.springframework.csscapstone.services;

import com.springframework.csscapstone.payload.basic.CategoryDto;
import com.springframework.csscapstone.payload.custom.creator_model.CategoryCreatorDto;
import com.springframework.csscapstone.payload.custom.return_model.category.CategoryReturnDto;
import com.springframework.csscapstone.payload.custom.search_model.CategorySearchDto;
import com.springframework.csscapstone.payload.custom.update_model.CategoryUpdaterDto;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryInvalidException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryNotFoundException;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryDto> findCategories(CategorySearchDto dto);

    CategoryReturnDto findCategoryById(UUID id) throws CategoryNotFoundException, EntityNotFoundException;

    UUID createCategory(CategoryCreatorDto dto) throws CategoryInvalidException;

    UUID updateCategory(CategoryUpdaterDto dto) throws CategoryInvalidException;

    void deleteCategory(UUID id) throws CategoryNotFoundException, EntityNotFoundException;

}
