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

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FranchiseServiceImp implements FranchiseService{

    private final FranchiseRepository franchiseRepository;
    private final MovieRepository movieRepository;

    //crate movie mapper
    private final MovieMapper movieMapper;

    @Autowired
    private final FranchiseMapper franchiseMapper;

    @Autowired
    private CharacterMapper characterMapper;


    @Override
    public Franchise findById(Integer id) {
        return franchiseRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public Collection<Franchise> findAll() {
        return franchiseRepository.findAll();
    }

    @Override
    public Franchise add(Franchise entity) {
        return franchiseRepository.save(entity);
    }



    //make an update function that will update the franchise and the movies
    @Override
    public Franchise update(Franchise entity) {

        return franchiseRepository.save(entity);
    }



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

    @Override
    public boolean exists(Integer id) {
        return franchiseRepository.existsById(id);
    }

    @Override
    public Set<MovieDTO> findAllMovies(Integer id) {

        // get movie ids using franchise id
        //franchiseRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        Set<Movie> franchiseSet =  franchiseRepository.findById(id).get().getMovies();

        // convert to movieDTO
        return franchiseSet.stream().map(movie -> movieMapper.movieToDto(movie)).collect(Collectors.toSet());

    }

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
}
