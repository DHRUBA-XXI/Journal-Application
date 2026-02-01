package com.DhrubaStudio.journalApp.service;

import com.DhrubaStudio.journalApp.repository.UserRepositoryImpl;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    public void testIfEmailExists(){
        assertTrue(userRepositoryImpl.getUsersForSentiAnalysis().isEmpty());
    }

}
