package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.AccountImage;
import com.springframework.csscapstone.data.status.AccountImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
@Transactional(readOnly = true)
public interface AccountImageRepository extends JpaRepository<AccountImage, UUID> {
    @Query("FROM AccountImage a WHERE a.account.id = :accountId AND a.type = :typeImage")
    Optional<AccountImage> findByAccountAndType(@Param("accountId") UUID accountId, @Param("typeImage") AccountImageType type);
}