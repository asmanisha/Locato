package com.locato.locato.repository;

import com.locato.locato.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find users by location
    List<User> findByLocation(String location);

    // Find user by name (for login)
    User findByName(String name);
}
