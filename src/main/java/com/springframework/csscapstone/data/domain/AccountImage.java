package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.AccountImageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Accessors(chain = true)
@Table(name = "account_image")
public class AccountImage {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    @Enumerated(EnumType.STRING)
    private AccountImageType type;

    private String path;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public AccountImage(AccountImageType type, String path) {
        this.type = type;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountImage)) return false;

        AccountImage that = (AccountImage) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AccountImage{" +
                "id=" + id +
                ", name='" + type + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
