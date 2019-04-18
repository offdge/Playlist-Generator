package com.example.playlistgenerator.repositories;

import com.example.playlistgenerator.models.Album;
import org.springframework.data.repository.CrudRepository;

public interface AlbumRepository extends CrudRepository<Album, Long> {
}
