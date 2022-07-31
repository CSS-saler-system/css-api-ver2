package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.AccountToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
public interface AccountTokenRepository extends JpaRepository<AccountToken, UUID> {
    //need handling stream to get single token
    @Query("SELECT at FROM AccountToken at " +
            "WHERE at.account.id = :accountId " +
            "ORDER BY at.updateTokenDate DESC ")
    List<AccountToken> getAccountTokenByAccount(@Param("accountId") UUID accountId);
}