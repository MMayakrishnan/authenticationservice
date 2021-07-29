package com.hotel.authenticationservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.authenticationservice.pojo.User;

import lombok.NonNull;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByEmailId(@NonNull String emailId);

}
