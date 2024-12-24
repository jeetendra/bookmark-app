package com.jeet.bookmarkapp.repository;

import com.jeet.bookmarkapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
