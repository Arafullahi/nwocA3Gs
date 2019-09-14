package com.nwoc.a3gs.group.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nwoc.a3gs.group.app.dto.PasswordResetTokenDTO;
import com.nwoc.a3gs.group.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> ,PagingAndSortingRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User findUserByEmail(String email);
}