package com.example.playlistgenerator.services;

import com.example.playlistgenerator.models.Playlist;
import com.example.playlistgenerator.models.Track;
import com.example.playlistgenerator.repositories.PlaylistRepository;
import com.example.playlistgenerator.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PlaylistService {
    private int travelTime = 10800;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private TrackRepository trackRepository;

    public Playlist createPlaylistByGenre(String genre) {
        Playlist playlist = new Playlist();
        Iterable<Track> iterable = trackRepository.findAllByGenre(genre);
        List<Track> tracks = StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        Collections.shuffle(tracks);
        for (Track track : tracks) {
            playlist.getTracklist().add(track);
            if (playlist.getPlaylistDuration() > travelTime) {
                break;
            }
        }

        return playlist;
    }


}
