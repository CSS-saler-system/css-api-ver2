package com.springframework.csscapstone.data.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "bill_image")
public class BillImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    private String path;

    @ManyToOne
    @JoinColumn(name = "transactions_id")
    private Transactions transactions;
}