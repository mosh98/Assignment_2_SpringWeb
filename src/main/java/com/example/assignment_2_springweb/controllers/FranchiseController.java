package com.example.assignment_2_springweb.controllers;

import com.example.assignment_2_springweb.mappers.mapstrukt.FranchiseMapper;
import com.example.assignment_2_springweb.model.Franchise;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import com.example.assignment_2_springweb.model.dtos.FranchiseDTO;
import com.example.assignment_2_springweb.model.dtos.MovieDTO;
import com.example.assignment_2_springweb.services.franchise.FranchiseService;
import com.example.assignment_2_springweb.services.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.DataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "franchises")
public class FranchiseController {

  private final FranchiseService franchiseService;
  private final MovieService movieService;
  private final FranchiseMapper franchiseMapper;


    @Operation(summary = "Get all franchises")
    @ApiResponse(responseCode = "200", description = "Found all franchises",content = @Content)
    @ApiResponse(responseCode = "404", description = "No franchises found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping
    public ResponseEntity<Collection<FranchiseDTO>> getAll() {
        Collection<Franchise> franchise = franchiseService.findAll();

        Collection<FranchiseDTO> franchiseDTOs = new ArrayList<>();

        //convert each franchise to a franchiseDTO
        for (Franchise fr : franchise) {
            franchiseDTOs.add(franchiseMapper.franchiseToDto(fr));
        }

        return new ResponseEntity<>(franchiseDTOs, HttpStatus.OK);
    }


    @Operation(summary = "Get a franchise by id")
    @ApiResponse(responseCode = "200", description = "Found the franchise",content = @Content(mediaType = "application/json", schema = @Schema(implementation = FranchiseDTO.class)))
    @ApiResponse(responseCode = "404", description = "No franchise found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping("{id}") // GET:
    @ResponseStatus(value= HttpStatus.OK)
    public FranchiseDTO getById(@PathVariable int id) {
        Franchise franchise = franchiseService.findById(id);
        FranchiseDTO franchiseDTO = franchiseMapper.franchiseToDto(franchise);

        return franchiseDTO;
    }

    @Operation(summary="Add a new franchise")
    @ApiResponse(responseCode = "201", description = "Created a new franchise",content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PostMapping
    @ResponseStatus(value= HttpStatus.CREATED)
    public FranchiseDTO add(@RequestBody FranchiseDTO franchiseDTO) {
        Franchise franc = franchiseService.add( franchiseMapper.dtoToFranchise(franchiseDTO,movieService));
        FranchiseDTO returnDto = franchiseMapper.franchiseToDto(franc);

        return returnDto;
    }

    // TODO: Movies not updating
    @Operation(summary="Update a franchise")
    @PutMapping("{id}") // PUT
    @ResponseStatus(HttpStatus.OK)
    public FranchiseDTO update(@RequestBody FranchiseDTO franchise, @PathVariable int id) {
        // Validates if body is correct
        if(id != franchise.getId())
            return null;

        return franchiseMapper.franchiseToDto(franchiseService.update( franchiseMapper.dtoToFranchise(franchise,movieService) ));
    }


    //TODO: Get all movies from a franchise
    //Get all movies from a franchise
    @Operation(summary = "Get all movies from a franchise")
    @ApiResponse(responseCode = "200", description = "Found all movies from a franchise",content = @Content)
    @ApiResponse(responseCode = "404", description = "No movies found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping("{id}/movies")
    @ResponseStatus(HttpStatus.OK)
    public Set<MovieDTO> getAllMovies(@PathVariable Integer id) {

     return franchiseService.findAllMovies(id);
    }


    //Get all characters from a franchise
    @Operation(summary = "Get all characters from a franchise")
    @ApiResponse(responseCode = "200", description = "Found all characters from a franchise",content = @Content)
    @ApiResponse(responseCode = "404", description = "No characters found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping
    @RequestMapping(path= "{id}/characters", method = RequestMethod.GET )
    @ResponseStatus(value=HttpStatus.OK)
    public Set<CharacterDTO> getAllCharacters(@PathVariable Integer id) {

        return franchiseService.getAllCharactersFromFranchise(id);
    }



    // Update movies in a franchise, where we take list of movies and add them to the franchise
    //Update movies in a franchise
    @Operation(summary="Update movies in a franchise")
    @PutMapping("{id}/movies") // PUT
    @ResponseStatus(HttpStatus.OK)
    public FranchiseDTO updateMoviesForFranchise(@PathVariable int id, @RequestBody List<Integer> movieIds) {

        Franchise franchise = franchiseService.updateMovies(id, movieIds);

        return franchiseMapper.franchiseToDto(franchise);
    }



    @Operation(summary="Delete a franchise")
    @ApiResponse(responseCode = "200", description = "Deleted a franchise",content = @Content)
    @ApiResponse(responseCode = "404", description = "No franchise found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @DeleteMapping("{id}") // DELETE
    public ResponseEntity<?> delete(@PathVariable int id) {
        try{
            franchiseService.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (DataException e){
            return ResponseEntity.notFound().build();
        }


    }
}
