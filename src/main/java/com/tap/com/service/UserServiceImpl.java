package com.tap.com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tap.com.dto.UserDTO;
import com.tap.com.exception.ResourceNotFoundException;
import com.tap.com.model.UserEntity;
import com.tap.com.repository.UserRepository;
import com.tap.com.serviceInf.UserServiceDao;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserServiceDao {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserEntity findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public UserEntity findByPhone(String phone) {
		return userRepository.findByPhone(phone);
	}

	@Override
	public UserEntity findByEmailUserName(String phone,String username) {
		return userRepository.findByEmailUserName(phone, username);
	}

	@Override
	public UserEntity findByEmailPassword(String email, String password) {
		return userRepository.findByEmailPassword(email, password);
	}

	@Override
	public UserEntity updateUser(Integer userId, UserEntity user) {
		UserEntity userExist =  userRepository.findById(userId).orElseThrow(
				()->new ResourceNotFoundException("User","ID",userId));
		userExist.setUsername(user.getUsername());
		userExist.setEmail(user.getEmail());
		userExist.setPassword(user.getPassword());
		userExist.setPin(user.getPin());
		return userRepository.save(userExist);
	}

	@Override
	public UserEntity resetPINByEmail(String email, Integer pin) {
		UserEntity user = null;
		user =	userRepository.findByEmail(email);
		if(user == null) {
			new ResourceNotFoundException("User","Email",user);
		}
		user.setPin(pin);
		return userRepository.save(user);
	}

	@Override
	public UserEntity resetPINByPhone(String phone, Integer pin) {
		UserEntity user = userRepository.findByPhone(phone);
		user.setPin(pin);
		return userRepository.save(user);
	}

	@Override
	public UserEntity resetPasswordByEmail(String email, String password) {
		UserEntity user = userRepository.findByEmail(email);
		user.setPassword(password);
		return userRepository.save(user);
	}

	@Override
	public UserEntity findByEmailPin(String email, Integer pin) {
		UserEntity user = null;
		user = userRepository.findByEmailPin(email, pin);
		return user;
	}

	@Override
	public UserEntity findByPhonePin(String phone, Integer pin) {
		UserEntity user = null;
		user = userRepository.findByPhonePin(phone, pin);
		return user;
	}

	
	@Override
	public UserEntity saveUser(UserEntity user) {
		return userRepository.save(user);
	}
	
	@Override
	public UserEntity saveUserByPhone(UserEntity user) {
		return userRepository.save(user);
	}

	@Override
	public UserEntity findByUserNamePassword(String username, String password) {
		return userRepository.findByUserNamePassword(username, password);
	}

//	@Override
//	public Optional<UserEntity> findUserByUserName(String username) {
//		return userRepository.findUserByUserName(username);
//	}
	
	 @Override
	    public Optional<UserEntity> findUserByUserName(String username) {
	        return userRepository.findUserByUserName(username); // this must also return Optional<UserEntity>
	    }

	@Override
	public UserDTO getUser(Integer userId) {
	    Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
	    return userEntityOptional.map(UserDTO::new).orElse(null);
	}

	@Override
	public String deleteUser(Integer userId) {
		Optional<UserEntity> user = userRepository.findById(userId);
		if(user.isPresent()) {
			userRepository.deleteById(userId);
			return "user delete successfully";
		}
		return "user is not exist";
	}
}
