package com.springframework.csscapstone.data.domain;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "account_token")
public class AccountToken {
    @Id
    @GeneratedValue(generator = "hibernate_generator")
    @GenericGenerator(name = "hibernate_generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    private String registrationToken;

    @LastModifiedDate
    private LocalDateTime updateTokenDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @ToString.Exclude
    private Account account;

    @OneToMany(mappedBy = "accountToken")
    private List<Notification> notificationList = new ArrayList<>();

    public AccountToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccountToken that = (AccountToken) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
