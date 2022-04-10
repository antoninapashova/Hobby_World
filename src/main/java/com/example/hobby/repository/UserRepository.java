package com.example.hobby.repository;

import com.example.hobby.model.dto.UserDTO;
import com.example.hobby.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Collection<Object> findByUsernameIgnoreCase(String username);

    Optional<User> findById(Long id);

    Optional<User> findUserByUsername(String username);
    void deleteById(Long id);
     Optional<User> findByUsernameAndPassword(String username, String password);
}
