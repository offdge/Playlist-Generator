package com.example.playlistgenerator.repositories;

import com.example.playlistgenerator.models.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository  extends CrudRepository<Genre, Long> {
}
