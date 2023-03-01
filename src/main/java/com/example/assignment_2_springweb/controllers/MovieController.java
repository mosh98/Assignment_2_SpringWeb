package com.example.assignment_2_springweb.controllers;

import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.services.movie.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("bar") // GET: localhost:8080/bar
    public ResponseEntity<String> bar() {
        return ResponseEntity.ok().body("Bar!");
    }

    @GetMapping("{id}") // GET: localhost:8080/api/v1/students/1
    public ResponseEntity<Movie> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Collection<Movie>> getAll() {
        Collection<Movie> movies = movieService.findAll();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

}
