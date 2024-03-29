package com.springframework.csscapstone.data.domain;


import com.springframework.csscapstone.data.status.AccountImageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.mapstruct.Named;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.springframework.csscapstone.data.status.AccountImageType.*;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    @OneToMany(mappedBy = "account")
    private List<AccountToken> tokens = new ArrayList<>();

    @OneToMany(mappedBy = "accountCreator")
    private List<Customer> customerCreatorList = new ArrayList<>();

    @OneToMany(mappedBy = "accountUpdater")
    private List<Customer> customerUpdaterList = new ArrayList<>();


    //search product by account
    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<Campaign> campaigns = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    @ToString.Exclude
    private List<Prize> prizes = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<RequestSellingProduct> requests = new ArrayList<>();

    @OneToMany(mappedBy = "transactionCreator")
    private List<Transactions> transactionsCreatedList = new ArrayList<>();

    @OneToMany(mappedBy = "transactionApprover")
    private List<Transactions> transactionApprovedList = new ArrayList<>();

    @OneToMany(mappedBy = "approver")
    private List<FeedBack> approverFeedback = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<FeedBack> creatorFeedback = new ArrayList<>();

    @LastModifiedDate
    private LocalDateTime createAccountDate;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany
    @JoinTable(name = "account_prize",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "prize_id"))
    private Set<Prize> awards = new HashSet<>();


    @Transient
    private Long totalQuantity;

    @Transient
    Map<String, Long> percentSoldByCategory = new HashMap<>();

    public Account(UUID id) {
        this.id = id;
    }

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
                .filter(image -> image.getType().equals(AVATAR))
                .max(Comparator.comparing(AccountImage::getCreateDate))
                .orElse(AccountImage.emptyInstance(AVATAR));
    }

    @Named("license")
    public AccountImage getLicense() {
        return this.images.stream()
                .filter(image -> image.getType().equals(AccountImageType.LICENSE))
                .max(Comparator.comparing(AccountImage::getCreateDate))
                .orElse(AccountImage.emptyInstance(LICENSE));
    }

    @Named("idCard")
    public AccountImage getIdCard() {
        return this.images.stream()
                .filter(image -> image.getType().equals(AccountImageType.ID_CARD))
                .max(Comparator.comparing(AccountImage::getCreateDate))
                .orElse(AccountImage.emptyInstance(ID_CARD));
    }

    public void setTotalQuantity(Long quantity) {
        this.totalQuantity = quantity;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
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
    //=================Token=======================
    public Account addRegistration(AccountToken registrationToken) {
        this.getTokens().add(registrationToken);
        registrationToken.setAccount(this);
        return this;
    }

    //=================Utils=========================
    //=================Campaign Prizes===============


    //=================Utils=========================
    //=================Transaction===============
    public Account addApproverTransaction(Transactions transactions) {
        transactions.setTransactionApprover(this);
        this.getTransactionApprovedList().add(transactions);
        return this;
    }

    public Account awardPrize(Prize prize) {
        this.getAwards().add(prize);
        prize.getRecipients().add(this);
        return this;
    }

    public Account addCreatorPrize(Prize prize) {
        this.getPrizes().add(prize);
        prize.setCreator(this);
        return this;
    }

    public Account addFeedBackCreate(FeedBack feedBack) {
        this.getCreatorFeedback().add(feedBack);
        feedBack.setCreator(this);
        return this;
    }

    public Account addFeedBackReply(FeedBack feedBack) {
        this.getApproverFeedback().add(feedBack);
        feedBack.setApprover(this);
        return this;
    }

}
