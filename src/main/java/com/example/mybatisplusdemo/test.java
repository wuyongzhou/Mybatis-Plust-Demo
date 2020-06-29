package com.example.mybatisplusdemo;

import com.example.mybatisplusdemo.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class test {
    @Bean
    public User user(){
        System.out.println("create user bean");
        User user=new User();
        user.setName("test");
        user.setAge(18);
        return user;
    }

    @Bean
    public User print(User user){
        System.out.println(user.getName()+","+user.getAge());
        return user;
    }
}
