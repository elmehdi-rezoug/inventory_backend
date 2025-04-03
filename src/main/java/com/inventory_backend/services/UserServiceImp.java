package com.inventory_backend.services;

import com.inventory_backend.entities.User;
import com.inventory_backend.repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    UserRepo userRepo;

    public UserServiceImp(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("user with id " + id + " not found");
        }
    }

    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User updateUser(Long id, User userUpdates) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            // Only update fields that are provided (not null)
            if (userUpdates.getName() != null) {
                existingUser.setName(userUpdates.getName());
            }

            if (userUpdates.getEmail() != null) {
                existingUser.setEmail(userUpdates.getEmail());
            }

            if (userUpdates.getPassword() != null) {
                existingUser.setPassword(userUpdates.getPassword());
            }

            return userRepo.save(existingUser);
        } else {
            throw new EntityNotFoundException("user with id " + id + " not found");
        }
    }
    @Override
    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepo.delete(user);
        } else {
            throw new EntityNotFoundException("user with id " + id + " not found");
        }
    }
}
