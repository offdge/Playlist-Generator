package com.example.playlistgenerator.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Artist")
@Table(name = "artists")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private long id;

    @Column(name = "artist_name")
    private String name;

    @Column(name = "artist_tracklist_url")
    private String tracklist;
}
