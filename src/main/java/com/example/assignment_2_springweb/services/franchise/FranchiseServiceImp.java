package com.example.assignment_2_springweb.services.franchise;

import com.example.assignment_2_springweb.model.Franchise;
import com.example.assignment_2_springweb.model.Movie;
import com.example.assignment_2_springweb.repositories.FranchiseRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class FranchiseServiceImp implements FranchiseService{

    private final FranchiseRepository franchiseRepository;

    public FranchiseServiceImp(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

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
}
