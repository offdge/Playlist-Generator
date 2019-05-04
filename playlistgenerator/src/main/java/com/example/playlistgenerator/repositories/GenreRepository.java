package com.example.playlistgenerator.repositories;

import com.example.playlistgenerator.models.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository  extends CrudRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
}
