package com.example.assignment_2_springweb.controllers;
import com.example.assignment_2_springweb.mappers.mapstrukt.CharacterMapper;
import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import com.example.assignment_2_springweb.services.character.CharacterService;
import com.example.assignment_2_springweb.services.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/character")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;
    private final CharacterMapper characterMapper;

    private final MovieService movieService;

    //get

    @Operation(summary = "Get all characters",
            description = "Get all characters, return a list with Character DTO objects")
    @ApiResponse(responseCode = "200", description = "Characters found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDTO.class)))
    @ApiResponse(responseCode = "404", description = "Characters not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping
    @ResponseStatus(value= HttpStatus.OK)
    public List<CharacterDTO> getAll(){
        List<Characters> charList = characterService.getAll();
        return charList.stream().map(characterMapper::toCharacterDto).collect(Collectors.toList());
    }

    //
    @Operation(summary = "Get character by id",
            description = "Get character by id, return a Character DTO object")
    @ApiResponse(responseCode = "200", description = "Character found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDTO.class)))
    @ApiResponse(responseCode = "404", description = "Character not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    @ResponseStatus(value= HttpStatus.OK)
    public CharacterDTO getById(@PathVariable int id){
        return characterMapper.toCharacterDto(characterService.getById(id));
    }


    //create
    @Operation(summary = "Create character",
            description = "Create character, return a Character DTO object")
    @ApiResponse(responseCode = "201", description = "Character created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PostMapping
    @ResponseStatus(value= HttpStatus.CREATED)
    public CharacterDTO createCharacter(@RequestBody CharacterDTO character){

        return characterMapper.toCharacterDto(characterService.create( characterMapper.dtoToCharacters(character,movieService) ));
    }

    //update
    @Operation(summary = "Update character",
            description = "Update character, return a Character DTO object")
    @ApiResponse(responseCode = "200", description = "Character updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @ApiResponse(responseCode = "404", description = "Character not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CharacterDTO updateCharacter(@PathVariable Integer id, @RequestBody CharacterDTO characterDTO) {
        //TODO: update movie? think it through......


        return characterService.updateCharacter(id,characterDTO);
    }

    //delete
    @Operation(summary = "Delete character",
            description = "Delete character, return a String")
    @ApiResponse(responseCode = "200", description = "Character deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "404", description = "Character not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @DeleteMapping("{id}")
    public String delete(@PathVariable int id){

        return characterService.delete(id);
    }


}
