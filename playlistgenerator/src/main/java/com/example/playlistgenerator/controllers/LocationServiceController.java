package com.example.playlistgenerator.controllers;

import com.example.playlistgenerator.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/distance")
public class LocationServiceController {

    private LocationService service;

    @Autowired
    public LocationServiceController(LocationService service) {
        this.service = service;
    }

    @GetMapping("/{startPoint}/{endPoint}")
    public long getLocation(@PathVariable String startPoint, @PathVariable String endPoint) {
            return service.getTravelDuration(startPoint, endPoint);
    }
}
