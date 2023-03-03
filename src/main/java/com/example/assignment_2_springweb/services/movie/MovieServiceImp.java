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

    // TODO add exeption lesson https://noroff-accelerate.gitlab.io/java/course-notes/_rework/module3/02_SpringWeb/
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
                //TODO Fix to set only movie_id to Null not clear all - Hibernate: delete from movie_characters where movie_id=?- not right i think
                //movieToDelete.setCharacters(null);

              // movieToDelete.getCharacters().forEach(s -> s.setMovie(null));
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

        Set<Characters> characters = new HashSet<>();
        for (Integer characterId : characterIds) {
            Characters character = characterRepository.findById(characterId)
                    .orElseThrow(() -> new RuntimeException("Not found"));
            characters.add(character);
        }

        movie.setCharacters(characters);

        return characters;
    }

}
