package com.baizhi.dao;

import com.baizhi.entity.Month;
import com.baizhi.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

//用户Dao接口  继续通用Mapper
public interface UserDao extends Mapper<User> {

    List<Month> select(String month);//月份
    List<Month> selectNOe(Integer count);//数量

    List<Month> queryAll(String sex);

}
