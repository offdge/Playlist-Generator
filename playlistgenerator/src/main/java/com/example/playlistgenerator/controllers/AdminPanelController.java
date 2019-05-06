package com.example.playlistgenerator.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AdminPanelController {

    @GetMapping("/adminPanel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getAdminPanel() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
