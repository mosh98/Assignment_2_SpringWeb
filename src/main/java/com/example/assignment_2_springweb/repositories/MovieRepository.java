package com.example.assignment_2_springweb.repositories;

import com.example.assignment_2_springweb.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Collection<Movie> findAllByTitle(String title);
}
