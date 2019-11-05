package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    //分页查询
    Map<String,Object> selectAll(Integer page,Integer rows);
    //添加
    String add(Album album);
    //修改
    void edit(Album album);
    //删除
    void del(Album album);

    Album selectOne(String id);
    //查所有
    List<Album> selectAll();
}
