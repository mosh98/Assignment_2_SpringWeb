package com.example.assignment_2_springweb.mappers;

import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;


public class CharacterMapperImpl implements CharacterMapper{
    /**
     * Class not fully configues yet
     */


    @Override
    public CharacterDTO characterToCharacterDTO(Characters characters) {

        /**
         * This method is made to convert from character model to DTO object
         * TO hide certain infromation......
         */
        CharacterDTO characterDTO = new CharacterDTO();

        characterDTO.setId(characters.getId());
        characterDTO.setFullName(characters.getFullName());
        characterDTO.setAlias(characters.getAlias());
        characterDTO.setGender(characters.getGender());
        characterDTO.setPicture(characters.getPicture());
        characterDTO.setMovie(characters.getMovie());

        return characterDTO;
    }

    @Override
    public Characters characterDTOToCharacter(CharacterDTO characterDTO) {
        /**
         * This method is made to convert from character DTO to character model
         */
        Characters characters = new Characters();

        characters.setId(characterDTO.getId());
        characters.setFullName(characterDTO.getFullName());
        characters.setAlias(characterDTO.getAlias());
        characters.setGender(characterDTO.getGender());
        characters.setPicture(characterDTO.getPicture());
        characters.setMovie(characterDTO.getMovie());

        return characters;
    }
}
