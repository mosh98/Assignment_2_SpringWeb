package com.example.assignment_2_springweb.controllers;

import com.example.assignment_2_springweb.mappers.mapstrukt.MovieMapper;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import com.example.assignment_2_springweb.model.dtos.MovieDTO;
import com.example.assignment_2_springweb.services.character.CharacterService;
import com.example.assignment_2_springweb.services.franchise.FranchiseService;
import com.example.assignment_2_springweb.services.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;
    private final CharacterService characterService;
    private final FranchiseService franchiseService;

   /* public MovieController(MovieService movieService, MovieMapper movieMapper, CharacterService characterService, FranchiseService franchiseService) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        this.characterService = characterService;
        this.franchiseService = franchiseService;
    }
*/
    @Operation(summary = "Get all movies")
    @ApiResponse(responseCode = "200", description = "Found all movies", content = @Content)
    @ApiResponse(responseCode = "404", description = "No movies found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping // GET
    public ResponseEntity<Collection<MovieDTO>> getAll() {
        Collection<Movie> movies = movieService.findAll();
        Collection<MovieDTO> movieDTOs = movies.stream()
                .map(movieMapper::movieToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movieDTOs);
    }

    @Operation(summary = "Get a movie by id")
    @ApiResponse(responseCode = "200", description = "Found the movie", content = @Content)
    @ApiResponse(responseCode = "404", description = "No movie found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping("{id}") // GET:
    public ResponseEntity<MovieDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(movieMapper.movieToDto(movieService.findById(id)));
    }

    @Operation(summary = "Add a new movie")
    @ApiResponse(responseCode = "201", description = "Created a new movie", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PostMapping // POST
    public ResponseEntity add(@RequestBody MovieDTO newMovieDTO) {
        Movie movie = movieMapper.dtoTOMovie(newMovieDTO, characterService, franchiseService);
        Movie movieSave = movieService.add(movie);
       // MovieDTO returnDTO = movieMapper.movieToDto(movieSave);
        //URI location = URI.create("movies/" + mov.getId());
      //  return ResponseEntity.created(location).build();
         return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a movie")
    @ApiResponse(responseCode = "204", description = "Updated the movie", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PutMapping("{id}") // PUT
    @ResponseStatus(value = HttpStatus.OK)
    public MovieDTO update(@RequestBody MovieDTO movieDTO, @PathVariable int id) {


        Movie movie = movieService.findById(id);

        // Validates if id is correct
        if(id != movie.getId()){
            throw new IllegalArgumentException("Movie ID must match the ID in the URL");
        }
        //movieMapper.movieToDto(movie);
        Movie mov = movieMapper.dtoTOMovie(movieDTO, characterService, franchiseService);

        //return
        return movieMapper.movieToDto(movieService.update(mov));
    }
    @Operation(summary = "Update characters in movie")
    @ApiResponse(responseCode = "204", description = "Updated the movie", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PutMapping("{id}/characters") // PUT
    public Set<MovieDTO> updateCharactersInMovie(@RequestBody Set<Integer> charactersId, @PathVariable int id) {

        return null;
    }

    @Operation(summary = "Delete a movie")
    @ApiResponse(responseCode = "204", description = "Deleted the movie", content = @Content)
    @ApiResponse(responseCode = "404", description = "No movie found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @DeleteMapping("{id}") // DELETE
    public ResponseEntity<MovieDTO> delete(@PathVariable int id) {
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
