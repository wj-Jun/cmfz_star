package com.baizhi.service.Impl;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    //注入ArticleDao
    @Autowired
    private ArticleDao articleDao;
    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        //创建article对象
        Article article = new Article();
        //创建Rowbounds对象  设置分页条件
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Article> articles = articleDao.selectByRowBounds(article,rowBounds);
        int count = articleDao.selectCount(article);
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);//起始页
        map.put("rows",articles);//当前页多少条数据
        //总共多少页
        map.put("total",count%rows==0?count/rows:count/rows-1);
        //总共多少条数据
        map.put("records",count);
        return map;
    }

    @Override
    public String add(Article article) {

        //设置id为UUID
       // article.setId(UUID.randomUUID().toString());
        article.setId(UUID.randomUUID().toString());
        article.setCreatedate(new Date());
        //调用dao方法将数据存入数据库
        int i = articleDao.insertSelective(article);
        //如果i为0 则添加失败
        //api添加方法返回值为 0/1
        //1：代表添加成功    0：代表添加失败
        if(i ==0){
            throw new RuntimeException("添加失败");
        }
        return article.getId();

    }

    @Override
    public void edit(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        //如果i为0 则修改失败
        //1：代表修改成功    0：代表修改失败
            if(i == 0){
                throw new RuntimeException("删除失败");

        }
    }

    @Override
    public void del(String id) {
        Article article = new Article();
        article.setId(id);
        //根据实体类上的注解id来删除指定对象
        int i = articleDao.deleteByPrimaryKey(article);
        //如果i为0 则删除失败
        //1：代表删除成功    0：代表删除失败
        if(i == 0){
            throw new RuntimeException("删除失败");
        }
    }
    //查询所有
    @Override
    public List<Article> selectAll() {
        List<Article> articles = articleDao.selectAll();
        return null;
    }
}
