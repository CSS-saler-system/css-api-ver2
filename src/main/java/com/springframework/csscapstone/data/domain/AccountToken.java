package com.springframework.csscapstone.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "account_token")
public class AccountToken {
    @Id
    @GeneratedValue(generator = "hibernate_generator")
    @GenericGenerator(name = "hibernate_generator",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    private String registrationToken;

    @LastModifiedDate
    private LocalDateTime updateTokenDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @ToString.Exclude
    private Account account;



}
