package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.PrizeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

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

    @ManyToMany(mappedBy = "prizes")
    private Set<Campaign> campaigns = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account creator;

    @ManyToMany(mappedBy = "awards")
    private Set<Account> recipients = new HashSet<>();

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

//===================== Utils ====================
    public Prize addImage(PrizeImage prizeImage) {
        this.getPrizeImages().add(prizeImage);
        prizeImage.setPrize(this);
        return this;
    }
}
