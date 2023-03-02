package com.example.assignment_2_springweb.mappers;
import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CharacterMapperImpl implements CharacterMapper{

    @Override
    public CharacterDTO toCharacterDto(Characters character) {

        CharacterDTO dto = new CharacterDTO();

        dto.setId(character.getId());
        dto.setFullName(character.getFullName());
        dto.setAlias(character.getAlias());
        dto.setGender(character.getGender());
        dto.setPicture(character.getPicture());

        if (character.getMovie() != null) {
            Set<Integer> moviesId = character.getMovie().stream()
                    .map(Movie::getId).collect(Collectors.toSet());
            dto.setMovie(moviesId);
        }
        return dto;
    }

    @Override
    public Characters toCharacters(CharacterDTO dto) {
        Characters characters = new Characters();

        characters.setId(dto.getId());
        characters.setFullName(dto.getFullName());
        characters.setAlias(dto.getAlias());
        characters.setGender(dto.getGender());
        characters.setPicture(dto.getPicture());

        return characters;
    }


}
