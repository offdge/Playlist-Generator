package com.example.playlistgenerator.controllers;

import com.example.playlistgenerator.models.User;
import com.example.playlistgenerator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/adminUpdateUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity adminUpdateUsers(@RequestBody User user) {
        service.adminUpdateUsers(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/adminDeleteUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity adminDeletePlaylist(@PathVariable long id) {
        service.adminDeleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/makeAdmin/{id}")
    public ResponseEntity makeAdmin(@PathVariable long id){
        service.makeAdmin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/removeAdmin/{id}")
    public ResponseEntity removeAdmin(@PathVariable long id){
        service.removeAdmin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
