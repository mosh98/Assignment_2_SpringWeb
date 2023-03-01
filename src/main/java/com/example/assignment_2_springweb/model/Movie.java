package com.example.assignment_2_springweb.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity  // Turn class into entity. This will require a primary key.
public class Movie {
    @Id     // primary key annotation.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autogenerate id
    @Column(name = "movie_id")
    private int id;             // int can't be NULL
    @Column(name = "movie_title", length = 50, nullable = false)
    private String title;
    // Genre (just a simple string of comma separated genres)
    @Column(name = "movie_genre", length = 100)
    private String genre;
    @Column(name = "release_year", length = 4)
    private Integer release;    // Integer can be NULL
    @Column(name = "director", length = 50, nullable = false)
    private String director;
    // Picture (URL to a movie poster) TODO should it be picture_URL or poster_URL
    @Column(name = "poster_URL", length = 100)
    private String poster;
    @Column(name = "trailer_link", length = 100)
    private String trailer;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(name = "movie_characters", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "character_id"))
    private Set<Characters> characters;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", release=" + release +
                ", director='" + director + '\'' +
                ", poster='" + poster + '\'' +
                ", trailer='" + trailer + '\'' +
                ", franchise=" + franchise +
                '}';
    }
}
