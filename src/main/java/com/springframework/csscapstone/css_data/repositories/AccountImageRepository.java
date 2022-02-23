package com.springframework.csscapstone.css_data.repositories;

import com.springframework.csscapstone.css_data.domain.AccountImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountImageRepository extends JpaRepository<AccountImage, UUID> {
}