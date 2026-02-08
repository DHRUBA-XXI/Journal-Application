package com.DhrubaStudio.journalApp.controller;
import com.DhrubaStudio.journalApp.entity.User;
import com.DhrubaStudio.journalApp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService  userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getAllusers();
        if (!users.isEmpty() && users != null){
            return new ResponseEntity<>(users,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-new-admin")
    public ResponseEntity<?> addNewAdmin(@RequestBody User user){
        userService.saveNewAdmin(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
