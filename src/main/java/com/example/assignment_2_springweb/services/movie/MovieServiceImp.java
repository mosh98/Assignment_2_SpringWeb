package com.example.assignment_2_springweb.services.movie;

import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.repositories.CharacterRepository;
import com.example.assignment_2_springweb.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImp implements MovieService{

    private final MovieRepository movieRepository;
    private final CharacterRepository characterRepository;

    public MovieServiceImp(MovieRepository movieRepository, CharacterRepository characterRepository) {
        this.movieRepository = movieRepository;
        this.characterRepository = characterRepository;
    }

    @Override
    public Movie findById(Integer id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    @Override
    public Collection<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie add(Movie entity) {
        return movieRepository.save(entity);
    }

    @Override
    public Movie update(Movie entity) {

        return movieRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);

            if(optionalMovie.isPresent()) {
                // Set relationships to null, so we can delete without referential problems
                Movie movieToDelete = optionalMovie.get();
                movieToDelete.setFranchise(null);

                movieToDelete.getCharacters().forEach(character -> character.getMovie().remove(movieToDelete));
                movieRepository.deleteById(id);
            } else {
                throw new RuntimeException("No movie exist to delete");
            }
    }

    @Override
    public boolean exists(Integer id) {
        return movieRepository.existsById(id);
    }

    @Override
    public Set<Characters> findAllCharacters(Integer id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return movie.getCharacters();
    }

    @Override
    @Transactional
    public Set<Characters> updateCharactersInMovie(Integer id, Set<Integer> characterIds) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));


        //get characters using ids
        Set<Characters> characters = new HashSet<>();
        for (Integer characterId : characterIds) {
            Characters character = characterRepository.findById(characterId)
                    .orElseThrow(() -> new RuntimeException("Not found"));
            characters.add(character);
        }
        if(characters.isEmpty()) {
            throw new RuntimeException("No characters found");
        }
        Set<Characters> currentCast = movie.getCharacters(); //get old characters
        characters.forEach(character -> currentCast.add(character)); //add the new characters

        movie.setCharacters(currentCast); //to da loo

        return movie.getCharacters();
    }

    @Override
    public Set<Characters> getCharactersByMovieId(int id) {
        //find movie
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));

        //get characters
        Set<Characters> characters = movie.getCharacters();
        if(characters.isEmpty()) {
            throw new RuntimeException("No characters found");
        }
        return characters;
    }

}
