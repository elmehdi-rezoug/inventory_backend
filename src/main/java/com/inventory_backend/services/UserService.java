package com.inventory_backend.services;

import com.inventory_backend.entities.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUserById(long id);

    User addUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);
}
