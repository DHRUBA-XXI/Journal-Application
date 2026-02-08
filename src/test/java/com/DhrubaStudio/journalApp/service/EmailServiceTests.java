package com.DhrubaStudio.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void testEmailService() {
        emailService.sendEmail("dhrubamaitra22@gmail.com",
                "Spring Mail service testing",
                "It's working great");
    }
}
