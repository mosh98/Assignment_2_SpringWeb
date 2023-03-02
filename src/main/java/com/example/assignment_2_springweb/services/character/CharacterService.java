package com.example.assignment_2_springweb.services.character;
import com.example.assignment_2_springweb.model.Characters;
import com.example.assignment_2_springweb.repositories.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;

    public List<Characters> getAll() {return characterRepository.findAll();}

    public Characters create(Characters character) {return characterRepository.save(character);}

    public String deleteById(int id) {
        System.out.println(id);

        characterRepository.deleteById(id); return "Character Deleted";}

    public Characters getById(int id) {
        return characterRepository.getReferenceById(id);
    }
}
