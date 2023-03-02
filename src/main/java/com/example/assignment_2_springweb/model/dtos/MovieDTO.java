package com.example.assignment_2_springweb.model.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class MovieDTO {
    private int id;
    private String title;
    private String genre;
    private Integer release;
    private String director;
    private String poster;
    private String trailer;
    private Set<Integer> characters;
    private int franchise;

}
