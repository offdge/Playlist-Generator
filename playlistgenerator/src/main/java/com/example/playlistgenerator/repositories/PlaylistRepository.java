package com.example.playlistgenerator.repositories;

import com.example.playlistgenerator.models.Playlist;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
}
