package com.example.musicdownload.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlaylistsList {

    @JsonProperty("data")
    private List<Playlist> playlists;

    public PlaylistsList(){
        playlists = new ArrayList<>();
    }
}
