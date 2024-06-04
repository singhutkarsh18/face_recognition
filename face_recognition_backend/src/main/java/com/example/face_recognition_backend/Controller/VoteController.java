package com.example.face_recognition_backend.Controller;

import com.example.face_recognition_backend.Entity.Vote;
import com.example.face_recognition_backend.Repository.VoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
public class VoteController {


    private VoteRepository voteRepository;

    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    // POST endpoint to create a new vote
    @PostMapping
    public ResponseEntity<String> createVote(@RequestBody Vote vote) {
        try {
            Vote savedVote = voteRepository.save(vote);
            return new ResponseEntity<>("Vote created with id: " + savedVote.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create vote: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET endpoint to fetch all votes
    @GetMapping
    public ResponseEntity<List<Vote>> getAllVotes() {
        List<Vote> votes = voteRepository.findAll();
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }
}