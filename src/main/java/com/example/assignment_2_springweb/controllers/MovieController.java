package com.example.assignment_2_springweb.controllers;

import com.example.assignment_2_springweb.mappers.mapstrukt.MovieMapper;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.MovieDTO;
import com.example.assignment_2_springweb.services.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

@RestController

@RequestMapping(path = "movies")
public class MovieController {

    private final MovieService movieService;

/*    @Autowired*/
    private final MovieMapper movieMapper;

    public MovieController(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper= movieMapper;
    }

    @Operation(summary = "Get all movies")
    @ApiResponse(responseCode = "200", description = "Found all movies", content = @Content)
    @ApiResponse(responseCode = "404", description = "No movies found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping // GET
    public ResponseEntity<Collection<Movie>> getAll() {
        Collection<Movie> movies = movieService.findAll();
        Collection<MovieDTO> moviesDTOS = new  ArrayList<>();

        for (Movie movie : movies) {
            moviesDTOS.add(movieMapper.movieToDto(movie));
        }





        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @Operation(summary = "Get a movie by id")
    @ApiResponse(responseCode = "200", description = "Found the movie", content = @Content)
    @ApiResponse(responseCode = "404", description = "No movie found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping("{id}") // GET:
    public ResponseEntity<Movie> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    // TODO look up raw use of ResponseEntity
    @Operation(summary = "Add a new movie")
    @ApiResponse(responseCode = "201", description = "Created a new movie", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PostMapping // POST
    public ResponseEntity add(@RequestBody Movie movie) {
        Movie mov = movieService.add(movie);
        URI location = URI.create("movies/" + mov.getId());
        return ResponseEntity.created(location).build();
        // return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a movie")
    @ApiResponse(responseCode = "204", description = "Updated the movie", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PutMapping("{id}") // PUT
    public ResponseEntity<Movie> update(@RequestBody Movie movie, @PathVariable int id) {
        // Validates if body is correct
        if(id != movie.getId())
            return ResponseEntity.badRequest().build();
        movieService.update(movie);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a movie")
    @ApiResponse(responseCode = "204", description = "Deleted the movie", content = @Content)
    @ApiResponse(responseCode = "404", description = "No movie found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @DeleteMapping("{id}") // DELETE
    public ResponseEntity<Movie> delete(@PathVariable int id) {
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
