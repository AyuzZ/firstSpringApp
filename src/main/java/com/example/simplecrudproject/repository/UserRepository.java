package com.example.simplecrudproject.repository;

import com.example.simplecrudproject.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public Optional<User> getUserByUsername(String username);

    @Modifying
    @Query("DELETE FROM User u WHERE u.username = ?1")
    public void deleteUserByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.gender = ?2, u.age = ?3  WHERE u.username= ?1")
    public void updateUser(String username, String gender, String age);

}
