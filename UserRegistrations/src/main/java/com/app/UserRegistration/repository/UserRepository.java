package com.app.UserRegistration.repository;

import com.app.UserRegistration.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
