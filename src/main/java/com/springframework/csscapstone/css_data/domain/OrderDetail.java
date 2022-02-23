package com.springframework.csscapstone.css_data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    @Column(name = "product_name")
    private String nameProduct;

    private Long quantity;

    //total price: quantity * product.price
    @Column(name = "total_price_line")
    private double orderLinePrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetail)) return false;

        OrderDetail that = (OrderDetail) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", nameProduct='" + nameProduct + '\'' +
                ", quantity=" + quantity +
                ", orderLinePrice=" + orderLinePrice +
                ", product=" + product +
                ", order=" + order +
                '}';
    }

    //==================Utils Method======================
    //Todo this is never use: Product no need save in orders-detail
    public OrderDetail addProductToOrderDetail(Product product) {
       return this;
    }

    public OrderDetail addOrderDetailDToOrder(Order order) {
        this.setOrder(order);
        order.getOrderDetails().add(this);
        return this;
    }
    //todo this is detach method util
    public OrderDetail removeOrderDetailsFromOrder() {
        order.getOrderDetails().remove(this);
        this.setOrder(null);
        return this;
    }

}
