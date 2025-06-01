package com.tap.com.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tap.com.dto.UserDTO;
import com.tap.com.model.UserEntity;
import com.tap.com.service.EmailService;
import com.tap.com.service.PhoneService;
import com.tap.com.serviceInf.UserServiceDao;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
public class EmailController {

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private PhoneService phoneService;
    
    @Autowired
    private UserServiceDao userServiceDao;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
    	return new ResponseEntity<String>(emailService.sendOtp(email), HttpStatus.OK);
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<Boolean> verifyOtp(@RequestParam String email, @RequestParam String otp) {
    	return new ResponseEntity<Boolean>(emailService.verifyOtp(email,otp), HttpStatus.OK);
    }
        
    @PostMapping("/send-otp-Phone")
    public ResponseEntity<String> sendOtpToMobieNumber(@RequestParam String phone) {
    	return new ResponseEntity<String>(phoneService.sendOtp(phone),HttpStatus.OK);
    }
    
    @GetMapping("/login")	
    public ResponseEntity<UserEntity> login(@RequestBody UserEntity user) {
    	System.out.println("login calling");
    	return new ResponseEntity<UserEntity>(user,HttpStatus.OK);
    }
    
    @PostMapping("/saveUser")
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity user ) {
    	return new ResponseEntity<UserEntity>(userServiceDao.saveUser(user),HttpStatus.CREATED);
    }
    
    @PostMapping("/saveUserByPhone")
    public ResponseEntity<UserEntity> saveUserByPhone(@RequestBody UserEntity user ) {
    	return new ResponseEntity<UserEntity>(userServiceDao.saveUser(user),HttpStatus.CREATED);
    }
    
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer userId) {
    	return new ResponseEntity<String>(userServiceDao.deleteUser(userId),HttpStatus.OK);
    }
    
//    @GetMapping("/getUser/{id}")
    @GetMapping(value = "/getUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Integer UserId) {
    	System.out.println("UserId : "+UserId);
    	return new ResponseEntity<UserDTO>(userServiceDao.getUser(UserId),HttpStatus.OK);
    }

    @PutMapping("/updatedUser/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("id") Integer UserId,@RequestBody UserEntity user ) {
//    	userServiceDao.fi
    	return new ResponseEntity<UserEntity>(userServiceDao.updateUser(UserId,user),HttpStatus.OK);
    }
    
    @PutMapping("/resetPinByEmail")
    private ResponseEntity<UserEntity> resetPinByEmail(@RequestParam String email, @RequestParam Integer pin) {
    	return new ResponseEntity<UserEntity>(userServiceDao.resetPINByEmail(email, pin),HttpStatus.OK);
    }
    
    @PutMapping("/resetPinByPhone")
    private ResponseEntity<UserEntity> resetPinByPhone(@RequestParam String phone, @RequestParam Integer pin) {
    	return new ResponseEntity<UserEntity>(userServiceDao.resetPINByPhone(phone, pin),HttpStatus.OK);
    }
    
    @PutMapping("/resetPassword")
    private ResponseEntity<UserEntity> resetPasswordByEmail(@RequestParam String email, @RequestParam String password) {
    	return new ResponseEntity<UserEntity>(userServiceDao.resetPasswordByEmail(email, password),HttpStatus.OK);
    }
    
    @GetMapping("/uniqueRecordByEmail")
    public ResponseEntity<UserEntity> getUserByEmail(@RequestParam String email) {
    	UserEntity user = new UserEntity();
    	user = null;
    	user = userServiceDao.findByEmail(email);
    	System.out.println(user);
    	if(user != null) {
    		System.out.println(user.getUserId());
    		System.out.println(user.getUsername());
    		System.out.println(user.getEmail());
    		System.out.println(user.getPhone());
    	}
    	else {
    		System.out.println("user is not present");
    	}
    	return new ResponseEntity<UserEntity>(user,HttpStatus.OK);
    }
    
    @GetMapping("/uniqueRecordByPhone")
//    produces = {org.springframework.http.MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_ATOM_XML_VALUE},
//	consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_ATOM_XML_VALUE})
    public ResponseEntity<UserEntity> getUserByPhone(@RequestParam String phone) {
    	System.out.println(phone);
    	UserEntity user = new UserEntity();
    	user = null;
    	user = userServiceDao.findByPhone(phone);
    	System.out.println(user);
    	if(user != null) {
    		System.out.println(user.getUserId());
    		System.out.println(user.getUsername());
    		System.out.println(user.getEmail());
    		System.out.println(user.getPhone());
    	}
    	else {
    		System.out.println("user is not present");
    	}
    	return new ResponseEntity<UserEntity>(user,HttpStatus.OK);
    }
    
    @GetMapping("/uniqueRecordByEmailName")
    public ResponseEntity<UserEntity> getUserByEmailName(@RequestParam String email, @RequestParam String username) {
    	System.out.println(email);
    	System.out.println(username);
    	UserEntity user = new UserEntity();
    	user = null;
    	user = userServiceDao.findByEmailUserName(email, username);
    	System.out.println(user);
    	if(user != null) {
    		System.out.println(user.getUserId());
    		System.out.println(user.getUsername());
    		System.out.println(user.getEmail());
    		System.out.println(user.getPhone());
    	}
    	else {
    		System.out.println("user is not present");
    	}
    	return new ResponseEntity<UserEntity>(user,HttpStatus.OK);
    }
    
    @GetMapping("/uniqueRecordByEmailPassword")
    public ResponseEntity<UserEntity> getUserByEmailPassword(@RequestParam String email, @RequestParam String password) {
    	System.out.println(email);
    	System.out.println(password);
    	UserEntity user = new UserEntity();
    	user = null;
    	user = userServiceDao.findByEmailPassword(email, password);
    	System.out.println(user);
    	if(user != null) {
    		System.out.println(user.getUserId());
    		System.out.println(user.getUsername());
    		System.out.println(user.getEmail());
    		System.out.println(user.getPhone());
    	}
    	else {
    		System.out.println("user is not present");
    	}
    	return new ResponseEntity<UserEntity>(user,HttpStatus.OK);
    }
    
    @GetMapping("/uniqueRecordByEmailPIN")
    public ResponseEntity<UserEntity> getUserByEmailPIN(@RequestParam String email, @RequestParam Integer pin) {
    	System.out.println(email);
    	System.out.println(pin);
    	UserEntity user = new UserEntity();
    	user = null;
    	user = userServiceDao.findByEmailPin(email, pin);
    	System.out.println(user);
    	if(user != null) {
    		System.out.println(user.getUserId());
    		System.out.println(user.getUsername());
    		System.out.println(user.getEmail());
    		System.out.println(user.getPhone());
    	}
    	else {
    		System.out.println("user is not present");
    	}
    	return new ResponseEntity<UserEntity>(user,HttpStatus.OK);
    }
    
    @GetMapping("/uniqueRecordByPhonePIN")
    public ResponseEntity<UserEntity> getUserByPhonePIN(@RequestParam String phone, @RequestParam Integer pin) {
    	System.out.println(phone);
    	System.out.println(pin);
    	UserEntity user = new UserEntity();
    	user = null;
    	user = userServiceDao.findByPhonePin(phone, pin);
    	System.out.println(user);
    	if(user != null) {
    		System.out.println(user.getUserId());
    		System.out.println(user.getUsername());
    		System.out.println(user.getEmail());
    		System.out.println(user.getPhone());
    	}
    	else {
    		System.out.println("user is not present");
    	}
    	return new ResponseEntity<UserEntity>(user,HttpStatus.OK);
    }
}
