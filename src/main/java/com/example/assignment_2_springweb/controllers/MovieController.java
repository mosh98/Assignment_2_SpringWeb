package com.example.assignment_2_springweb.controllers;

import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.services.movie.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(path = "movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
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

    // TODO look up raw use of ResponseEntity
    @PostMapping
    public ResponseEntity add(@RequestBody Movie movie) {
        Movie mov = movieService.add(movie);
        URI location = URI.create("movies/" + mov.getId());
        return ResponseEntity.created(location).build();
        // return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{id}") // PUT: localhost:8080/api/v1/students/1
    public ResponseEntity<Movie> update(@RequestBody Movie movie, @PathVariable int id) {
        // Validates if body is correct
        if(id != movie.getId())
            return ResponseEntity.badRequest().build();
        movieService.update(movie);
        return ResponseEntity.noContent().build();
    }

}
