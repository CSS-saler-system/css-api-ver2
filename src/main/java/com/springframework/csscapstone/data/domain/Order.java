package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
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
//
//    @ManyToMany(mappedBy = "orders")
//    private Set<Campaign> campaigns = new HashSet<>();

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

    public Order(Double totalPrice, Double totalPointSale, String customerName, String deliveryPhone, String deliveryAddress) {
        this.totalPrice = totalPrice;
        this.totalPointSale = totalPointSale;
        this.customerName = customerName;
        this.deliveryPhone = deliveryPhone;
        this.deliveryAddress = deliveryAddress;
    }

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

    // ======================== Order =====================
    public Order addOrderDetails(List<OrderDetail> orderDetails) {
        this.setOrderDetails(orderDetails);
        orderDetails.forEach(od -> od.setOrder(this));
        return this;
    }

    public Order addAccount(Account collaborator) {
        collaborator.getOrders().add(this);
        this.setAccount(collaborator);
        return this;
    }

    public Order addCustomer(Customer customer) {
        this.setCustomer(customer);
        customer.getOrders().add(this);
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", totalPrice=" + totalPrice +
                ", totalPointSale=" + totalPointSale +
                ", customerName='" + customerName + '\'' +
                ", deliveryPhone='" + deliveryPhone + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", status=" + status +
                '}';
    }
}
