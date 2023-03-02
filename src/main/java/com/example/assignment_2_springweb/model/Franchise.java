package com.example.assignment_2_springweb.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Franchise {

    /**
     * • Autoincremented Id
     * • Name
     * • Description
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "franchise_id")
    private int id;

    @NonNull
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @NonNull
    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @OneToMany(mappedBy = "franchise")
    @JsonIgnore
    private Set<Movie> movies;

    @JsonGetter("movies")
    public List<Integer> jsonGetSubjects() {
        if(movies != null)
            return movies.stream().map(Movie::getId)
                    .collect(Collectors.toList());
        return null;
    }

}
