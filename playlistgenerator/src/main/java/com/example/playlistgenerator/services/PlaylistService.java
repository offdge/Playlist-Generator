package com.example.playlistgenerator.services;

import com.example.playlistgenerator.models.Playlist;
import com.example.playlistgenerator.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public void populatePlaylist() {
        List<Playlist> playlist = new ArrayList<>();
    }
}
