package com.example.musicdownload.repositories;

import com.example.musicdownload.models.Genre;
import com.example.musicdownload.models.Track;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends CrudRepository<Track, Long> {
}
