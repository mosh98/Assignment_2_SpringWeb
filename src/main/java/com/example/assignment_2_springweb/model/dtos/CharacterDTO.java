package com.example.assignment_2_springweb.model.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class CharacterDTO {
    private int id;
    private String fullName;
    private String alias;

    private String gender;

    private String picture;

    private Set<Integer> movie;
}
