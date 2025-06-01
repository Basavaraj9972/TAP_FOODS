package com.tap.com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tap.com.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	  @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
	  UserEntity findByEmail(@Param("email") String email);
	  
	  @Query("SELECT u FROM UserEntity u WHERE u.email = :email and u.pin = :pin")
	  UserEntity findByEmailPin(@Param("email") String email, @Param("pin") Integer pin);

	  @Query("SELECT u FROM UserEntity u WHERE u.phone = :phone")
	  UserEntity findByPhone(@Param("phone") String phone);
	  
	  @Query("SELECT u FROM UserEntity u WHERE u.phone = :phone and u.pin = :pin")
	  UserEntity findByPhonePin(@Param("phone") String phone, @Param("pin") Integer pin );
	  
	  @Query("SELECT u FROM UserEntity u WHERE u.email = :email and u.username = :username")
	  UserEntity findByEmailUserName(@Param("email") String email, @Param("username") String username);
	  
	  @Query("SELECT u FROM UserEntity u WHERE u.email = :email and u.password = :password")
	  UserEntity findByEmailPassword(@Param("email") String email, @Param("password") String password);
	  
	  @Query("SELECT u FROM UserEntity u WHERE u.username = :username and u.password = :password")
	  UserEntity findByUserNamePassword(@Param("username") String username, @Param("password") String password);
	  
	  @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
	  Optional<UserEntity> findUserByUserName(@Param("username") String username);

}
