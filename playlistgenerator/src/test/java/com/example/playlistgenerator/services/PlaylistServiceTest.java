package com.example.playlistgenerator.services;

import com.example.playlistgenerator.dto.GenreDto;
import com.example.playlistgenerator.dto.PlaylistDto;
import com.example.playlistgenerator.exception.PlaylistNotExistException;
import com.example.playlistgenerator.models.Genre;
import com.example.playlistgenerator.models.Playlist;
import com.example.playlistgenerator.repositories.GenreRepository;
import com.example.playlistgenerator.repositories.PlaylistRepository;
import com.example.playlistgenerator.repositories.TrackRepository;
import com.example.playlistgenerator.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class PlaylistServiceTest {
    @Mock
    PlaylistRepository playlistRepository;
    @Mock
    GenreRepository genreRepository;
    @Mock
    TrackRepository trackRepository;
    @Mock
    LocationService locationService;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    PlaylistService playlistService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreatePlaylistByGenre() throws Exception {
        playlistService.createPlaylistByGenre(new Genre(), 0L);
    }

//    @Test
//    public void testGeneratePlaylist() throws Exception {
//        when(genreRepository.findByName(anyString())).thenReturn(null);
//        when(trackRepository.findAllByGenre(any())).thenReturn(null);
//        when(locationService.getTravelDuration(anyString(), anyString())).thenReturn(0L);
//        when(userRepository.findByUsername(anyString())).thenReturn(null);
//
//        playlistService.generatePlaylist(new PlaylistDto("title", Arrays.<GenreDto>asList(new GenreDto("name", 0)), "startPoint", "endPoint", true, true), "username");
//    }

    @Test(expected = RuntimeException.class)
    public void testGeneratePlaylist() {
        playlistService.generatePlaylist(new PlaylistDto(), "username");
    }

    @Test
    public void testGetAllPlaylists(){
        when(playlistService.getAllPlaylists()).thenReturn(new ArrayList<>());
        Assert.assertEquals(new ArrayList<>(), playlistService.getAllPlaylists());
    }

    @Test
    public void testRemovePlaylist() throws PlaylistNotExistException {
        playlistService.removePlaylist(0L);
    }
}

