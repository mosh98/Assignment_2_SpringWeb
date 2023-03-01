
package com.example.assignment_2_springweb.repositories;

import com.example.assignment_2_springweb.model.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CharacterRepository extends JpaRepository<Characters, Integer> {


/*
    Optional<Characters> findByAlias(String alias);

    Optional<Characters> findByFullName(String fullName);

    List<Characters> findCharactersByGender(String gender);


    Optional<Characters> findByAliasContaining(String substring);
*/



    //@Query
    //Optional<Characters> c


}


