package com.example.assignment_2_springweb.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter // Lombok
@Setter // Lombok
@ToString // Lombok
@NoArgsConstructor // Lombok
@Entity
public class Characters {
    //TODO: Check if the movie_charatcer table only contains unique rows.

    /**
     * Character
     * Autoincremented Id
     * Full name
     * Alias (if applicable)
     * Gender
     * Picture (URL to photo – do not store an image)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private int id;

    @NonNull
    @Column(name = "full_name", length = 200, nullable = false)
    private String fullName;
    @NonNull
    @Column(name = "alias")
    private String alias;
    @NonNull
    @Column(name = "gender",length = 10)
    private String gender;
    @NonNull
    @Column(name = "picture",length = 255)
    private String picture;

    @ManyToMany(mappedBy= "characters")
    @JsonBackReference
    private Set<Movie> movie;


}
