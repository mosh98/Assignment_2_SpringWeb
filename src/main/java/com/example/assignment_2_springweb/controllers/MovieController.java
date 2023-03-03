package com.example.assignment_2_springweb.controllers;

import com.example.assignment_2_springweb.mappers.mapstrukt.CharacterMapper;
import com.example.assignment_2_springweb.mappers.mapstrukt.MovieMapper;
import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import com.example.assignment_2_springweb.model.dtos.MovieDTO;
import com.example.assignment_2_springweb.services.character.CharacterService;
import com.example.assignment_2_springweb.services.franchise.FranchiseService;
import com.example.assignment_2_springweb.services.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
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

    private final CharacterMapper characterMapper;

    @Operation(summary = "Get all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all movies",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MovieDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No movies found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping // GET
    public ResponseEntity<Collection<MovieDTO>> getAll() {
        Collection<Movie> movies = movieService.findAll();
        Collection<MovieDTO> movieDTOs = movies.stream()
                .map(movieMapper::movieToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(movieDTOs);
    }

    @Operation(summary = "Get a movie by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the movie",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieDTO.class))),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("{id}") // GET:
    public ResponseEntity<MovieDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(movieMapper.movieToDto(movieService.findById(id)));
    }

    @Operation(summary = "Add a new movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created a new movie",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping // POST
    public ResponseEntity add(@RequestBody MovieDTO newMovieDTO) {
        Movie movie = movieMapper.dtoTOMovie(newMovieDTO, characterService, franchiseService);
        movie.setCharacters(new HashSet<>());
        movie.setFranchise(null);
        Movie movieSave = movieService.add(movie);

         return ResponseEntity.status(HttpStatus.CREATED).body(movieMapper.movieToDto(movieSave));
    }

    /**
     * Update a movie by ID
     *
     * @param movieDTO MovieDTO object with updated movie details
     * @param id ID of the movie to update
     * @return MovieDTO object for the updated movie
     */
    @Operation(summary = "Update a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success - Updated the movie"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json"))
    })
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
    /**
     * Update characters in a movie by ID
     *
     * @param charactersId Set of IDs of characters to add to the movie
     * @param id ID of the movie to update
     * @return Set of CharacterDTO objects for the updated movie
     */
    @Operation(summary = "Update characters in a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success - Updated the movie"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("{id}/characters") // PUT
    public Set<CharacterDTO> updateCharactersInMovie(@RequestBody Set<Integer> charactersId, @PathVariable int id) {

        Set<Characters> updatedMovie = movieService.updateCharactersInMovie( id,charactersId);


        return updatedMovie.stream().map(characterMapper::toCharacterDto).collect(Collectors.toSet());
    }

    /**
     * Delete a movie by ID
     *
     * @param id ID of the movie to delete
     * @return HTTP response indicating success or failure
     */
    @Operation(summary = "Delete a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success - Deleted the movie"),
            @ApiResponse(responseCode = "404", description = "Not Found - No movie found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("{id}") // DELETE
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            movieService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Get characters by a movie id
    @Operation(summary = "Get characters by a movie id", description = "Get characters by a movie id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success - Found the characters",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CharacterDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found - No characters found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json"))})
    @GetMapping("{id}/characters")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<CharacterDTO> getCharactersByMovieId(@PathVariable int id) {
        Set<Characters> characters = movieService.getCharactersByMovieId(id);
        Set<CharacterDTO> characterDTOs = characters.stream()
                .map(characterMapper::toCharacterDto)
                .collect(Collectors.toSet());
        return characterDTOs;
    }

}
