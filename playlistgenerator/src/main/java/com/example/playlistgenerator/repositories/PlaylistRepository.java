package com.example.playlistgenerator.repositories;

import com.example.playlistgenerator.models.Playlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends CrudRepository<Playlist, Long> {

//    Optional<Playlist> findByGenre(String genre);
}
