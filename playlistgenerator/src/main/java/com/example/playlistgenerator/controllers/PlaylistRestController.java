package com.example.playlistgenerator.controllers;

import com.example.playlistgenerator.models.Genre;
import com.example.playlistgenerator.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/playlist")
public class PlaylistRestController {
    private PlaylistService service;

    @Autowired
    public PlaylistRestController(PlaylistService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/createPlaylist/{genre}")
    public ResponseEntity createBeer (String genre) {
        service.createPlaylistByGenre(genre);
        return new ResponseEntity(HttpStatus.OK);
    }
}
