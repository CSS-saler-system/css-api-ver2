package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.AccountImageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
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
    @JoinColumn(name = "accunt_id")
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountImage)) return false;

        AccountImage that = (AccountImage) o;

        return id != null ? id.equals(that.id) : that.id == null;
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
