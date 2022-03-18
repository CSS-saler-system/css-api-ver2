package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findCategoryByCategoryName(String category);
}