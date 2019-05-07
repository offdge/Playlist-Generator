package com.example.playlistgenerator.services;

import com.example.playlistgenerator.models.User;
import com.example.playlistgenerator.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers(){
        Iterable<User> result = userService.getAllUsers();
        Assert.assertEquals(new ArrayList<>(), result);
    }
}
