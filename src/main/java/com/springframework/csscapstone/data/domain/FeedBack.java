package com.springframework.csscapstone.data.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class FeedBack {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    @Lob
    private String feedReply;

    @Lob
    private String feedContent;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private Account approver;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Account creator;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime replyDate;

}
