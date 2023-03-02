package com.example.assignment_2_springweb.services.franchise;

import com.example.assignment_2_springweb.model.Franchise;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.MovieDTO;
import com.example.assignment_2_springweb.services.CrudService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

public interface FranchiseService extends CrudService<Franchise, Integer> {
    Set<MovieDTO> findAllMovies(Integer id);
}
