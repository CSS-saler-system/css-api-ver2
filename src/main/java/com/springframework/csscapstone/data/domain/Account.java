package com.springframework.csscapstone.data.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

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
    private UUID id; //Long

    @Column(name = "name")
    private String name;

    @Column(name = "day_of_birth")
    private LocalDate dob;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "username")
    private String username;
    @Column(name="password")
    private String password;

    @Lob
    @Column(name = "address", nullable = false)
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

    @OneToMany(mappedBy = "account")
    @Where(clause = "ID_CARD")
    @ToString.Exclude
    private List<AccountImage> idCardAccountImage = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    @Where(clause = "AVATAR")
    @ToString.Exclude
    private List<AccountImage> avatar = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    @Where(clause = "type = 'LICENSE'")
    @ToString.Exclude
    private List<AccountImage> businessLicense = new ArrayList<>();

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

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<Prize> prizes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "account_transaction",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    @ToString.Exclude
    private Set<Transactions> transactions = new HashSet<>();
//
//    @ManyToMany
//    @JoinTable(name = "account_order",
//            joinColumns = @JoinColumn(name = "account_id"),
//            inverseJoinColumns = @JoinColumn(name = "order_id"))
//    @ToString.Exclude
//    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "account")
    private List<Order> orders = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "account_request",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "request_id"))
    @ToString.Exclude
    private Set<RequestSellingProduct> requests = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role = new Role("ROLE_3", "Collaborator");

    public Account(String name, LocalDate dob, String phone, String email, String address) {
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.address = address;
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
        return "data.Account{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", point=" + point +
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

}
