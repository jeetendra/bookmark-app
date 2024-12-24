package com.jeet.bookmarkapp.service;

import com.jeet.bookmarkapp.entity.User;

import java.util.List;

public interface UserService {
    public User findByUsername(String username);

    public User findByEmail(String email);

    public User findByUsernameAndPassword(String username, String password);

    public User findByEmailAndPassword(String email, String password);

    public User findById(int id);

    public List<User> findAll();

    public User save(User user, Long id);

    public void delete(int id);

}
