package com.example.assignment_2_springweb.mappers.mapstrukt;

import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.Franchise;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.MovieDTO;
import com.example.assignment_2_springweb.services.character.CharacterService;
import com.example.assignment_2_springweb.services.franchise.FranchiseService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    //movie to dto
    @Mapping(source = "characters", target = "characters", qualifiedByName = "charactersConverter")
    @Mapping(source = "franchise", target = "franchise", qualifiedByName = "franchiseConverter")
    MovieDTO movieToDto(Movie movie);

    @Named("charactersConverter")
    default Set<Integer> charactersConverter(Set<Characters> characters) {

        Set<Integer> characterIds = characters.stream()
                .map(Characters::getId)
                .collect(Collectors.toSet());

        return characterIds;
    }

    @Named("franchiseConverter")
    default Integer franchiseConverter(Franchise franchise) {
        return franchise.getId();
    }

    default int map(Franchise value) {
        return value.getId();
    }

    @Mapping(source = "franchise", target = "franchise", qualifiedByName = "mapIntToFranchise")
    @Mapping(source = "characters", target = "characters", qualifiedByName = "charactersConverterIntToCharacters")
    Movie dtoTOMovie(MovieDTO movieDTO, @Context CharacterService characterService, @Context FranchiseService franchiseService) ;

    @Named("charactersConverterIntToCharacters")
    default Set<Characters> charactersConverterIntToCharacters(Set<Integer> characters, @Context CharacterService characterService) {
        Set<Characters> charactersSet = new HashSet<>();

        for (Integer characterID : characters) {
            Characters character = characterService.getById(characterID);
            charactersSet.add(character);
        }

        return charactersSet;
    }

    @Named("mapIntToFranchise")
    default Franchise mapFranchise(int franchiseId, @Context FranchiseService franchiseServiceImp) {
        Franchise franchise = franchiseServiceImp.findById(franchiseId);
        return franchise;
    }

}
