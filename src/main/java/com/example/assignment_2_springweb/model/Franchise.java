package com.example.assignment_2_springweb.model;


import jakarta.persistence.*;
import lombok.*;

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




}
