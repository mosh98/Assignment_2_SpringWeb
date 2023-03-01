package com.example.assignment_2_springweb.services.character;

import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.repositories.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }


    @Override
    public Characters findById(Integer integer) {
        return characterRepository.findById(integer).orElseThrow(() -> new RuntimeException("Character not found")); //TODO: create custom exception
    }

    @Override
    public Collection<Characters> findAll() {

        return characterRepository.findAll();
    }

    @Override
    public Characters add(Characters entity) {
        return characterRepository.save(entity);
    }

    @Override
    public Characters update(Characters entity) {
        return characterRepository.save(entity);
    }

    @Override
    public void deleteById(Integer integer) {


    }

    @Override
    public boolean exists(Integer integer) {

        return characterRepository.existsById(integer);
    }
}
