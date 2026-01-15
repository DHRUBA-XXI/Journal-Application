package com.DhrubaStudio.journalApp.service;

import com.DhrubaStudio.journalApp.entity.User;
import com.DhrubaStudio.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveUser(User user){
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Exception",e);
            return false;
        }
    }

    public boolean saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Exception",e);
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

    public User findbyUserName(String username){
        return userRepository.findByUserName(username);
    }

    public void deletebyUsername(String username){
        userRepository.delete(userRepository.findByUserName(username));
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }


    public Optional<User> findbyId(ObjectId Id){
        return userRepository.findById(Id);
    }

    public void deletebyId(ObjectId Id){
        userRepository.deleteById(Id);
    }
}
