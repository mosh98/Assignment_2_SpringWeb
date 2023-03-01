package com.example.assignment_2_springweb.services.movie;

import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class MovieServiceImp implements MovieService{

    private final MovieRepository movieRepository;

    public MovieServiceImp(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
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
        return movieRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);

            if(optionalMovie.isPresent()) {
                // Set relationships to null, so we can delete without referential problems
                Movie movieToDelete = optionalMovie.get();
                movieToDelete.setFranchise(null);
                //TODO Fix to set only movie_id to Null
                movieToDelete.setCharacters(null);

               // movieToDelete.getCharacters().forEach(s -> s.setMovie(null));
                movieRepository.deleteById(id);
            } else{
                throw new RuntimeException("No movie exist to delete");

            }
    }

    @Override
    public boolean exists(Integer integer) {
        return movieRepository.existsById(integer);
    }

/*    @Override
    public Collection<Movie> findAllByTitle(String title) {
        return movieRepository.findAll(title);
    }*/
}
