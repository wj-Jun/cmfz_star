package com.baizhi.service.Impl;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.Month;
import com.baizhi.entity.User;
import com.baizhi.service.Userservice;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class UserServiceImpl implements Userservice {
    //注入UserDao
    @Autowired
    private UserDao userDao;


    @Override
    public Integer[] queryAll(String sex) {
        //创建map集合
        Map<String, Object> map = new HashMap<>();
        //将月份放入数组中
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        for (int i = 0; i < integers.length; i++) {
            userDao.queryAll(sex).forEach(users -> {
                //判断获取的月份是否为空，如果不为空则添加进map集合
                if (users.getMonth() != null) {
                    //将获取到的月份添加进集合中
                    map.put(users.getMonth(), users.getCount());
                }
            });
            //如果为空 则设置月份为空
            map.put(integers[i] + "月", 0);
        }
        Integer[] integers1 = new Integer[12];
        for (int i = 0; i < integers1.length; i++) {
            Integer count = (Integer) map.get(integers[i] + "月");
            integers1[i] = count;

        }
        return integers1;
    }


    @Override
    public List<User> select() {
        return userDao.selectAll();

    }

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows, String starId) {
        //创建User对象
        User user = new User();
        //将当前用户关注的明星id绑定在当前用户上
        user.setStarid(starId);
        //创建RowBounds 设置分页计算条件
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        //调用通用Dao方法,将对象和分页数据存入集合中
        List<User> users = userDao.selectByRowBounds(user,rowBounds);
            //计算总数量
        int count = userDao.selectCount(user);
        //创建Map集合，将对象和分页的数据存入集合中
        Map<String,Object> map =new HashMap<>();
        map.put("page",page);//起始页数
        map.put("rows",users);//当前页显示多少条数据
        map.put("total",count%rows==0?count/rows:count/rows+1);//总共有几页
        map.put("records",count);//总共有多少条数据

        return map;
    }
}
