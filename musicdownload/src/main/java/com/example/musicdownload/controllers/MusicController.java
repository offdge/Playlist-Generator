package com.example.musicdownload.controllers;

import com.example.musicdownload.services.MusicDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/music")
public class MusicController {

    @Autowired
    private MusicDownloadService musicDownloadService;

    @GetMapping(path="/populate/genres")
    public void populateGenres() {
        musicDownloadService.populateGenres();
    }

    @GetMapping(path="/populate/tracks/{genre}")
    public void populateTracks(@PathVariable String genre) {
        //This is done so we can work with genres like "rap/hip hop" using dash instead of slash in urls
        genre = genre.replace('-', '/');

        musicDownloadService.populateTracks(genre);
    }
}

