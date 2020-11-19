package com.zmm.helmet.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAll(){
        System.out.println(userRepository.findAll());
    }

    @Test
    void findByUsernameAndAccess_level(){
        System.out.println(userRepository.findByUsernameAndAccess_level("admin",""));
    }

    @Test
    void findById() {
        System.out.println(userRepository.findByUserid(Long.valueOf(1)));
    }

}
