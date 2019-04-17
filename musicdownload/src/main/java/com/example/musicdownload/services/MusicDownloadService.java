package com.example.musicdownload.services;

import com.example.musicdownload.models.*;
import com.example.musicdownload.repositories.AlbumRepository;
import com.example.musicdownload.repositories.ArtistRepository;
import com.example.musicdownload.repositories.GenreRepository;
import com.example.musicdownload.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MusicDownloadService {


    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    private List<String> getTrackLists(Genre genre)throws NullPointerException{
        RestTemplate restTemplate = new RestTemplate();

        PlaylistsList response =
                restTemplate.getForObject(
                        "https://api.deezer.com/search/playlist?q=" + genre.getName() + "&index=0&limit=20",
                        PlaylistsList.class);

        return response.getPlaylists().stream().map(Playlist::getTracklist).collect(Collectors.toList());
    }

    public void populateGenres() {
        List<Genre> genres = getGenres();
        List<Genre> newGenres = new ArrayList<>();

        for (Genre genre : genres) {
            if (!genreRepository.existsByName(genre.getName())) {
                newGenres.add(genre);
            }
        }

        genreRepository.saveAll(newGenres);
    }

    public void populateTracks(String genreName){
        Optional<Genre> genre = genreRepository.findByName(genreName);
        if(!genre.isPresent()){
            throw new IllegalArgumentException("There is no such genre");
        }

        List<Track> tracks = getAllTracksForGenre(genre.get());

        setGenresToTracks(tracks, genre.get());

        saveAlbumsFromTracks(tracks);
        saveArtistsFromTracks(tracks);
        trackRepository.saveAll(tracks);
    }

    private void saveAlbumsFromTracks(List<Track> tracks){
        List<Artist> artists = getArtists(tracks);
        artistRepository.saveAll(artists);
    }

    private void saveArtistsFromTracks(List<Track> tracks){
        List<Album> albums = getAlbums(tracks);
        albumRepository.saveAll(albums);
    }

    private List<Track> getAllTracksForGenre(Genre genre){
        List<String> tracklists = getTrackLists(genre);
        List<Track> tracks = new ArrayList<>();
        tracklists.forEach(t -> tracks.addAll(getTracksFromTrackList(t)));
        return tracks;
    }

    private List<Track> getTracksFromTrackList(String trackList){
        RestTemplate restTemplate = new RestTemplate();

        TracksList response =
                restTemplate.getForObject(trackList + "?index=0&limit=100", TracksList.class);

        List<Track> tracks = response.getTracks();
        return tracks;

    }

    private List<Album> getAlbums(List<Track> tracks){
        List<Album> albums = new ArrayList<>();
        tracks.forEach(track -> albums.add(track.getAlbum()));
        return albums;
    }

    private void setGenresToTracks(List<Track> tracks, Genre genre){
        for (Track track : tracks) {
            track.setGenre(genre);
        }
    }

    private List<Artist> getArtists(List<Track> tracks){
        List<Artist> artists = new ArrayList<>();
        tracks.forEach(track -> artists.add(track.getArtist()));
        return artists;
    }

    private List<Genre> getGenres()throws NullPointerException{
        RestTemplate restTemplate = new RestTemplate();

        GenresList response =
                restTemplate.getForObject(
                        "http://api.deezer.com/genre",
                        GenresList.class);

        List<Genre> genres = response.getGenres();
        return genres;
    }
}
