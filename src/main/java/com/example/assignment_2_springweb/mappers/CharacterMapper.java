package com.example.assignment_2_springweb.mappers;

import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CharacterMapper {
    /**
     * This mapper is made to convert between character model to DTO object
     * TO hide certain infromation......
     */

    //Mapping character --> character DTO
    CharacterDTO characterToCharacterDTO(Characters characters);

    //Mapping character DTO --> character character
    Characters characterDTOToCharacter(CharacterDTO characterDTO);

}
