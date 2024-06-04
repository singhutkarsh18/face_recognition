package com.example.face_recognition_backend.Repository;

import com.example.face_recognition_backend.Entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party,Long> {
}
