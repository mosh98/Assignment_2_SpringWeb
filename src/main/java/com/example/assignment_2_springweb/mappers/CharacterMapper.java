package com.example.assignment_2_springweb.mappers;

import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;

public interface CharacterMapper {

    CharacterDTO toCharacterDto(Characters character);

    Characters toCharacters(CharacterDTO dto);
}
