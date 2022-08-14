package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.FeedbackStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
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

    @CreatedDate
    private LocalDateTime createDate;

//    @LastModifiedDate
    private LocalDateTime replyDate;
    private FeedbackStatus feedbackStatus;

}
