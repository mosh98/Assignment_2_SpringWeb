package com.example.assignment_2_springweb.mappers.mapstrukt;

import com.example.assignment_2_springweb.model.Franchise;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.FranchiseDTO;
import com.example.assignment_2_springweb.services.movie.MovieService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

    @Mapping(source = "movies", target = "movies", qualifiedByName = "moviesConverter")
    FranchiseDTO franchiseToDto(Franchise franchise);

    @Mapping(source = "movies", target = "movies", qualifiedByName = "moviesConverterIntToMovies")
    Franchise dtoToFranchise(FranchiseDTO franchiseDTO, @Context MovieService movieService);

    @Named("moviesConverter")
    default Set<Integer> moviesConverter(Set<Movie> movies) {

        Set<Integer> movieIds = movies.stream()
                .map(Movie::getId)
                .collect(Collectors.toSet());

        return movieIds;

    }

    @Named("moviesConverterIntToMovies")
    default Set<Movie> moviesConverterIntToMovies(Set<Integer> movies, @Context MovieService movieService) {

        if(movies == null) return new HashSet<>();

        Set<Movie> moviesz = new HashSet<>();

        for (Integer movieId : movies) {
            Movie movie = movieService.findById(movieId);
            moviesz.add(movie);
        }

        return moviesz;
    }

}
