package com.example.playlistgenerator.repositories;

import com.example.playlistgenerator.models.Track;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends CrudRepository<Track, Long> {
    @Query(value="select * from tracks join genres as g on tracks.genre_id = g.genre_id where g.genre_name = ?1", nativeQuery = true)
    Iterable<Track> findAllByGenreName(String genre);
}
