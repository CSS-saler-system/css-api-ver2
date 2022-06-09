package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.PrizeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Accessors(chain = true)
@Table(name = "prize")
public class Prize {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    private String name;

    private Double price;

    private Long quantity;

    @Lob
    private String description;

    @Column(name = "status_prize")
    @Enumerated(EnumType.STRING)
    private PrizeStatus prizeStatus;

    @OneToMany(mappedBy = "prize")
    private List<PrizeImage> prizeImages = new ArrayList<>();

    @OneToMany(mappedBy = "prize")
    private List<CampaignPrize> campaignPrizes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account creator;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prize)) return false;

        Prize prize = (Prize) o;

        return Objects.equals(id, prize.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Prize{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }
}
