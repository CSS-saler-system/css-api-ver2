package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.controller.domain.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {
}