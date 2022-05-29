package com.hs.hscontrolinformation;


import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hs.hscontrolinformation.dao.UserDao;
import com.hs.hscontrolinformation.domain.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class HsControlInformationApplicationTests {
    @Autowired
    private UserDao userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    void createUser() {
        User user= new User();
        user.setUsername("alejo");
        user.setPassword(encoder.encode("333"));
        User userReturn= userRepository.save(user);
        assertTrue(userReturn.getUsername().equalsIgnoreCase(user.getUsername()));
    }

}
