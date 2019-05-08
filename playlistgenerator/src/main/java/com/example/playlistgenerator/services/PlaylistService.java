package com.example.playlistgenerator.services;

import com.example.playlistgenerator.dto.PlaylistDto;
import com.example.playlistgenerator.exception.PlaylistNameExistException;
import com.example.playlistgenerator.models.Genre;
import com.example.playlistgenerator.models.Playlist;
import com.example.playlistgenerator.models.Track;
import com.example.playlistgenerator.repositories.GenreRepository;
import com.example.playlistgenerator.repositories.PlaylistRepository;
import com.example.playlistgenerator.repositories.TrackRepository;
import com.example.playlistgenerator.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PlaylistService {

    private static final Logger logger = LoggerFactory.getLogger(PlaylistService.class);

    private PlaylistRepository playlistRepository;

    private GenreRepository genreRepository;

    private TrackRepository trackRepository;

    private LocationService locationService;

    private UserRepository userRepository;


    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, GenreRepository genreRepository, TrackRepository trackRepository, LocationService locationService, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.genreRepository=genreRepository;
        this.trackRepository=trackRepository;
        this.locationService=locationService;
        this.userRepository=userRepository;
    }

    public Playlist createPlaylistByGenre(Genre genre, long duration, boolean repeatArtists, boolean useTopRankedSongs) {
        Set<Long> artists = new HashSet<>();
        Playlist playlist = new Playlist();
        playlist.addGenre(genre);
        Iterable<Track> iterable = trackRepository.findAllByGenre(genre);

        List<Track> tracks = StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());

        if (useTopRankedSongs) {
            tracks.sort(Comparator.comparingDouble(Track::getRank)
                    .reversed());
        } else {
            Collections.shuffle(tracks);
        }

        for (Track track : tracks) {
            if (playlist.getPlaylistDuration() >= duration) {
                break;
            }
            if (repeatArtists) {
                playlist.getTracklist().add(track);
            }
            {
                if (!artists.contains(track.getArtist().getId())) {
                    artists.add(track.getArtist().getId());
                    playlist.getTracklist().add(track);
                }
            }
        }
        return playlist;
    }

    public void generatePlaylist(PlaylistDto playlistDto, String username) {
        try {
            Playlist playlist = new Playlist();
            playlist.setUser(userRepository.findByUsername(username).get());

            playlist.setPlaylistTitle(playlistDto.getTitle());

            long duration = locationService.getTravelDuration(
                    playlistDto.getStartPoint(), playlistDto.getEndPoint());

            playlistDto.getGenres().forEach(genreDto -> {
                Genre genre = genreRepository.findByName(genreDto.getName()).get();
                Playlist genrePlaylist = createPlaylistByGenre(
                        genre,
                        (duration * genreDto.getPercentage() * 60) / 100,
                        playlistDto.isAllowSameArtist(),
                        playlistDto.isUseTopRanks());
                playlist.addTracks(genrePlaylist.getTracklist());
                if (genreDto.getPercentage() > 0) {
                    playlist.addGenre(genre);
                }
            });
            playlistRepository.save(playlist);

        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            throw new PlaylistNameExistException("Playlist name already exists");
        }
    }

    public Iterable<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public void userDeletePlaylist(long id, String username) throws IllegalAccessException {
        Playlist playlistToEdit = playlistRepository.findById(id).get();

        if (playlistToEdit.getUser().getUsername().equals(username)) {
            playlistRepository.deleteById(id);
        } else {
            throw new IllegalAccessException("You cannot edit other users playlists.");
        }
    }

    public void userUpdatePlaylist(Playlist playlist, String username) throws IllegalAccessException {
        Playlist playlistToEdit = playlistRepository.findById(playlist.getId()).get();
        if (playlistToEdit.getUser().getUsername().equals(username)) {
            playlistToEdit.setPlaylistTitle(playlist.getPlaylistTitle());
            playlistRepository.save(playlistToEdit);
        } else {
            throw new IllegalAccessException("You cannot edit other users playlists.");
        }
    }

    public void adminDeletePlaylist(long id) {
        playlistRepository.deleteById(id);
    }

    public void adminUpdatePlaylist(Playlist playlist) {
        Playlist playlistToEdit = playlistRepository.findById(playlist.getId()).get();
        playlistToEdit.setPlaylistTitle(playlist.getPlaylistTitle());
        playlistRepository.save(playlistToEdit);
    }

    public Iterable<Track> getTracksForPlaylist(long playlistId) {
        return playlistRepository.findById(playlistId).get().getTracklist();
    }
}
