package com.example.mybatisplusdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.mybatisplusdemo.dao.UserDao;
import com.example.mybatisplusdemo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisPlusDemoUpdateTests {

    @Autowired
    private UserDao userDao;

    /**
     * 使用Wrapper自定义where条件
     */
    @Test
    public void test1() {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getName,"变态叮当").eq(User::getAge,27);
        User user=new User();
        user.setAge(28);
        user.setEmail("变态叮当更加变态@.com");
        int update = userDao.update(user, lambdaUpdateWrapper);
        System.out.println("修改行数："+update);
    }

    /**
     * 在Wrapper中定义where条件和set字段
     */
    @Test
    public void test2() {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(User::getName,"茶王人尚").eq(User::getAge,28).set(User::getAge,29).set(User::getEmail,"广东茶王人尚@.com");
        int update = userDao.update(null, lambdaUpdateWrapper);
        System.out.println("修改行数："+update);
    }


}
