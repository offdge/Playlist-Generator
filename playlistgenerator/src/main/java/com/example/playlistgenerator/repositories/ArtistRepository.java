package com.example.playlistgenerator.repositories;

import com.example.playlistgenerator.models.Artist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository  extends CrudRepository<Artist, Long> {
}
