package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;

import java.util.Map;
//章节
public interface ChapterService {
    //分页查询
    Map<String,Object> selectAll(Integer page, Integer rows, String albumid);

    //添加
    String add(Chapter chapter);
    //修改
    void edit(Chapter chapter);
    //删除
    void del(Chapter chapter);
}
