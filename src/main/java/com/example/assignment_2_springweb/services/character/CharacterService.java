package com.example.assignment_2_springweb.services.character;

import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;

import java.util.List;

public interface CharacterService {
    List<Characters> getAll();

    Characters create(Characters character);

    CharacterDTO updateCharacter(int id, CharacterDTO characterDTO);

    Characters getById(int id);

    String delete(int id);
}
