package com.example.face_recognition_backend.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "people")
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String aadharNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String dateOfBirth;

    @Column(nullable = false)
    private String fatherName;

    @Column(nullable = false)
    private String city;

    // Constructors, getters, and setters

    public People() {
    }

    public People(String aadharNumber, String name, String dateOfBirth, String fatherName, String city) {
        this.aadharNumber = aadharNumber;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.fatherName = fatherName;
        this.city = city;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // toString() method

    @Override
    public String toString() {
        return "Person [id=" + id + ", aadharNumber=" + aadharNumber + ", name=" + name + ", dateOfBirth="
                + dateOfBirth + ", fatherName=" + fatherName + ", city=" + city + "]";
    }
}