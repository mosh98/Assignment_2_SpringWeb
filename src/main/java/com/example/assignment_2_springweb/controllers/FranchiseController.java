package com.example.assignment_2_springweb.controllers;

import com.example.assignment_2_springweb.model.Franchise;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.services.franchise.FranchiseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(path = "franchises")
public class FranchiseController {

    private final FranchiseService franchiseService;

    public FranchiseController(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

    @GetMapping
    public ResponseEntity<Collection<Franchise>> getAll() {
        Collection<Franchise> franchise = franchiseService.findAll();
        return new ResponseEntity<>(franchise, HttpStatus.OK);
    }

    @GetMapping("{id}") // GET:
    public ResponseEntity<Franchise> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(franchiseService.findById(id));
    }

    @PostMapping // POST
    public ResponseEntity<Franchise> add(@RequestBody Franchise franchise) {
        Franchise franc = franchiseService.add(franchise);
        URI location = URI.create("franchises/" + franc.getId());
        return ResponseEntity.created(location).build();
    }

    // TODO: Movies not updating
    @PutMapping("{id}") // PUT
    public ResponseEntity<Franchise> update(@RequestBody Franchise franchise, @PathVariable int id) {
        // Validates if body is correct
        if(id != franchise.getId())
            return ResponseEntity.badRequest().build();
        franchiseService.update(franchise);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}") // DELETE
    public ResponseEntity<Franchise> delete(@PathVariable int id) {
        franchiseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
