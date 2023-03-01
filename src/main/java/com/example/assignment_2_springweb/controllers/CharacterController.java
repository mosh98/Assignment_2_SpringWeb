package com.example.assignment_2_springweb.controllers;


import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import com.example.assignment_2_springweb.mappers.CharacterMapper;

import com.example.assignment_2_springweb.services.character.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/character")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;
    private final CharacterMapper characterMapper;

    //get
    @GetMapping
    @ResponseStatus(value= HttpStatus.OK)
    public List<CharacterDTO> getAll(){

        List<Characters> charList = characterService.getAll();

        return charList.stream().map(characterMapper::toCharacterDto).collect(Collectors.toList());
    }

    //create
    @PostMapping
    @ResponseStatus(value= HttpStatus.CREATED)
    public CharacterDTO createCharacter(@RequestBody Characters character){



        return characterMapper.toCharacterDto(characterService.create(character));
    }

}
