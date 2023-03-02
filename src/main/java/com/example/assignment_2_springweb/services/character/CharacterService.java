package com.example.assignment_2_springweb.services.character;
import com.example.assignment_2_springweb.mappers.CharacterMapper;
import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import com.example.assignment_2_springweb.repositories.CharacterRepository;
import com.example.assignment_2_springweb.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final MovieRepository movieRepository;
    private final CharacterMapper characterMapper;

    public List<Characters> getAll() {return characterRepository.findAll();}

    public Characters create(Characters character) {return characterRepository.save(character);}

    public CharacterDTO updateCharacter (int id, CharacterDTO characterDTO){

        Optional<Characters> optionalCharacter = Optional.of(characterRepository.getById(id));

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
            Set<Movie> movieSet = movieIds.stream().map(movieRepository::getById).collect(Collectors.toSet());

            for (Movie movie : movieSet) {
                movie.getCharacters().add(character);
            }

            character.setMovie( movieSet); //update existing character obj with new movie object


            // set other fields as necessary
            CharacterDTO characterDto = characterMapper.toCharacterDto(characterRepository.saveAndFlush(character));
            return characterDto;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found");
        }

    }

    public String deleteById(int id) {
        System.out.println(id);

        characterRepository.deleteById(id); return "Character Deleted";}

    public Characters getById(int id) {
        return characterRepository.getReferenceById(id);
    }
}
