package com.example.playlistgenerator.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Track")
@Table(name = "tracks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Track {

    @Id
    @Column(name="track_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="track_title")
    private String title;

    @Column(name="track_link")
    private String link;

    @Column(name="duration")
    private int duration;

    @Column(name="rank")
    private int rank;

    @Column(name="track_preview_url")
    private String preview;

    @OneToOne
    @JoinColumn(name ="album_id")
    @JsonProperty("album")
    private Album album;

    @OneToOne
    @JoinColumn(name ="artist_id")
    @JsonProperty("artist")
    private Artist artist;

    @OneToOne
    @JoinColumn(name ="genre_id")
    private Genre genre;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable (
        name = "track_to_playlist",
        joinColumns = @JoinColumn(name = "track_id"),
        inverseJoinColumns = @JoinColumn (name = "playlist_id")
    )
    private Set<Playlist> playlist;

}
