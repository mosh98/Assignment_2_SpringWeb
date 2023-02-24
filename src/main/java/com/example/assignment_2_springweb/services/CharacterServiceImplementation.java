package com.example.assignment_2_springweb.services;

import com.example.assignment_2_springweb.model.Characters;

import java.util.Optional;

public class CharacterServiceImplementation implements CharactersService {
    @Override
    public Optional<Characters> findByAlias(String alias) {

        return Optional.empty();
    }
}
