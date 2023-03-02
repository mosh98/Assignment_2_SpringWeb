package com.example.assignment_2_springweb.repositories;

import com.example.assignment_2_springweb.model.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Integer> {

    Optional<Franchise> findById(Integer id);
}
