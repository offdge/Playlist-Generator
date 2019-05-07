package com.example.playlistgenerator.services;

import com.example.playlistgenerator.models.User;
import com.example.playlistgenerator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void adminUpdateUsers(User user){
        User userToEdit = userRepository.findById(user.getId()).get();
        userToEdit.setEmail(user.getEmail());
        userRepository.save(userToEdit);
    }

}
