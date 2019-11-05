package com.baizhi.service.Impl;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    //注入Chapterservice
    @Autowired
    private ChapterDao chapterDao;
    //分页查询
    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows, String albumId) {
            //创建CHAPTER对象
        Chapter chapter = new Chapter();
        //将为明星id赋值
        chapter.setAlbumid(albumId);
        //创建ROWBOUNDS
        RowBounds rowBounds = new RowBounds();
        //调用Dao方法。将对象和分页数据存入集合中
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter,rowBounds);
        //计算总数count
        int count = chapterDao.selectCount(chapter);
        //创建一个MAP集合  并将list中的数据存入集合中
        Map<String, Object> map = new HashMap<>();
        //起始页数
        map.put("page",page);
        //当前页总共多少条数据
        map.put("rows",chapters);
        //总共多少页
        map.put("total",count%rows==0?count/rows:count/rows-1);
        //总共多少条数据
        map.put("records",count);
        return map;
    }

    //添加
    @Override
    public String add(Chapter chapter) {
        //设置id为UUID
        chapter.setId(UUID.randomUUID().toString());
        chapter.setCreatedate(new Date());
        //调用dao方法将数据存入数据库
        int i = chapterDao.insertSelective(chapter);
        //如果i为0 则添加失败
        //api添加方法返回值为 0/1
        //1：代表添加成功    0：代表添加失败
        if(i ==0){
            throw new RuntimeException("添加失败");
        }
        return chapter.getId();
    }

    //修改
    @Override
    public void edit(Chapter chapter) {

        try {
            if("".equals(chapter.getName())){
                chapter.setName(null);
            }
            int i = chapterDao.updateByPrimaryKeySelective(chapter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }
        //删除
    @Override
    public void del(Chapter chapter) {
        //根据实体类上的注解id来删除指定对象
        int i = chapterDao.deleteByPrimaryKey(chapter);
        //如果i为0 则删除失败
        //1：代表删除成功    0：代表删除失败
        if(i == 0){
            throw new RuntimeException("删除失败");
        }
    }
}
