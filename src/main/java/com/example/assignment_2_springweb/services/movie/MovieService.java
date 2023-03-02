package com.example.assignment_2_springweb.services.movie;

import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.services.CrudService;

import java.util.Collection;

public interface MovieService extends CrudService<Movie, Integer> {
   // Collection<Movie> findAllByTitle(String title);
}
