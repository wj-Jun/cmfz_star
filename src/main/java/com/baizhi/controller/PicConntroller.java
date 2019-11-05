package com.baizhi.controller;

import com.baizhi.entity.Pic;
import com.baizhi.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

//轮播图控制层
@RequestMapping("pic")
@RestController
public class PicConntroller {
    @Autowired
    private PicService picService;

    @RequestMapping("selectAll")
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        //查询所有并分页  调用service
        //page:代表当前页    rows:代表的是每页展示多少条数据
        Map<String, Object> map = picService.selectAll(page, rows);
        return map;
    }

    //文件上传
    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile cover, String id, HttpServletRequest request) {
        System.out.println("我是upload=============");
        //创建一个map集合
        Map<String, Object> map = new HashMap<>();

        try {
            //获取相对路径
            //执行文件上操作
            cover.transferTo(new File(request.getServletContext().getRealPath("/pic/img"),cover.getOriginalFilename()));
            //创建pic对象
            //修改pic对象中的属性
            Pic pic = new Pic();
            pic.setId(id);
            System.out.println(cover.getOriginalFilename());
            pic.setCover(cover.getOriginalFilename());
            //调用edit方法
            picService.edit(pic);
            //添加成功返回为true
            map.put("status", true);
        } catch (Exception e) {
            e.printStackTrace();
            //如果上传图片失败 返回一个false
            map.put("status", false);
            throw new RuntimeException("添加失败");
        }
        return map;
    }


    //增删改操作
    @RequestMapping("edit")
   public Map<String, Object> edit(String oper, Pic pic, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<>();

        try {
            //如果执行的是add 则添加操作
            if("add".equals(oper)){
                //添加成功为true
                String id = picService.add(pic);
                map.put("message",id);
            }
            //如果执行的是edit 则修改操作
            if("edit".equals(oper)){
                picService.edit(pic);
            }
            //如果执行的是del 则删除操作
            if("del".equals(oper)){
                picService.del(pic.getId(),request);
            }
            //执行操作成功返回一给true
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            //执行增删改操作失败，返回一个false
            map.put("status",false);
            //将错误信息存入前台
            map.put("message",e.getMessage());
        }
        return map;
    }
}