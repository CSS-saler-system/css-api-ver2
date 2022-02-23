package com.springframework.csscapstone.css_data.repositories;

import com.springframework.csscapstone.css_data.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findCategoryByCategoryName(String category);
}