package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {
    @Transactional(readOnly = true)
    Optional<Role> findAllByName(String name);
}