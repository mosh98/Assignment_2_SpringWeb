package com.example.assignment_2_springweb.mappers.mapstrukt;

import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CharMapStruct {

    //@Mapping(expression = "java(characters.getMovies().stream().mapToInt(Movie::getId).boxed().collect(java.util.stream.Collectors.toList()))", target = "movie")
    //CharacterDTO toCharacterDto(Characters characters);

   // @Mapping()
    //Characters dtoToCharacters(CharacterDTO dto);
}
