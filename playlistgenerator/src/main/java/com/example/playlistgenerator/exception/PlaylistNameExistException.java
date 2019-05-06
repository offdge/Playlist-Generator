package com.example.playlistgenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlaylistNameExistException extends RuntimeException {
    public PlaylistNameExistException(String exception) {
        super(exception);
    }
}
