package com.example.face_recognition_backend.Controller;

import com.example.face_recognition_backend.Entity.Party;
import com.example.face_recognition_backend.Repository.PartyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/parties")
public class PartyController {

    private final PartyRepository partyRepository;

    public PartyController(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }


    // POST endpoint to create a new party
    @PostMapping
    public ResponseEntity<?> createParty(@RequestBody Party party) {
        try {
            Party savedParty = partyRepository.save(party);
            return new ResponseEntity<>(savedParty, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create party: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET endpoint to fetch all parties
    @GetMapping
    public ResponseEntity<List<String>> getAllParties() {
        List<Party> parties = partyRepository.findAll();
        List<String> partyList = new ArrayList<>();
        parties.forEach(p ->{
            partyList.add(p.getPartyName());
        });
        return new ResponseEntity<>(partyList, HttpStatus.OK);
    }
}