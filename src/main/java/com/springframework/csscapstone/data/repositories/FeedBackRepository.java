package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FeedBackRepository extends JpaRepository<FeedBack, UUID>, JpaSpecificationExecutor<FeedBack> {
}