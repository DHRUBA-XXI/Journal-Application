package com.DhrubaStudio.journalApp.controller;
import com.DhrubaStudio.journalApp.api.response.WeatherResponse;
import com.DhrubaStudio.journalApp.entity.User;
import com.DhrubaStudio.journalApp.service.QuotesService;
import com.DhrubaStudio.journalApp.service.UserService;
import com.DhrubaStudio.journalApp.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private QuotesService quotesService;

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateUserPassword(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User existingUser = userService.findbyUserName(username);
        existingUser.setUserName(user.getUserName());
        existingUser.setPassword(user.getPassword());
        userService.saveNewUser(existingUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deletebyUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String greet = "";
        WeatherResponse weatherResponse = weatherService.getWeatherByCity("Kolkata");
        String Quote = quotesService.getQuote();
        if(weatherResponse != null){
            greet = ", it feels like " +
                    weatherResponse.getCurrent().getFeelslike()
                    +"°C at Kolkata." + " Here's a quote for you:\n"+
                    Quote;
        }
        return new ResponseEntity<>("Hey welcome "+ username + greet ,HttpStatus.OK);
    }
}
