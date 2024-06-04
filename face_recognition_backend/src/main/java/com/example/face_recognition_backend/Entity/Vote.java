package com.example.face_recognition_backend.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String partyName;

    private String userName;
    // Constructors, getters, and setters

    public Vote() {
    }

    public Vote(String partyName, String userName) {
        this.partyName = partyName;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        return "Vote [id=" + id + ", partyName=" + partyName + "]";
    }
}
