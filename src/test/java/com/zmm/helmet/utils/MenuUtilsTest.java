package com.zmm.helmet.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


class MenuUtilsTest {

    @Test
    void findAll(){
        System.out.println(MenuUtils.menu_admin());
        System.out.println("================");
        System.out.println(MenuUtils.menu_super());
        System.out.println("================");
        System.out.println(MenuUtils.menu_device());
    }

}
