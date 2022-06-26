package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.RequestStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
public class RequestSellingProduct {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    @CreatedDate
    @Column(name = "date_time_request")
    private LocalDateTime dateTimeRequest;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

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
                '}';
    }

//============== Utils ===================

    public RequestSellingProduct addProduct(Product product) {
        product.getRequestSellingProducts().add(this);
        this.setProduct(product);
        return this;
    }

    public RequestSellingProduct addAccount(Account account) {
        account.getRequests().add(this);
        this.setAccount(account);
        return this;
    }

}
