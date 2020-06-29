package com.example.mybatisplusdemo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo.dao.UserDao;
import com.example.mybatisplusdemo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisPlusDemoQueryTests {

    @Autowired
    private UserDao userDao;


    @Test
    public void test(){
        User whereUser = new User();
        whereUser.setName("Jack");
        whereUser.setAge(20);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(whereUser);
        List<User> userList = userDao.selectList(queryWrapper);
        userList.forEach(System.out::println);

        /*QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","Jack").eq("age",20);
        List<User> userList = userDao.selectList(queryWrapper);
        userList.forEach(System.out::println);*/
    }

    /**
     * AND 嵌套
     *
     * where name like 王% and (age<30)
     */
    @Test
    public void test1() {
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        //lambda 写法，相当于匿名内部类
        queryWrapper.likeRight("name","变态").and((qw) -> qw.lt("age",30));
        /*queryWrapper.likeRight("name","变态").and(new Consumer<QueryWrapper<User>>() {
            @Override
            public void accept(QueryWrapper<User> o) {
                o.lt("age",30);
            }
        });*/
        List<User> users = userDao.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * OR 嵌套
     *
     * WHERE ((age <= 25 OR email IS NOT NULL) AND create_time IS NOT NULL)
     */
    @Test
    public void test2() {
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        //lambda 写法，相当于匿名内部类
        queryWrapper.nested(qw -> qw.le("age",25).or().isNotNull("email")).isNotNull("create_time");
        List<User> users = userDao.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 排除某些字段
     */
    @Test
    public void test3() {
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        //lambda 写法，相当于匿名内部类
        queryWrapper.select(User.class,info -> !info.getColumn().equals("email")).nested(qw -> qw.le("age",25).or().isNotNull("email")).isNotNull("create_time");
        List<User> users = userDao.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * lambda 表达式 本质上一样，只是在编写SQL时针对的是对象，mybatis-plus底层会自动转换
     */
    @Test
    public void test4() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper.likeRight(User::getName,"变态");
        List<User> users = userDao.selectList(userLambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 自定义SQL 需要参数是Wrapper条件构造器
     */
    @Test
    public void test5() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper.likeRight(User::getName,"变态");
        List<User> users = userDao.selectAll(userLambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * Page分页
     *
     * limit ?,?  起始（偏移量） 0和1是相同的，都认为从第一条数据开始 -  总量
     * Page构造函数有多个重载，其中，isSearchCount指定为false不查询总记录数
     *
     */
    @Test
    public void test6() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper.ge(User::getAge,20);

        Page<User> pageLimit=new Page<>(1,2);
        IPage<User> page = userDao.selectPage(pageLimit,userLambdaQueryWrapper);
        System.out.println("总记录数："+page.getTotal());
        System.out.println("总页数："+page.getPages());
        List<User> users=page.getRecords();
        users.forEach(System.out::println);
    }

    /**
     * 自定义多表分页查询
     *
     * 这里只是模拟了一个表，可以根据实际情况关联多个表，where条件使用Wrapper条件构造器
     */
    @Test
    public void test7() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper.ge(User::getAge,20);

        Page<User> pageLimit=new Page<>(1,2);
        IPage<User> page = userDao.selectAllByPage(pageLimit,userLambdaQueryWrapper);
        System.out.println("总记录数："+page.getTotal());
        System.out.println("总页数："+page.getPages());
        List<User> users=page.getRecords();
        users.forEach(System.out::println);
    }

}
