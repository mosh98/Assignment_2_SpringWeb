
package com.example.assignment_2_springweb.repositories;

import com.example.assignment_2_springweb.model.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CharacterRepository extends JpaRepository<Characters, Integer> {



    Optional<Characters> deleteById(int id);

    Optional<Characters> getCharactersByFullName(String name);







}


