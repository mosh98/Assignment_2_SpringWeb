package com.example.assignment_2_springweb.controllers;

import com.example.assignment_2_springweb.mappers.mapstrukt.FranchiseMapper;
import com.example.assignment_2_springweb.model.Franchise;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.FranchiseDTO;
import com.example.assignment_2_springweb.services.franchise.FranchiseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "franchises")
public class FranchiseController {

  private final FranchiseService franchiseService;
    private final FranchiseMapper franchiseMapper;
     /*
    public FranchiseController(FranchiseService franchiseService, FranchiseMapper franchiseMapper) {
        this.franchiseService = franchiseService;
        this.franchiseMapper = franchiseMapper;
    }*/


    @Operation(summary = "Get all franchises")
    @ApiResponse(responseCode = "200", description = "Found all franchises",content = @Content)
    @ApiResponse(responseCode = "404", description = "No franchises found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping
    public ResponseEntity<Collection<Franchise>> getAll() {
        Collection<Franchise> franchise = franchiseService.findAll();
        return new ResponseEntity<>(franchise, HttpStatus.OK);
    }


    @Operation(summary = "Get a franchise by id")
    @ApiResponse(responseCode = "200", description = "Found the franchise",content = @Content)
    @ApiResponse(responseCode = "404", description = "No franchise found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping("{id}") // GET:
    public ResponseEntity<FranchiseDTO> getById(@PathVariable Integer id) {


        return ResponseEntity.ok(franchiseMapper.franchiseToDto(franchiseService.findById(id)) );
    }

    @Operation(summary="Add a new franchise")
    @ApiResponse(responseCode = "201", description = "Created a new franchise",content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PostMapping // POST
    public ResponseEntity<Franchise> add(@RequestBody Franchise franchise) {
        Franchise franc = franchiseService.add(franchise);
        URI location = URI.create("franchises/" + franc.getId());
        return ResponseEntity.created(location).build();
    }

    // TODO: Movies not updating
    @Operation(summary="Update a franchise")
    @PutMapping("{id}") // PUT
    public ResponseEntity<Franchise> update(@RequestBody Franchise franchise, @PathVariable int id) {
        // Validates if body is correct
        if(id != franchise.getId())
            return ResponseEntity.badRequest().build();
        franchiseService.update(franchise);
        return ResponseEntity.noContent().build();
    }


    //TODO: Get all movies from a franchise

    //TODO: Get all characters from a franchise

    //TODO: Update movies in a francise

    //TODO: Convert to DTO's when returing from controller

    @Operation(summary="Delete a franchise")
    @ApiResponse(responseCode = "204", description = "Deleted a franchise",content = @Content)
    @ApiResponse(responseCode = "404", description = "No franchise found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @DeleteMapping("{id}") // DELETE
    public ResponseEntity<Franchise> delete(@PathVariable int id) {
        franchiseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
