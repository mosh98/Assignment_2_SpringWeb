package com.example.assignment_2_springweb.model;


import lombok.Getter;
import lombok.ToString;

@Getter // Lombok
@ToString // Lombok
// @Entity
public class Character {
    /**
     * Character
     * • Autoincremented Id
     * • Full name
     * • Alias (if applicable)
     * • Gender
     * • Picture (URL to photo – do not store an image)
     */

    private int id;
    private String fullName;

    private String Alias;

    private String gender;

    private String picture;

    public Character() {
    }


}
