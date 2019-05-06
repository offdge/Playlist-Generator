package com.example.playlistgenerator.services;

import com.example.playlistgenerator.dto.PlaylistDto;
import com.example.playlistgenerator.models.Genre;
import com.example.playlistgenerator.models.Playlist;
import com.example.playlistgenerator.models.Track;
import com.example.playlistgenerator.repositories.GenreRepository;
import com.example.playlistgenerator.repositories.PlaylistRepository;
import com.example.playlistgenerator.repositories.TrackRepository;
import com.example.playlistgenerator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Service
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


    public void generatePlaylist(PlaylistDto playlistDto, String username) {
        Playlist playlist = new Playlist();
        playlist.setUser(userRepository.findByUsername(username).get());
        playlist.setPlaylistTitle(playlistDto.getTitle());

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

    public void removePlaylist(long id, Authentication authentication) {
        String playlistOwnerUsername = playlistRepository.findById(id).get().getUser().getUsername();
        removePlaylist(id, playlistOwnerUsername, authentication.getName());
    }

    @PreAuthorize("hasRole('ADMIN')")
    private void removePlaylist(long id, String username, String authenticatedUser){
        playlistRepository.deleteById(id);

    }

    public void updatePlaylist(Playlist playlist, String username) throws IllegalAccessException{
        Playlist playlistToEdit = playlistRepository.findById(playlist.getId()).get();
        if(playlistToEdit.getUser().getUsername().equals(username)){
            playlistToEdit.setPlaylistTitle(playlist.getPlaylistTitle());
            playlistRepository.save(playlistToEdit);
        } else {
            throw new IllegalAccessException("You cannot edit other users playlists.");
        }
    }

    public Iterable<Track> getTracksForPlaylist(long playlistId) {
        return playlistRepository.findById(playlistId).get().getTracklist();
    }
}
