package com.springframework.csscapstone.css_data.repositories;

import com.springframework.csscapstone.css_data.domain.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {
}