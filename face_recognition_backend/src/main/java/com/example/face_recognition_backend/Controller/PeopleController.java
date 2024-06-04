package com.example.face_recognition_backend.Controller;

import com.example.face_recognition_backend.Entity.People;
import com.example.face_recognition_backend.Repository.PeopleRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeopleController {

    private final PeopleRepository peopleRepository;

    public PeopleController(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @PostMapping("/people")
    public ResponseEntity<?> createPerson(@RequestBody People person) {
        try {
            People savedPerson = peopleRepository.save(person);
            return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create person: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}