package com.springframework.csscapstone.css_data.repositories;

import com.springframework.csscapstone.css_data.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}