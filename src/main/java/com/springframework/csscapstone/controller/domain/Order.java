package com.springframework.csscapstone.controller.domain;

import com.springframework.csscapstone.data.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    @CreatedDate
    private LocalDateTime createDate;

    private double totalPrice;
    @Column(name = "total_point_award")
    private double totalPointSale;

    private String customerName;

    @Column(name = "delivery_phone", nullable = false)
    private String deliveryPhone;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToMany(mappedBy = "orders")
    private Set<Campaign> campaigns = new HashSet<>();

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @ManyToMany(mappedBy = "orders")
    private Set<Account> accounts = new HashSet<>();

}
