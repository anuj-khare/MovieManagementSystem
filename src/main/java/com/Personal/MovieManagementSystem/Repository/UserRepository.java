package com.Personal.MovieManagementSystem.Repository;

import com.Personal.MovieManagementSystem.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser,Long> {
    Optional<MyUser> findByUserName(String username);
}
