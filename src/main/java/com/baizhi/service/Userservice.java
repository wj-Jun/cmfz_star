package com.baizhi.service;

import com.baizhi.entity.Month;
import com.baizhi.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface Userservice {
    //分页查询明星下的用户
    Map<String,Object> selectAll(Integer page,Integer rows,String starId);

        List<User> select();

        Integer[] queryAll(String sex);

}
