package com.baizhi.service;

import com.baizhi.entity.Pic;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface PicService {
    //查询所有带分页
    Map<String,Object> selectAll(Integer page, Integer rows);
    //添加
    String add(Pic pic);
    //修改
    void edit(Pic pic);
    //删除
    void del(String id, HttpServletRequest request);
    //查所有
    List<Pic> selectAll();

}
