package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.TransactionStatus;
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
@Table(name = "transactions")
@EntityListeners(AuditingEntityListener.class)
public class Transactions {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;
    @CreatedDate
    private LocalDateTime createTransactionDate;
    @LastModifiedDate
    private LocalDateTime LastModifiedDate;

    @OneToMany(mappedBy = "transactions")
    private List<BillImage> billImage = new ArrayList<>();

    private double point;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;


    @ManyToOne
    @JoinColumn(name = "account_creator_id")
    private Account transactionCreator;

    @ManyToOne
    @JoinColumn(name = "account_approver_id")
    private Account transactionApprover;

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
                ", createTransaction=" + createTransactionDate +
                ", LastModifiedDate=" + LastModifiedDate +
                ", billImage=" + billImage +
                ", point=" + point +
                ", transactionStatus=" + transactionStatus +
                '}';
    }

//    ======================= utils =====================

    public Transactions addCreators(Account transactionCreator) {
        this.setTransactionCreator(transactionCreator);
        transactionCreator.getTransactionsCreatedList().add(this);
        return this;
    }

    public Transactions addApprover(Account transactionApprover) {
        this.setTransactionApprover(transactionApprover);
        transactionApprover.getTransactionApprovedList().add(this);
        return this;
    }

//    ======================= utils images =====================
    public Transactions addImages(BillImage image) {
        this.billImage.add(image);
        image.setTransactions(this);
        return this;

    }
}