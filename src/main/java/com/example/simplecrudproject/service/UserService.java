package com.example.simplecrudproject.service;

import com.example.simplecrudproject.model.User;

import java.util.List;

public interface UserService {

    public abstract boolean doesUsernameExist(String username);

    public abstract void createUser(User user);

    public abstract User getUser(String username);

    public abstract List<User> getUsers();

    public abstract void updateUser(User user);

    public abstract void deleteUser(String username);

}
