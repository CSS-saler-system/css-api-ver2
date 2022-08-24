package com.springframework.csscapstone.data.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Customer {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
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

    @CreatedDate
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

}
