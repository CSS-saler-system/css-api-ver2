package com.springframework.csscapstone.css_data.domain;

import com.springframework.csscapstone.css_data.status.ProductImageType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Entity
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProductImageType type;

    private String path;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}