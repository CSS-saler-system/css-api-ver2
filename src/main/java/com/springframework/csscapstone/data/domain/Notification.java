package com.springframework.csscapstone.data.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Accessors(chain = true)
public class Notification {

    @Id
    @GeneratedValue(generator = "generator_id")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String title;
    private String message;

    @CreatedDate
    private LocalDateTime sendDate;
    private String pathImage;
    private String topic;

    @ManyToOne
    @JoinColumn(name = "account_token_id")
    private AccountToken accountToken;

    public Notification(
            String title, String message, String pathImage, String topic) {
        this.title = title;
        this.message = message;
        this.pathImage = pathImage;
        this.topic = topic;
    }
}
