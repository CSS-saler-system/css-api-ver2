package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Prize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
public interface PrizeRepository extends JpaRepository<Prize, UUID>, JpaSpecificationExecutor<Prize> {
    @Query("SELECT p FROM Prize p ")
    Page<Prize> getAllPrize(Pageable pageable);

}