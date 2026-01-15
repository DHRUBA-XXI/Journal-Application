package com.DhrubaStudio.journalApp.service;
import com.DhrubaStudio.journalApp.repository.UserRepository;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @ValueSource(strings = {
            "Dhruv22",
            "Gobinda46",
            "Arka07",
            "Debarjun47"
    })
    public void TestfindByUserName(String name){
        assertNotNull(userRepository.findByUserName(name),"Failed for: "+name);
    }

}
