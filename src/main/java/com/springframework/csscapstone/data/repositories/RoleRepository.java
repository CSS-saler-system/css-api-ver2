package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, String> {
    @Transactional(readOnly = true)
    List<Role> findAllByName(String name);
}