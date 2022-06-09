package com.springframework.csscapstone.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CampaignImage {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name ="UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uniqueIdentifier")
    private UUID id;

    private String path;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CampaignImage that = (CampaignImage) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CampaignImage{" +
                "id=" + id +
                ", path='" + path + '\'' +
                '}';
    }
}
