package com.example.playlistgenerator.services;

import com.example.playlistgenerator.dto.GenreDto;
import com.example.playlistgenerator.dto.PlaylistDto;
import com.example.playlistgenerator.models.Genre;
import com.example.playlistgenerator.models.Playlist;
import com.example.playlistgenerator.models.Track;
import com.example.playlistgenerator.models.User;
import com.example.playlistgenerator.repositories.GenreRepository;
import com.example.playlistgenerator.repositories.PlaylistRepository;
import com.example.playlistgenerator.repositories.TrackRepository;
import com.example.playlistgenerator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserRepository userRepository;


    public Playlist createPlaylistByGenre(Genre genre, long duration) {
        Playlist playlist = new Playlist();
        playlist.addGenre(genre);
        Iterable<Track> iterable = trackRepository.findAllByGenre(genre);
        List<Track> tracks = StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        Collections.shuffle(tracks);
        for (Track track : tracks) {
            if (playlist.getPlaylistDuration() >= duration) {
                break;
            }
            playlist.getTracklist().add(track);
        }

        return playlist;
    }


    public void generatePlaylist(PlaylistDto playlistDto) {
        Playlist playlist = new Playlist();
        playlist.setPlaylistTitle(playlistDto.getTitle());

        // to be replaced with real user using
//        User user = userRepository.findByUsername("bizzcuit").get();
//        playlist.setUser(user);

        long duration = locationService.getTravelDuration(
                playlistDto.getStartPoint(), playlistDto.getEndPoint());

        playlistDto.getGenres().forEach(genreDto -> {
                Genre genre = genreRepository.findByName(genreDto.getName()).get();
                Playlist genrePlaylist = createPlaylistByGenre(
                        genre, (duration * genreDto.getPercentage() * 60) / 100);
                playlist.addTracks(genrePlaylist.getTracklist());
                if (genreDto.getPercentage() > 0) {
                    playlist.addGenre(genre);
                }
        });

        playlistRepository.save(playlist);
    }

    public Iterable<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }
}
