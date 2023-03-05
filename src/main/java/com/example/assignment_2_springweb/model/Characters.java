package com.example.assignment_2_springweb.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter // Lombok
@Setter // Lombok
@ToString // Lombok
@NoArgsConstructor // Lombok
@AllArgsConstructor
@Entity
public class Characters {

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
    private Set<Movie> movie;


}
