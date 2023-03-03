package com.example.assignment_2_springweb.services.franchise;

import com.example.assignment_2_springweb.mappers.mapstrukt.CharacterMapper;
import com.example.assignment_2_springweb.mappers.mapstrukt.FranchiseMapper;
import com.example.assignment_2_springweb.mappers.mapstrukt.MovieMapper;
import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.model.Franchise;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.model.dtos.CharacterDTO;
import com.example.assignment_2_springweb.model.dtos.MovieDTO;
import com.example.assignment_2_springweb.repositories.FranchiseRepository;
import com.example.assignment_2_springweb.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FranchiseServiceImp implements FranchiseService{
    /**
     * This class is the service layer for the franchise
     * It will handle all the business logic
     * It will also handle the mapping between the entity and the DTO for some methods
     *
     */

    private final FranchiseRepository franchiseRepository;
    private final MovieRepository movieRepository;

    //crate movie mapper
    private final MovieMapper movieMapper;

    @Autowired
    private final FranchiseMapper franchiseMapper;

    @Autowired
    private CharacterMapper characterMapper;


    /**
     * Find a franchise by id
     *
     * @param id
     * @return Francise object
     */
    @Override
    public Franchise findById(Integer id) {
        return franchiseRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    /**
     * Find all franchises
     * @return a collection of francise
     */

    @Override
    public Collection<Franchise> findAll() {
        return franchiseRepository.findAll();
    }

    /**
     * Add a franchise
     * @param entity
     * @return the added franchise (i.e Franchise object)
     */

    @Override
    public Franchise add(Franchise entity) {
        return franchiseRepository.saveAndFlush(entity);
    }



    //make an update function that will update the franchise and the movies

    /**
     * Update a franchise, and the movies
     * @param entity
     * @return the updated franchise
     */
    @Override
    public Franchise update(Franchise entity) {
        //get the movie ids from the entity
        Set<Movie> movies = entity.getMovies();
        movies.forEach(movie -> {
            movie.setFranchise(entity);
        });

        return franchiseRepository.save(entity);
    }



    /**
     * Delete a franchise by id
     * @param id
     * @return the deleted franchise
     */
    @Override
    public void deleteById(Integer id) {
        Optional<Franchise> optionalFranchise = franchiseRepository.findById(id);

        if (optionalFranchise.isPresent()) {
            Franchise franchiseDelete = optionalFranchise.get();
            Set<Movie> movies = franchiseDelete.getMovies();
            for (Movie movie : movies) {
                movie.setFranchise(null);
            }

            franchiseRepository.deleteById(id);
        } else {
            throw new RuntimeException("No franchise exist to delete");
        }
    }

    /**
     * Check if a franchise exists
     * @param id
     * @return true if the franchise exists, false otherwise
     */
    @Override
    public boolean exists(Integer id) {
        return franchiseRepository.existsById(id);
    }


    /**
     * Find all movies from a franchise
     * @param id
     * @return a collection of movies
     */
    @Override
    public Set<MovieDTO> findAllMovies(Integer id) {

        // get movie ids using franchise id
        //franchiseRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        Set<Movie> franchiseSet =  franchiseRepository.findById(id).get().getMovies();

        // convert to movieDTO
        return franchiseSet.stream().map(movie -> movieMapper.movieToDto(movie)).collect(Collectors.toSet());

    }

    /**
     * Find all characters from a franchise
     * @param id
     * @return a Set of characters in DTO format
     */

    @Override
    public Set<CharacterDTO> getAllCharactersFromFranchise(Integer id) {

        //Get all characters from a franchise
        //first get all movies from a franchise
        //for each movie get all characters
        //return all characters

        Set<Movie> movies = franchiseRepository.findById(id).get().getMovies();
        Set<CharacterDTO> characters = new HashSet<>();

        for (Movie movie : movies) {

            Set<Characters> charactersInCurrentMovie = movie.getCharacters();

            for (Characters character : charactersInCurrentMovie) {
                //System.out.println(character.getFullName());
                CharacterDTO xx =  characterMapper.toCharacterDto(character);
                System.out.println(xx.getFullName());
                characters.add(xx);
            }
        }

        return characters;
    }

    /**
     * Update the movies in a franchise
     * @param franchiseId
     * @param movieIds list of movie ids
     * @return the updated franchise in the movie and save it.
     */
    @Override
    public Franchise updateMovies(int franchiseId, List<Integer> movieIds) {

        //Add the franchise object for each movie objects.
        //Then save the franchise object.
        Franchise franchise = franchiseRepository.findById(franchiseId).orElseThrow(() -> new RuntimeException("Not found"));

        //get all movies from the movie ids

        for (Integer movieId : movieIds) {
            Optional<Movie> curMovie = movieRepository.findById(movieId);
            curMovie.get().setFranchise(franchise);
        }

        return franchiseRepository.save(franchise);
    }
}
