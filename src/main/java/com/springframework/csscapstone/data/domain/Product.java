package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.ProductImageType;
import com.springframework.csscapstone.data.status.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.mapstruct.Named;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_brand")
    private String brand;
    private String shortDescription;
    @Lob
    private String description;
//    private Long quantityInStock;
    private Double price;
    private Double pointSale;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> image = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<RequestSellingProduct> requestSellingProducts = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany(mappedBy = "products")
    private Set<Campaign> campaign = new HashSet<>();

    @CreatedDate
    private LocalDateTime createDate;

    public Product(String name, String brand,
                   String shortDescription,
                   String description, Double price,
                   Double pointSale, ProductStatus productStatus) {
        this.name = name;
        this.brand = brand;
        this.shortDescription = shortDescription;
        this.description = description;
        this.price = price;
        this.pointSale = pointSale;
        this.productStatus = productStatus;
    }

    @Named("normal")
    public List<ProductImage> getNormal() {
        return this.image
                .stream()
                .filter(image -> image.getType().equals(ProductImageType.NORMAL))
                .collect(Collectors.toList());
    }

    @Named("certification")
    public List<ProductImage> getCertification() {
        return this.image
                .stream()
                .filter(image -> image.getType().equals(ProductImageType.CERTIFICATION))
                .collect(Collectors.toList());
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
//                ", quantityInStock=" + quantityInStock +
                ", price=" + price +
                ", pointSale=" + pointSale +
                ", productStatus=" + productStatus +
                '}';
    }

    //=================Utils=========================
    //=================Account=========================
    public Product addAccount(Account account) {
        account.getProducts().add(this);
        this.setAccount(account);
        return this;
    }

    //=================Utils=========================
    //=================Category=========================
    public Product addCategory(Category category) {
        category.getProducts().add(this);
        this.setCategory(category);
        return this;
    }

    //=================Utils=========================
    //=================Category=========================
    public Product addProductImage(ProductImage... image) {
        this.image.addAll(Arrays.asList(image));
        Arrays
                .stream(image)
                .forEach(x -> x.setProduct(this));
        return this;
    }

}