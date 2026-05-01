package com.vcube.Security.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vcube.Security.Entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User>findByUsername(String username);

}
