package com.example.face_recognition_backend.Repository;

import com.example.face_recognition_backend.Entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<People,Long> {
}
