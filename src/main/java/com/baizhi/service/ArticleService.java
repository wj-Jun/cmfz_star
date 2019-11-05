package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    //分页查询文章
    Map<String,Object> selectAll(Integer page, Integer rows);
    //添加
    String add(Article article);
    //修改
    void edit(Article article);
    //删除
    void del(String id);
    //查询所有
    List<Article> selectAll();


}
