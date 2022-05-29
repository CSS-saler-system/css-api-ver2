package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.RequestSellingProduct;
import com.springframework.csscapstone.data.status.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
public interface RequestSellingProductRepository extends JpaRepository<RequestSellingProduct, UUID> {
    @Query(value = "SELECT r FROM RequestSellingProduct r WHERE r.requestStatus = :requestStatus",
    countQuery = "SELECT COUNT(r) FROM RequestSellingProduct r WHERE r.requestStatus = :requestStatus")
    Page<RequestSellingProduct> findAllByRequestStatus(@Param("requestStatus") RequestStatus requestStatus, Pageable pageable);

}