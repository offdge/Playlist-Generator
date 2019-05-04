package com.example.playlistgenerator.controllers;

import com.example.playlistgenerator.dto.PlaylistDto;
import com.example.playlistgenerator.models.Playlist;
import com.example.playlistgenerator.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//    @PostMapping("/createPlaylist/{genre}")
//    public ResponseEntity createPlaylistByGenre (@PathVariable String genre) {
//        service.createPlaylistByGenre(genre);
//        return new ResponseEntity(HttpStatus.OK);
//    }

    //    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/createPlaylist")
    public ResponseEntity createPlaylistByGenre (@RequestBody PlaylistDto playlistDto) {
        service.generatePlaylist(playlistDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/getPlaylists")
    public Iterable<Playlist> getAllBeers() {
        return service.getAllPlaylists();
    }
}
