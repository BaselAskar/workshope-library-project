package com.baselcode.wrokshoplibraryspring.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int appUserId;

    @Column(unique = true)
    private String username;

    private String password;

    private LocalDate regDate;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "fk_details_id")
    private Details userDetails;

    @OneToMany(
            cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "borrower"
    )
    private List<BookLoan> loans;

    public AppUser(int appUserId, String username, String password, LocalDate regDate, Details userDetails) {
        this.appUserId = appUserId;
        this.username = username;
        this.password = password;
        this.regDate = regDate;
        this.userDetails = userDetails;
    }

    public AppUser() {
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public Details getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(Details userDetails) {
        this.userDetails = userDetails;
    }

    public List<BookLoan> getLoans() {
        if(loans == null) return new ArrayList<>();
        return loans;
    }

    public void setLoans(List<BookLoan> loans) {
        if(loans == null) loans = new ArrayList<>();

        if(loans.isEmpty()){
            if(this.loans != null){
                this.loans.forEach(l -> l.setBorrower(null));
            }else {
                loans.forEach(l -> l.setBorrower(this));
            }
        }
        this.loans = loans;
    }
}
