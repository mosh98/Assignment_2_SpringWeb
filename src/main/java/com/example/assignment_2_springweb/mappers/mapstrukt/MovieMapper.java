package com.example.assignment_2_springweb.mappers.mapstrukt;

import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.Franchise;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.MovieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    //movie to dto
    @Mapping(source = "characters", target = "characters", qualifiedByName = "charactersConverter")
    @Mapping(source = "franchise", target = "franchise",qualifiedByName = "franchiseConverter")
    MovieDTO  movieToDto(Movie movie);

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



}
