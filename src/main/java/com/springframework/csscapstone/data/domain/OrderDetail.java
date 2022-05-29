package com.springframework.csscapstone.data.domain;

import lombok.Getter;
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

    private Double productPrice;

    private Double productPoint;

    private Long quantity;

    @Column(name = "total_point_product")
    private Double totalPointProduct;

    //total price: quantity * product.price
    @Column(name = "total_price_product")
    private Double totalPriceProduct;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
//    @ManyToOne
//    @JoinColumn(name = "selling_product_id")
//    private RequestSellingProduct sellingProduct;

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
                ", totalPriceProduct=" + totalPriceProduct +
//                ", product=" + product +
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
