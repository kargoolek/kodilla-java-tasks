package com.crud.tasks.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminConfigTestSuite {
    @Autowired
    AdminConfig adminConfig;

    @Test
    public void testAdminConfig() {
        //Given, When
        String adminEmail = adminConfig.getAdminMail();

        //Then
        Assert.assertTrue(isValidEmail(adminEmail));
    }

    private boolean isValidEmail(String email) {
            if(!email.contains("@")) return false;
            if(email.indexOf("@") != email.lastIndexOf("@")) return false;
        return true;
    }
}
