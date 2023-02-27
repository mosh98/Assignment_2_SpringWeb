package com.example.assignment_2_springweb;

import com.example.assignment_2_springweb.repositories.CharacterRepository;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.*;

@Component
public class AppRunner implements ApplicationRunner {

    private final CharacterRepository characterRepository;

   @Autowired
    public AppRunner(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        //System.out.println(characterRepository.findByAlias("Iron Man"));
        //characterRepository.findAll().forEach(System.out::println);
        characterRepository.findCharactersByGender("Male").forEach(System.out::println);

    }
}
