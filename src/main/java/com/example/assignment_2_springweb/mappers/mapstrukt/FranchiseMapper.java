package com.example.assignment_2_springweb.mappers.mapstrukt;

import com.example.assignment_2_springweb.model.Franchise;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.FranchiseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

    @Mapping(source = "movies", target = "movies", qualifiedByName = "moviesConverter")
    FranchiseDTO franchiseToDto(Franchise franchise);


    @Named("moviesConverter")
    default Set<Integer> moviesConverter(Set<Movie> movies) {

        Set<Integer> movieIds = movies.stream()
                .map(Movie::getId)
                .collect(Collectors.toSet());

        return movieIds;

    }
}
