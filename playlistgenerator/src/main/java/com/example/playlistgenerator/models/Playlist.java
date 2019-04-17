package com.example.playlistgenerator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    @Id
    @Column(name="playlist_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name = "playlist_title")
    private String playlistTitle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "track_to_playlist",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    private Set<Track> tracklist;
}
