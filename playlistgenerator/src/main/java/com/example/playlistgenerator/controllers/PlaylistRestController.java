package com.example.playlistgenerator.controllers;

import com.example.playlistgenerator.dto.PlaylistDto;
import com.example.playlistgenerator.models.Playlist;
import com.example.playlistgenerator.models.Track;
import com.example.playlistgenerator.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistRestController {
    private PlaylistService service;

    @Autowired
    public PlaylistRestController(PlaylistService service) {
        this.service = service;
    }


    //    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/createPlaylist")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity createPlaylistByGenre (@RequestBody PlaylistDto playlistDto, Authentication authentication) {
        String username = authentication.getName();
        service.generatePlaylist(playlistDto, username);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getPlaylists")
    public Iterable<Playlist> getAllPlaylists() {
        return service.getAllPlaylists();
    }

    @DeleteMapping("/deletePlaylist/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity removePlaylist(@PathVariable long id, Authentication authentication) {
        service.removePlaylist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/updatePlaylist")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity updatePlaylist(@RequestBody Playlist playlist, Authentication authentication) {
        try{
            service.updatePlaylist(playlist, authentication.getName());
        } catch (IllegalAccessException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getTracks/{playlistId}")
    public Iterable<Track> getTracks(@PathVariable long playlistId) {
        return service.getTracksForPlaylist(playlistId);
    }
}
