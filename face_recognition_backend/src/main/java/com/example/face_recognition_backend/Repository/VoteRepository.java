package com.example.face_recognition_backend.Repository;


import com.example.face_recognition_backend.Entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
