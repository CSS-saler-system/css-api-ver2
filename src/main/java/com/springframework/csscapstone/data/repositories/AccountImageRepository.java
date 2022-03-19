package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.controller.domain.AccountImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountImageRepository extends JpaRepository<AccountImage, UUID> {
}