package com.example.assignment_2_springweb.controllers;
import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import com.example.assignment_2_springweb.mappers.CharacterMapper;
import com.example.assignment_2_springweb.services.character.CharacterService;
import com.example.assignment_2_springweb.services.movie.MovieServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

    //get
    @Operation(summary = "Get all characters",
            description = "Get all characters, return a list with Character DTO objects",
            tags = {"character"})
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
            description = "Get character by id, return a Character DTO object",
            tags = {"character"})
    @ApiResponse(responseCode = "200", description = "Character found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDTO.class)))
    @ApiResponse(responseCode = "404", description = "Character not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping
    @RequestMapping("{id}")
    @ResponseStatus(value= HttpStatus.OK)
    public CharacterDTO getById(@PathVariable int id){
        return characterMapper.toCharacterDto(characterService.getById(id));
    }


    //create
    @Operation(summary = "Create character",
            description = "Create character, return a Character DTO object",
            tags = {"character"})
    @ApiResponse(responseCode = "201", description = "Character created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PostMapping
    @ResponseStatus(value= HttpStatus.CREATED)
    public CharacterDTO createCharacter(@RequestBody Characters character){
        return characterMapper.toCharacterDto(characterService.create(character));
    }

    //update
    @Operation(summary = "Update character",
            description = "Update character, return a Character DTO object",
            tags = {"character"})
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
            description = "Delete character, return a String",
            tags = {"character"})
    @ApiResponse(responseCode = "200", description = "Character deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "404", description = "Character not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @DeleteMapping("{id}")
    public String delete(@PathVariable int id){
        Optional<Characters> optionalCharacter = Optional.ofNullable(characterService.getById(id));

        if(optionalCharacter.isPresent()){

            Characters characters = optionalCharacter.get();

            //characters.setMovie(null);
            Set<Movie> movieSet = characters.getMovie();

            //from each movie if the character is in it, remove it
            for (Movie movie : movieSet) {
                Set<Characters> movieChars = movie.getCharacters();
                for (Characters movieChar : movieChars) {
                    if(movieChar.getId() ==characters.getId() ){
                        //take away character from movie
                        movieChars.remove(movieChar);
                    }
                }
                movie.setCharacters(movieChars); //updating each movie
            }



        }else throw new RuntimeException("No character exist");
        //set
        return characterService.deleteById(id);
    }


}
