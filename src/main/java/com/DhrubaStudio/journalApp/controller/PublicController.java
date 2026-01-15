package com.DhrubaStudio.journalApp.controller;

import com.DhrubaStudio.journalApp.entity.User;
import com.DhrubaStudio.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        userService.saveNewUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
