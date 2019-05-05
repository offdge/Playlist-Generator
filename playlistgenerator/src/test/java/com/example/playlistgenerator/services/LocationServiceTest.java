package com.example.playlistgenerator.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class LocationServiceTest {
    @Mock
    RestTemplate restTemplate;
    @InjectMocks
    LocationService locationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void getCoordinatesAsString_Should_Throw_Exception_If_Not_Exist() throws NoSuchFieldException {
        locationService.getCoordinatesAsString("test");
    }
}

