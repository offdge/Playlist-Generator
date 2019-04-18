package com.example.playlistgenerator.repositories;

import com.example.playlistgenerator.models.Artist;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository  extends CrudRepository<Artist, Long> {
}
