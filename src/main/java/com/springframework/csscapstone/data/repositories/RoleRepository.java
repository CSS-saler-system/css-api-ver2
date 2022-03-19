package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.controller.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}