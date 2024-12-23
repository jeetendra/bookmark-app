package com.jeet.bookmarkapp.service.impl;

import com.jeet.bookmarkapp.entity.User;
import com.jeet.bookmarkapp.repository.UserRepository;
import com.jeet.bookmarkapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);

//        if (user != null) {
//            user.getRoles().size(); // Trigger lazy initialization
//        }

        return user;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return null;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user, Long id) {

        if (id == null) {
            return userRepository.save(user);
        }

        Optional<User> savedUser = userRepository.findById(id);
        if (savedUser.isPresent()) {
            var dbUser = savedUser.get();
            dbUser.setUsername(user.getUsername());
            dbUser.setPassword(user.getPassword());
            dbUser.setEmail(user.getEmail());
            return userRepository.save(dbUser);
        }
        //TODO: throw UserNotFoundException.
        return null;
    }


    @Override
    public void delete(int id) {

    }
}
