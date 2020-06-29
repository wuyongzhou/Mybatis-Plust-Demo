package com.example.mybatisplusdemo.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao extends BaseMapper<User> {

    /**
     * 使用Wrapper条件构造器
     * @param wrapper
     * @return
     */
    @Select("select * from user ${ew.customSqlSegment}")
    List<User> selectAll(@Param(Constants.WRAPPER)Wrapper<User> wrapper);

    /**
     * Page<User> page参数一定要在第一个位置，之后使用Wrapper条件构造器
     *
     * @param page
     * @param wrapper
     * @return
     */
    @Select("select * from user ${ew.customSqlSegment}")
    IPage<User> selectAllByPage(Page<User> page, @Param(Constants.WRAPPER)Wrapper<User> wrapper);
}
