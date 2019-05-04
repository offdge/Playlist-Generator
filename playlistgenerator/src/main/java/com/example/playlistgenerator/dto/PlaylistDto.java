package com.example.playlistgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDto {
    private String title;
    private List<GenreDto> genres;
    private String startPoint;
    private String endPoint;
    private boolean useTopRanks;
    private boolean allowSameArtist;
}
