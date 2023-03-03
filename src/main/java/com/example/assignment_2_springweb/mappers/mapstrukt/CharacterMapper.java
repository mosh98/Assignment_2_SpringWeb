package com.example.assignment_2_springweb.mappers.mapstrukt;

import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.services.movie.MovieService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    @Mapping(source = "movie", target = "movie", qualifiedByName = "moviesConverter")
    CharacterDTO toCharacterDto(Characters characters);

    @Named("moviesConverter")
    default Set<Integer> moviesConverter(Set<Movie> movies) {

        Set<Integer> movieIds = movies.stream()
                .map(Movie::getId)
                .collect(Collectors.toSet());

        return movieIds;

    }

    @Mapping(source = "movie", target = "movie", qualifiedByName = "moviesConverterIntToMovies")
    Characters dtoToCharacters(CharacterDTO characterDTO, @Context MovieService movieService);

    @Named("moviesConverterIntToMovies")
    default Set<Movie> moviesConverterIntToMovies(Set<Integer> movies, @Context MovieService movieService) {
        Set<Movie> moviesz = new HashSet<>();

        for (Integer movieId : movies) {
            Movie movie = movieService.findById(movieId);
            moviesz.add(movie);
        }

        return moviesz;
    }
}
