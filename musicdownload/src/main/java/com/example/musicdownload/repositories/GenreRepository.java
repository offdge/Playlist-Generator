package com.example.musicdownload.repositories;

import com.example.musicdownload.models.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {
    boolean existsByName(String name);

    Optional<Genre> findByName(String name);
}
