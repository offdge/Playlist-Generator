package com.example.playlistgenerator.controllers;

import com.example.playlistgenerator.models.User;
import com.example.playlistgenerator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    private UserService service;

    @Autowired
    public UserRestController(UserService service) {
        this.service = service;
    }

    @GetMapping("/getUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping("/adminUpdatePlaylist")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity adminUpdateUsers(@RequestBody User user) {
        service.adminUpdateUsers(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
