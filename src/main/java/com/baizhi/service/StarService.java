package com.baizhi.service;

import com.baizhi.entity.Star;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StarService {
    //查询所有明星 并分页
    Map<String,Object> selectAll(Integer page,Integer rows);
    //查询所有明星
    List<Star> selectAllOnd();
    //添加
    String add(Star star);
    //更新
    void edit(Star star);
    //删除
    void del(Star star);
}
