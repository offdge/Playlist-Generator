package com.example.playlistgenerator.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Genre")
@Table(name = "genres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    @Column(name="genre_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="genre_name")
    private String name;

    @Column(name="genre_image_url")
    private String picture;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable (
            name = "genre_to_playlist",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn (name = "playlist_id")
    )
    private Set<Playlist> playlist;
}
