package com.DhrubaStudio.journalApp.service;
import com.DhrubaStudio.journalApp.entity.User;
import com.DhrubaStudio.journalApp.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean saveNewUser(User user){
        if(userRepository.findByUserName(user.getUserName()) != null){
            log.warn("Signup failed: Username '{}' is already taken.", user.getUserName());
            return false;
        }
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("ExceptionL: ",e);
            return false;
        }
    }

    public boolean saveNewAdmin(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER","ADMIN"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Exception",e);
            return false;
        }
    }

    public boolean saveUser(User user){
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Exception",e);
            return false;
        }
    }

    public User findbyUserName(String username){
        return userRepository.findByUserName(username);
    }

    public void deletebyUsername(String username){
        userRepository.delete(userRepository.findByUserName(username));
    }

    public List<User> getAllusers(){
        return userRepository.findAll();
    }

}
