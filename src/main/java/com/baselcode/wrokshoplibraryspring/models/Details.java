package com.baselcode.wrokshoplibraryspring.models;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Details {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String email;

    private String name;

    private LocalDate birthDate;

    public Details(int id, String email, String name, LocalDate birthDate) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Details() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
