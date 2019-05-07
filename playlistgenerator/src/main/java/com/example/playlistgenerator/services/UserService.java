package com.example.playlistgenerator.services;

import com.example.playlistgenerator.models.Role;
import com.example.playlistgenerator.models.RoleName;
import com.example.playlistgenerator.models.User;
import com.example.playlistgenerator.repositories.RoleRepository;
import com.example.playlistgenerator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Component
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Iterable<User> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return users;
    }

    public void adminUpdateUsers(User user){
        User userToEdit = userRepository.findById(user.getId()).get();
        userToEdit.setEmail(user.getEmail());
        userRepository.save(userToEdit);
    }

    public void adminDeleteUser(long id) {
        userRepository.deleteById(id);
    }

    public void makeAdmin(long id) {
        User userToEdit = userRepository.findById(id).get();
        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));

        userToEdit.getRoles().add(adminRole);

        userRepository.save(userToEdit);
    }

    public void removeAdmin(long id) {
        User userToEdit = userRepository.findById(id).get();

        Role roleToDelete = null;

        for (Role role: userToEdit.getRoles()) {
            if(role.getName().equals(RoleName.ROLE_ADMIN)){
                roleToDelete = role;
            }
        }

        if(roleToDelete != null){
            userToEdit.getRoles().remove(roleToDelete);
            userRepository.save(userToEdit);
        }

    }
}
