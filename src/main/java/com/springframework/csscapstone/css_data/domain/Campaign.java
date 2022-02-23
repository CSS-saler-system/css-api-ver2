package com.springframework.csscapstone.css_data.domain;

import com.springframework.csscapstone.css_data.status.CampaignStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "campaign")
@EntityListeners(AuditingEntityListener.class)
public class Campaign {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    private String name;
    //todo change to table
    private String image;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Lob
    private String campaignShortDescription;

    @Lob
    private String campaignDescription;

    //todo total solve product greater than this
    @Column(name = "kpi")
    private Long kpiSaleProduct;

    @Enumerated(EnumType.STRING)
    private CampaignStatus campaignStatus = CampaignStatus.PENDING;

    @ManyToMany
    @JoinTable(name = "campaign_order",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "campaign")
    private List<CampaignPrize> campaignPrizes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany
    @JoinTable(name = "campaign_product",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    public Campaign(String name, LocalDateTime startDate, LocalDateTime endDate, String campaignShortDescription,
                    String campaignDescription, Long kpiSaleProduct, CampaignStatus campaignStatus) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.campaignShortDescription = campaignShortDescription;
        this.campaignDescription = campaignDescription;
        this.kpiSaleProduct = kpiSaleProduct;
        this.campaignStatus = campaignStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Campaign)) return false;

        Campaign campaign = (Campaign) o;

        return Objects.equals(id, campaign.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", createDate=" + createDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", campaignShortDescription='" + campaignShortDescription + '\'' +
                ", campaignDescription='" + campaignDescription + '\'' +
                ", kpiSaleProduct=" + kpiSaleProduct +
                ", campaignStatus=" + campaignStatus +
                '}';
    }
}