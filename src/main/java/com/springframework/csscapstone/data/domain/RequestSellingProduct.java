package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.RequestStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class RequestSellingProduct {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    @CreatedDate
    @Column(name = "date_time_request")
    private LocalDateTime dateTimeRequest;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @Column(name = "quantity_product")
    private Long quantityProduct;

//    @OneToMany(mappedBy = "sellingProduct")
//    private List<OrderDetail> orderDetail = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany(mappedBy = "requests")
    private Set<Account> accounts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestSellingProduct)) return false;

        RequestSellingProduct that = (RequestSellingProduct) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RequestSellingProduct{" +
                "id=" + id +
                ", dateTimeRequest=" + dateTimeRequest +
                ", requestStatus=" + requestStatus +
                ", quantityProduct=" + quantityProduct +
                '}';
    }
}
