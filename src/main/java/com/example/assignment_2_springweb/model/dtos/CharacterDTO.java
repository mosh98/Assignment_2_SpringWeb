package com.example.assignment_2_springweb.model.dtos;

import com.example.assignment_2_springweb.model.Movie;
import lombok.Data;

import java.util.Set;

@Data
public class CharacterDTO {
    private int id;
    private String fullname;
    private String alias;
    private String gender;
    private String picture;
    private Set<Movie> movie;
}
