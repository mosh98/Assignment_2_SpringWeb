package com.example.assignment_2_springweb.services.movie;

import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.services.CrudService;

import java.util.Collection;
import java.util.Set;

public interface MovieService extends CrudService<Movie, Integer> {
    Set<Characters> findAllCharacters(Integer movieId);

    Set<Characters> updateCharactersInMovie(Integer id, Set<Integer> characterIds);


}
