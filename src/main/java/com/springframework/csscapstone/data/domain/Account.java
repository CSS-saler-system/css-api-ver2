package com.springframework.csscapstone.data.domain;


import com.springframework.csscapstone.data.status.AccountImageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.mapstruct.Named;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(nullable = false, columnDefinition = "uniqueIdentifier")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "day_of_birth")
    private LocalDate dob;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Lob
    @Column(name = "address")
    private String address;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "point")
    private Double point;

    @Column(name = "is_active")
    private Boolean isActive = Boolean.TRUE;

    @Column(name = "non_locked")
    private Boolean nonLock = Boolean.TRUE;

    @OneToMany(mappedBy = "account", fetch = LAZY)
    @ToString.Exclude
    private List<AccountImage> images = new ArrayList<>();

    @ElementCollection
    @Column(name = "tokens")
    private List<String> tokens = new ArrayList<>();

    @OneToMany(mappedBy = "accountCreator")
    private List<Customer> customerCreatorList = new ArrayList<>();

    @OneToMany(mappedBy = "accountUpdater")
    private List<Customer> customerUpdaterList = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<Category> categories = new ArrayList<>();

    //search product by account
    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<Campaign> campaigns = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<CampaignPrize> campaignsPrizes = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    @ToString.Exclude
    private List<Prize> prizes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "account_transaction",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    @ToString.Exclude
    private Set<Transactions> transactions = new HashSet<>();

    @OneToMany(mappedBy = "account")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<RequestSellingProduct> requests = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Account(String name, LocalDate dob, String phone, String email, String address) {
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }
    @Named("avatar")
    public AccountImage getAvatar() {
        return this.images.stream()
                .filter(image -> image.getType().equals(AccountImageType.AVATAR))
                .findFirst().orElse(new AccountImage());
    }
    @Named("license")
    public AccountImage getLicense() {
        return this.images.stream()
                .filter(image -> image.getType().equals(AccountImageType.LICENSE))
                .findFirst().orElse(new AccountImage());
    }
    @Named("id_card")
    public AccountImage getIdCard() {
        return this.images.stream()
                .filter(image -> image.getType().equals(AccountImageType.ID_CARD))
                .findFirst().orElse(new AccountImage());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        return getId() != null ? getId().equals(account.getId()) : account.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", gender=" + gender +
                ", point=" + point +
                ", isActive=" + isActive +
                ", nonLock=" + nonLock +
                ", images=" + images +
                ", tokens=" + tokens +
                ", role=" + role +
                '}';
    }

    //=================Utils=========================
    //=================Role=========================

    public Account addRole(Role role) {
        this.setRole(role);
        role.getAccount().add(this);
        return this;
    }
    //=================Utils=========================
    //=================Image=========================

    public Account addImage(AccountImage images) {
        images.setAccount(this);
        this.getImages().add(images);
        return this;
    }

    //=================Utils=========================
    //=================Request=======================


    //=================Utils=========================
    //=================Campaign Prizes===============
    public Account addCampaignPrizes(CampaignPrize campaignPrize) {
        this.getCampaignsPrizes().add(campaignPrize);
        campaignPrize.setAccount(this);
        return this;
    }


}
