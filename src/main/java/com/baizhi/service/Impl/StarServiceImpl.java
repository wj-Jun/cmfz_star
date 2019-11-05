package com.baizhi.service.Impl;

import com.baizhi.dao.StarDao;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class StarServiceImpl implements StarService {
    @Autowired
    private StarDao starDao;

    //查询所有明星
    @Override
    public List<Star> selectAllOnd() {
        List<Star> stars = starDao.selectAll();
        return stars;
    }

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        //创建一个star对象
        Star star = new Star();
        //创建RowBounds 设置分页条件
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        //将对象和分页条件一起存入对象集合中
        List<Star> stars = starDao.selectByRowBounds(star, rowBounds);
        //计算总条数
        int count = starDao.selectCount(star);
        //创建一个map集合
        Map<String,Object> map =new HashMap<>();
        map.put("page",page);//起始页
        map.put("rows",stars);//当前页多少条数据
        map.put("total",count%rows==0?count/rows:count/rows+1);//总共有几页
        map.put("records",count);//总共有多少条数据
         return map;//返回MAP集合
    }

  //添加
    @Override
    public String add(Star star) {
        //设置id为UUIDD
        star.setId(UUID.randomUUID().toString());
        //调用添加方法添加数据进数据库
        int i = starDao.insertSelective(star);
          //如果i为0 则添加失败
        //api添加方法返回值为 0/1
        //1：代表添加成功    0：代表添加失败
        if(i ==0){
            throw new RuntimeException("添加失败");
        }
        return star.getId();
    }
        //修改
    @Override
    public void edit(Star star) {
        try {
            //判断头像是否为空，如果不修改则为空
            if("".equals(star.getPhoto())){
                star.setPhoto(null);
            }
            int i = starDao.updateByPrimaryKeySelective(star);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("~~~修改失败");
        }
    }
    //删除
    @Override
    public void del(Star star) {
        //根据实体类上的注解id来删除指定对象
        int i = starDao.deleteByPrimaryKey(star);
        //如果i为0 则删除失败
        //1：代表删除成功    0：代表删除失败
        if(i == 0){
            throw new RuntimeException("删除失败");
        }

    }
}
