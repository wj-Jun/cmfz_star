package com.baizhi.service.Impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.tree.FieldTypeSignature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//专辑
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    //注入albumdao
    @Autowired
    private AlbumDao albumDao;
    //注入stardao
    @Autowired
    private StarDao starDao;
        //用过id查询
    @Override
    public Album selectOne(String id) {
        Album album = albumDao.selectByPrimaryKey(id);
        return album;
    }
    //查询所有
    @Override
    public List<Album> selectAll() {
        List<Album> albums = albumDao.selectAll();
        return albums;
    }

    //分页查询
  @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        //创建Album对象
        Album album = new Album();
        //创建RowBounds对象  计算分页的条件
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //将对象和分页条件一起存入对象集合中
        List<Album> albums = albumDao.selectByRowBounds(album, rowBounds);
      for (Album album1 : albums) {
          Star star = starDao.selectByPrimaryKey(album1.getStarid());
          album1.setStar(star);
      }
        //计算总数
        int count = albumDao.selectCount(album);
        //创建一个map集合，将获取到数据存入map集合中
        Map<String,Object> map =new HashMap<>();
        map.put("page",page);//起始页
        map.put("rows",albums);//当前页显示多行条数据
        map.put("total",count%rows==0?count/rows:count/rows+1);//总共有几页
        map.put("records",count);//总共有多少条数据
        return map;
    }

//添加数据
    @Override
    public String add(Album album) {

        //设置id为UUID
        album.setId(UUID.randomUUID().toString());
        album.setCount(0);
        //调用dao方法，将前台传的数据添加进数据库
        int i = albumDao.insertSelective(album);
        //如果i为0 则添加失败
        //api添加方法返回值为 0/1
        //1：代表添加成功    0：代表添加失败
        if(i ==0){
            throw new RuntimeException("添加失败");
        }
        return album.getId();
    }
//修改数据
    @Override
    public void edit(Album album) {
        try {
            if("".equals(album.getCover())){
                album.setCover(null);
            }
            int i = albumDao.updateByPrimaryKeySelective(album);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("~~~修改失败");
        }
    }
//删除数据
    @Override
    public void del(Album album) {
        //根据实体类上的注解id来删除指定对象
        int i = albumDao.deleteByPrimaryKey(album);
        //如果i为0 则删除失败
        //1：代表删除成功    0：代表删除失败
        if(i == 0){
            throw new RuntimeException("删除失败");
        }


    }
}
