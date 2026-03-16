package com.DhrubaStudio.journalApp.controller;
import com.DhrubaStudio.journalApp.api.response.WeatherResponse;
import com.DhrubaStudio.journalApp.entity.User;
import com.DhrubaStudio.journalApp.service.QuotesService;
import com.DhrubaStudio.journalApp.service.UserService;
import com.DhrubaStudio.journalApp.service.WeatherService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final WeatherService weatherService;
    private final QuotesService quotesService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService,
                          WeatherService weatherService,
                          QuotesService quotesService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.weatherService = weatherService;
        this.quotesService = quotesService;
        this.passwordEncoder = passwordEncoder;
    }

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateUserPassword(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User existingUser = userService.findbyUserName(username);

        if(user.getUserName() !=null && !user.getUserName().isEmpty()){
            existingUser.setUserName(user.getUserName());
        }
        if(user.getPassword() !=null && !user.getPassword().isEmpty()){
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if(user.getEmail() !=null && !user.getEmail().isEmpty()){
            existingUser.setEmail(user.getEmail());
        }
        existingUser.setSentimentAnalysisConsent(user.getSentimentAnalysisConsent());

        userService.saveUser(existingUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userService.deletebyUsername(username);
        return new ResponseEntity<>("Your account has been deleted.",HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        String greet = "";
        try{
            WeatherResponse weatherResponse = weatherService.getWeatherByCity("Kolkata");
            String Quote = quotesService.getQuote();
            if(weatherResponse != null){
                greet = ", it feels like " +
                        weatherResponse.getCurrent().getFeelslike()
                        +"°C at Kolkata." + " Here's a quote for you:\n"+
                        Quote;
            }
        }catch(Exception e){
            log.error("API error: {}",e.getMessage());
            greet = " to your journal !";
        }
        return new ResponseEntity<>("Hey welcome "+ username + greet ,HttpStatus.OK);
    }
}
