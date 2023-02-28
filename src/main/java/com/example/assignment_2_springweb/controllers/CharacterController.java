package com.example.assignment_2_springweb.controllers;

import com.example.assignment_2_springweb.mappers.CharacterMapper;
import com.example.assignment_2_springweb.mappers.CharacterMapperImpl;
import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import com.example.assignment_2_springweb.repositories.CharacterRepository;
import com.example.assignment_2_springweb.services.character.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/character")

public class CharacterController {
    public final CharacterService characterService;

    //Create
    //create new character
    @Autowired
    public CharacterController(CharacterService characterService){
        this.characterService = characterService;

    }
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(characterService.findAll());
    }

    //TODO: OBS!! Add method does not WORK
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Characters characters){
        Characters savedCharacter = characterService.add(characters);
        //CharacterDTO characterDTO = characterMapper.characterToCharacterDTO(savedCharacter);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //update
    //read
    //delete

}
