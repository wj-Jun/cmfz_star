package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("article")
@RestController
public class ArticleController {
    //注入ArticleService
    @Autowired
    private ArticleService articleService;

    //分页查询
    @RequestMapping("selectAll")
    public Map<String,Object> selectAll(Integer page, Integer rows){
        Map<String, Object> map = articleService.selectAll(page, rows);
        return map;
    }


   //修改
   @RequestMapping("edit")
   public void edit( Article article){
       articleService.edit(article);
   }
        //添加
    @RequestMapping("add")
   public String add(Article article){
       String add = articleService.add(article);
       return add;
   }
   //删除
    @RequestMapping("del")
   public void del(String id){
       articleService.del(id);
   }

     //图片上传
    @RequestMapping("upload")
     public Map<String,Object> upload(MultipartFile cover, HttpServletRequest request){
        //创建Map集合，将需要的参数添加进集合
        Map<String,Object> map =new HashMap<>();

            //获取文件相对路径
            File file = new File(request.getServletContext().getRealPath("article/img"), cover.getOriginalFilename());
        try {
            cover.transferTo(file);
            //参数1:error    参数2：url  图片路径
            //上传成功返回1
            map.put("error",0);
            map.put("url","http://localhost:9999/star/article/img/"+cover.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            //失败返回2
            map.put("error",1);
        }
        return map;

    }



    //图片空间
    @RequestMapping("picturespace")
    public Map<String,Object> browse(HttpServletRequest request){
        //创建map集合
        Map<String,Object>map =new HashMap<>();
        //获取文件相对路径
        File file = new File(request.getServletContext().getRealPath("article/img"));
        //file数组
        File[] files = file.listFiles();
        //创建list集合，将数组中的数据添加进list集合中
        ArrayList<Object> list = new ArrayList<>();
       //遍历数组、创建MAP集合。将参数添加进集合中
        for (File file1 : files) {
            Map<String,Object> map1 = new HashMap<>();
            map1.put("is_dir",false);//是否是？？
            map1.put("has_file",false);//是否是文件
            map1.put("filesize",file1.length());//长度
            map1.put("is_photo",true);//是否是图片
            //获取文件类型
            map1.put("filetype", FilenameUtils.getExtension(file1.getName()));//类型
            map1.put("filename",file1.getName());//文件名称
            map1.put("datetime",new Date());//上传时间 系统当前时间
            list.add(map1);//将map集合中的数据添加进list集合中
        }
        map.put("file_list",list);//为list集合
        map.put("total_count",list.size());//集合长度
        map.put("current_url","http://localhost:9999/star/article/img/");//图片存放的路径
         return map;

    }
}
