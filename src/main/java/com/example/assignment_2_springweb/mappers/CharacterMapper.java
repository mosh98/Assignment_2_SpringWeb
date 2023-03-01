package com.example.assignment_2_springweb.mappers;

import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;



//@Mapper(componentModel = "spring")
public interface CharacterMapper {

    /**
     *Class not configured yet
     *  This mapper is made to convert between character model to DTO object
     * TO hide certain infromation......
     */

    //Mapping character --> character DTO
    CharacterDTO characterToCharacterDTO(Characters characters);

    //Mapping character DTO --> character character
    Characters characterDTOToCharacter(CharacterDTO characterDTO);

}
