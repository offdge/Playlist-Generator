package com.example.musicdownload.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GenresList {

    @JsonProperty("data")
    private List<Genre> genres;

    public GenresList(){
        genres = new ArrayList<>();
    }
}
