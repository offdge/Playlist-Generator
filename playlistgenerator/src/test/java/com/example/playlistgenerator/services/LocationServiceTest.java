package com.example.playlistgenerator.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

//    @Test
//    public void testGetTravelDuration() throws NullPointerException {
//        long result = locationService.getTravelDuration("Sofia", "Plovdiv");
//        Assert.assertEquals(91L, result);
//    }

    @Test(expected = NullPointerException.class)
    public void getTravelDurationThrowsWhenEmptyInput() {
        locationService.getTravelDuration("", "");
    }
}

