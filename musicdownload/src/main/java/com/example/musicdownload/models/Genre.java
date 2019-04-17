package com.example.musicdownload.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Genre")
@Table(name = "genres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
    public class Genre {
        @Id
        @Column(name="genre_id")
        private long id;

        @Column(name="genre_name")
        private String name;

        @Column(name="genre_image_url")
        private String picture;
}
