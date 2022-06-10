package com.springframework.csscapstone.data.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Accessors(chain = true)
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