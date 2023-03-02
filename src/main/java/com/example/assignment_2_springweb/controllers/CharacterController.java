package com.example.assignment_2_springweb.controllers;
import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import com.example.assignment_2_springweb.mappers.CharacterMapper;
import com.example.assignment_2_springweb.services.character.CharacterService;
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
    @GetMapping
    @ResponseStatus(value= HttpStatus.OK)
    public List<CharacterDTO> getAll(){
        List<Characters> charList = characterService.getAll();
        return charList.stream().map(characterMapper::toCharacterDto).collect(Collectors.toList());
    }

    //
    @GetMapping
    @RequestMapping("{id}")
    @ResponseStatus(value= HttpStatus.OK)
    public CharacterDTO getById(@PathVariable int id){
        return characterMapper.toCharacterDto(characterService.getById(id));
    }

    //create
    @PostMapping
    @ResponseStatus(value= HttpStatus.CREATED)
    public CharacterDTO createCharacter(@RequestBody Characters character){
        return characterMapper.toCharacterDto(characterService.create(character));
    }

    //update
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CharacterDTO updateCharacter(@PathVariable Integer id, @RequestBody CharacterDTO characterDTO) {
        //TODO: update movie? think it through......

        Optional<Characters> optionalCharacter = Optional.ofNullable(characterService.getById(id));

        if (optionalCharacter.isPresent()) {

            //old movie ids
            Set<Integer> movieIds = optionalCharacter.get().getMovie().stream()
                    .map(Movie::getId)
                    .collect(Collectors.toSet());


            Characters character = optionalCharacter.get();
            character.setFullName(characterDTO.getFullName());
            character.setAlias(characterDTO.getAlias());
            character.setGender(characterDTO.getGender());
            character.setPicture(characterDTO.getPicture());

            //merge between dto and old character ids
            movieIds.addAll(characterDTO.getMovie());

            //get all the movie object from the database


            character.setMovie( null); //update existing character obj with new movie object


            //character.setMovies();

            // set other fields as necessary
            CharacterDTO characterDto = characterMapper.toCharacterDto(characterService.create(character)) ;
            return characterDto;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found");
        }
    }


    //delete
    @DeleteMapping("{id}")
    public String delete(@PathVariable int id){
        Optional<Characters> optionalCharacter = Optional.ofNullable(characterService.getById(id));

        if(optionalCharacter.isPresent()){

            Characters characters = optionalCharacter.get();
            //characters.getMovie().forEach(s->s.setCharacters(null));



            //Set<Movie> movieSet =  characters.getMovie(); //get movies this character

            characters.setMovie(null);

   /*         for (Movie movie : movieSet) {
                Set<Characters> movieChars = movie.getCharacters();
                for (Characters movieChar : movieChars) {
                    if(movieChar.getId() ==characters.getId() ){
                        //take away
                    }
                }
            }*/

        }else throw new RuntimeException("No character exist");
        //set
        return characterService.deleteById(id);
    }


}
