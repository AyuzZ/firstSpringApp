package com.example.simplecrudproject.service.impl;

import com.example.simplecrudproject.exception.UserAlreadyExistsException;
import com.example.simplecrudproject.exception.UserNotFoundException;
import com.example.simplecrudproject.model.User;
import com.example.simplecrudproject.repository.UserRepository;
import com.example.simplecrudproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUser(username);
        var springUser = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
        return springUser;
    }

    @Override
    public boolean doesUsernameExist(String username) {
        boolean doesUsernameExist = false;
        Optional<User> user = userRepository.getUserByUsername(username);
        if(user.isPresent()){ //returns true if user is not empty
            doesUsernameExist = true;
        }
        return doesUsernameExist;
    }

    @Override
    public void createUser(User user) {
        if(doesUsernameExist(user.getUsername())){
            throw new UserAlreadyExistsException("User Account Already Exists. Try new Username.");
        }else{
            userRepository.save(user);
        }
    }

    @Override
    public User getUser(String username) {
        if(doesUsernameExist(username)){
            Optional<User> userOptional = userRepository.getUserByUsername(username);
            User user = userOptional.get(); //Won't throw null exception as doesUsernameExist checks for it.
            return user;
        }else{
            throw new UserNotFoundException("User Account doesn't exist.");
        }
    }

    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void updateUser(User user) {
        String username = user.getUsername();
        String gender = user.getGender();
        String age = user.getAge();
        userRepository.updateUser(username, gender, age);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
    }

}
