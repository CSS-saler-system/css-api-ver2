package com.springframework.csscapstone.data.domain;

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

    private Double totalPrice;
    @Column(name = "total_point_award")
    private Double totalPointSale;

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

//    @ManyToMany(mappedBy = "orders")
//    private Set<Account> accounts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        return getId() != null ? getId().equals(order.getId()) : order.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
