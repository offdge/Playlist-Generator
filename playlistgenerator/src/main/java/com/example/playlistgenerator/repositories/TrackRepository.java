package com.example.playlistgenerator.repositories;

import com.example.playlistgenerator.models.Track;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends CrudRepository<Track, Long> {
}
