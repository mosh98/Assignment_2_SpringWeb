package com.example.assignment_2_springweb.services;

import com.example.assignment_2_springweb.model.Characters;

import java.util.Optional;

public interface CharactersService {

    Optional<Characters> findByAlias(String alias);
}
