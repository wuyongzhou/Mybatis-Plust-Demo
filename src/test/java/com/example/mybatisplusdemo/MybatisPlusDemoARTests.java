package com.example.mybatisplusdemo;

import com.example.mybatisplusdemo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisPlusDemoARTests {

    @Test
    public void test1() {
        User whereUser=new User();
        whereUser.setId(1L);
        User user = whereUser.selectById();
        System.out.println(user);
    }




}
