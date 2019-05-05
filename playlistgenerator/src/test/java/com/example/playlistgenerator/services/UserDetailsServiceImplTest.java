package com.example.playlistgenerator.services;

import com.example.playlistgenerator.models.User;
import com.example.playlistgenerator.repositories.UserRepository;
import com.example.playlistgenerator.services.UserDetailsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImplTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsername() {
        User user = new User();
        user.setName("testUser");
        Mockito.when(userRepository.findByUsername("testUser")).thenReturn(java.util.Optional.of(user));

        Assert.assertEquals(user, userRepository.findByUsername("testUser").get());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_Should_ThrowUserNotFoundException_If_Not_Exist() {
        userDetailsServiceImpl.loadUserByUsername("testUser");
    }
}

