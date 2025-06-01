package com.tap.com.serviceInf;

import java.util.Optional;

import com.tap.com.dto.UserDTO;
import com.tap.com.model.UserEntity;

public interface UserServiceDao {
	
	UserEntity saveUser(UserEntity user);
	UserEntity saveUserByPhone(UserEntity user);
	UserDTO getUser(Integer userId);
	String deleteUser(Integer userId);
	UserEntity updateUser(Integer userId, UserEntity user);
	UserEntity resetPINByEmail(String email, Integer pin);
	UserEntity resetPINByPhone(String phone, Integer pin);
	UserEntity resetPasswordByEmail(String email, String password);
	UserEntity findByEmail(String email);
	UserEntity findByEmailPin(String email,Integer pin);
	UserEntity findByPhone(String phone);
	UserEntity findByPhonePin(String phone,Integer pin);
	UserEntity findByEmailUserName(String email, String username);
	UserEntity findByEmailPassword(String email,String password);
	UserEntity findByUserNamePassword(String username,String password);
	Optional<UserEntity> findUserByUserName(String username);
}
