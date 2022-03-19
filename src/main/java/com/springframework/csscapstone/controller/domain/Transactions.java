package com.springframework.csscapstone.controller.domain;

import com.springframework.csscapstone.data.status.TransactionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    @CreatedDate
    private LocalDateTime createTransaction;

    @LastModifiedDate
    private LocalDateTime LastModifiedDate;

    @OneToMany(mappedBy = "transactions")
    private List<BillImage> billImage = new ArrayList<>();

    private double point;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @ManyToMany(mappedBy = "transactions")
    private Set<Account> account = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transactions)) return false;

        Transactions that = (Transactions) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + id +
                ", createTransaction=" + createTransaction +
                ", LastModifiedDate=" + LastModifiedDate +
                ", billImage=" + billImage +
                ", point=" + point +
                ", transactionStatus=" + transactionStatus +
                '}';
    }
}