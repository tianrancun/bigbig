package com.bigbig;


import com.bigbig.entity.User;
import com.bigbig.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
@SpringBootTest
public class TestUser {
    @Autowired
    private UserRepository userRepository;
    @Test
    public void testFindUser() throws Exception {
        Optional<User> user =  userRepository.findByClaimId(1);
        user.orElseThrow(()-> new Exception("user not exist"));
        System.out.println(user.get());

    }

}