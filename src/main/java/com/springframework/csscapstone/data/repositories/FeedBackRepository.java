package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.FeedBack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface FeedBackRepository extends JpaRepository<FeedBack, UUID>, JpaSpecificationExecutor<FeedBack> {

    Page<FeedBack> findAllByCreator_Id(UUID enterpriseId, Pageable pageable);

    List<FeedBack> findAllByCreator_Id(UUID collaboratorId);

}