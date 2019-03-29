package com.bigbig;

import com.bigbig.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author vn0ow6b
 * @Title: TestOptional
 * @Description: TODO
 * @date 2019/3/29 18:52
 */
@Slf4j
public class TestOptional {

    @Test(expected = NoSuchElementException.class)
    public void whenCreateEmptyOptional_thenNull() {
        Optional<User> emptyOpt = Optional.empty();
        emptyOpt.get();
    }

    @Test(expected = NullPointerException.class)
    public void whenCreateOfEmptyOptional_thenNullPointerException() {
        User user = null;
        Optional<User> opt = Optional.of(user);
    }

    @Test
    public void whenCreateOfNullableOptional_thenOk() {
        String name = "John";
        Optional<String> opt = Optional.ofNullable(name);

        assertEquals("John", opt.get());
    }

    @Test
    public void whenCheckIfPresent_thenOk() {
        User user = new User("john@gmail.com", "1234");
        Optional<User> opt = Optional.ofNullable(user);
        assertTrue(opt.isPresent());
        opt.ifPresent( u -> {
            System.out.println(u.getEmail());
            assertEquals(user.getEmail(), u.getEmail());
        });
        assertEquals(user.getEmail(), opt.get().getEmail());
    }

    @Test
    public void whenValueNotNull_thenIgnoreDefault() {
        User user = new User("john","john@gmail.com");
        User user2 = new User("anna", "anna@gmail.com");
        User result = Optional.ofNullable(user).orElse(user2);

        assertEquals("john@gmail.com", result.getEmail());
    }

    @Test
    public void givenEmptyValue_whenCompare_thenOk() {
        User user = null;
        log.info("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        log.info("email {}",result.getEmail());
        log.info("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
        log.info("email {}",result2.getEmail());
    }

    @Test
    public void givenPresentValue_whenCompare_thenOk() {
        User user = new User("john", "john@gmail.com");
        log.info("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        log.info("email {}",result.getEmail());
        log.info("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
        log.info("email {}",result2.getEmail());
    }

    private User createNewUser() {
        log.info("Creating New User");
        return new User("extra", "extra@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenThrowException_thenOk() {
        User user = null;
        User result = Optional.ofNullable(user)
                .orElseThrow( () -> new IllegalArgumentException());
    }

    @Test
    public void whenMap_thenOk() {
        User user = new User("anna", "anna@gmail.com");
        String email = Optional.ofNullable(user)
                .map(u -> u.getEmail()).orElse("default@gmail.com");

        assertEquals(email, user.getEmail());
    }

    @Test
    public void whenFilter_thenOk() {
        User user = new User("anna", "anna@gmail.com");
        Optional<User> result = Optional.ofNullable(user)
                .filter(u -> u.getEmail() != null && u.getEmail().contains("@"));

        assertTrue(result.isPresent());
    }


}