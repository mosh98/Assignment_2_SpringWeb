package com.example.assignment_2_springweb.services.franchise;

import com.example.assignment_2_springweb.model.Franchise;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import com.example.assignment_2_springweb.model.dtos.MovieDTO;
import com.example.assignment_2_springweb.services.CrudService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface FranchiseService extends CrudService<Franchise, Integer> {
    Set<MovieDTO> findAllMovies(Integer id);

    Set<CharacterDTO> getAllCharactersFromFranchise(Integer id);

    Franchise updateMovies(int franchiseId, List<Integer> movieIds);




}
