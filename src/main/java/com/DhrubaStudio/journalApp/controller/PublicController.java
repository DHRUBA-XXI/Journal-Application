package com.DhrubaStudio.journalApp.controller;
import com.DhrubaStudio.journalApp.config.JwtUtilities;
import com.DhrubaStudio.journalApp.entity.User;
import com.DhrubaStudio.journalApp.service.UserDetailsServiceImpl;
import com.DhrubaStudio.journalApp.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtilities jwtUtilities;

    @Autowired
    public PublicController(UserService userService,
                            AuthenticationManager authenticationManager,
                            UserDetailsServiceImpl userDetailsService,
                            JwtUtilities jwtUtilities) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtilities = jwtUtilities;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody User user){
        boolean isSaved = userService.saveNewUser(user);
        if(isSaved != true){
            return new ResponseEntity<>("Username is already taken. Please choose another one.",HttpStatus.CONFLICT);
        }else{
            return new ResponseEntity<>("Your account has been successfully created.",HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user){
        try {
            authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtilities.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.warn("Error while creating JWT: {}", e.getMessage());
            return new ResponseEntity<>("Incorrect Username or Password.",HttpStatus.UNAUTHORIZED);
        }
    }
}
