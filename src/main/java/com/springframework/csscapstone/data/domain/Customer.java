package com.springframework.csscapstone.data.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    private String name;
    private String phone;
    private String address;
    private LocalDate dob;

    @ManyToOne
    @JoinColumn(name = "account_creator_id")
    private Account accountCreator;

    @ManyToOne
    @JoinColumn(name = "account_updater_id")
    private Account accountUpdater;

    @Lob
    private String description;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

}
