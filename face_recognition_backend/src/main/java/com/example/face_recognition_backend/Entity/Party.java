package com.example.face_recognition_backend.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "parties")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String partyName;

    // Constructors, getters, and setters

    public Party() {
    }

    public Party(String partyName) {
        this.partyName = partyName;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    // toString() method

    @Override
    public String toString() {
        return "Party [id=" + id + ", partyName=" + partyName + "]";
    }
}