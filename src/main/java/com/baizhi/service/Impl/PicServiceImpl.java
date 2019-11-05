package com.baizhi.service.Impl;

import com.baizhi.dao.PicDao;
import com.baizhi.entity.Pic;
import com.baizhi.service.PicService;
import org.apache.ibatis.session.RowBounds;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


import javax.servlet.GenericFilter;
import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class PicServiceImpl implements PicService {
    @Autowired
    private PicDao picDao;
    //添加
     @Override
    public String add(Pic pic) {
         //设置id为UUID
         //设置时间为当前系统时间
         pic.setId(UUID.randomUUID().toString());
         pic.setCreatedate(new Date());
         //调用添加方法添加数据进数据库
         int i = picDao.insertSelective(pic);
         //         //如果i为0 则添加失败
         //api添加方法返回值为 0/1
         //1：代表添加成功    0：代表添加失败
         if(i == 0){
             throw new RuntimeException("添加失败~");
         }
         return pic.getId();
    }
    //修改
    @Override
    public void edit(Pic pic) {
        try {
            if("".equals(pic.getCover())){
                pic.setCover(null);
            }
            int i = picDao.updateByPrimaryKeySelective(pic);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("~~~");
        }

    }
    //删除
    @Override
    public void del(String id, HttpServletRequest request) {
         //先查询数据库中的数据
        Pic pic = picDao.selectByPrimaryKey(id);
        //调用删除方法
        int i = picDao.deleteByPrimaryKey(id);

        //如果i为0 则删除失败
        //1：代表添加成功    0：代表添加失败
        if(i == 0){
            throw new RuntimeException("删除失败~");
        }else {
            //获取封面
            String cover = pic.getCover();

        }
    }
//查所有
    @Override
    public List<Pic> selectAll() {
        Pic pic = new Pic();//创建pic对象
        pic.setStatus("正常");//设置状态为正常吃
        return picDao.select(pic);
    }


    //展示所有
    @Override
        public Map<String, Object> selectAll(Integer page, Integer rows) {
           //创建pic对象
            Pic pic =new Pic();
            //创建RowBounds对象  设置分页起始条数和当前页显示多少条
            RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
            //
            List<Pic> list = picDao.selectByRowBounds(pic, rowBounds);
            int count = picDao.selectCount(pic);
            Map<String, Object> map = new HashMap<>();
            map.put("page",page);//当前页
            map.put("rows",list);//当前页所有的数据
            map.put("total",count%rows==0?count/rows:count/rows+1);//总共有几页
            map.put("records",count);//总共有多少条数据

            return map;

    }

}
