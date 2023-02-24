
package com.example.assignment_2_springweb.repositories;

import com.example.assignment_2_springweb.model.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CharacterRepository extends JpaRepository<Characters, Integer> {

    //@Query("SELECT * FROM characters WHERE alias = ?")
    //Optional<Characters> findByAlias(String alias);

    Optional<Characters> findByAlias(String alias);


}


