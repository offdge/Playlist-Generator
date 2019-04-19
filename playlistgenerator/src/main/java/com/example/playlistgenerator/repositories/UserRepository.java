package com.example.playlistgenerator.repositories;

import com.example.playlistgenerator.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
