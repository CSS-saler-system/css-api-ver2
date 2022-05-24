package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.ProductImageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProductImageType type;
    //
    private String path;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductImage(ProductImageType type, String path) {
        this.type = type;
        this.path = path;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", type=" + type +
                ", path='" + path + '\'' +
                '}';
    }
}