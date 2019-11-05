package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Pic;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//专辑控制层
@RestController
@RequestMapping("album")
public class AlbumController {

    //album依赖注入
    @Autowired
    private AlbumService albumService;


    //分页查询
    @RequestMapping("selectAll")
    public Map<String,Object> selectAll(Integer page, Integer rows){
        Map<String, Object> map = albumService.selectAll(page, rows);
      return map;
    }

        //文件上传
    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile cover, String id, HttpServletRequest request){
        //创建Map集合
        Map<String,Object> map =new HashMap<>();
        try {
            //获取相对路径
            cover.transferTo(new File(request.getServletContext().getRealPath("album/img"),cover.getOriginalFilename()));
            //创建Album对象
            Album album = new Album();
            //设置专辑id绑定当前文件上传至那个专辑下
            album.setId(id);
            //设置文件的文件名为上传的文件名
            album.setCover(cover.getOriginalFilename());
            //调用seriver方法将文件存入数据库
            albumService.edit(album);
            //文件上传成功返回一个true
            map.put("status",true);
        } catch (IOException e) {
            e.printStackTrace();
            //如果上传图片失败 返回一个false
            map.put("status",false);
            throw new RuntimeException("文件上传失败");
        }


        return map;
    }


    //增删改
    @RequestMapping("edit")
    public Map<String, Object> edit(String oper,Album album, HttpServletRequest request){
        //创建一个mAp集合
        Map<String,Object>map =new HashMap<>();
        try {
            //如果执行的是add 则添加操作
            if("add".equals(oper)){
                String id = albumService.add(album);
                map.put("message",id);
            }
            //如果执行的是edit 则修改操作
            if("edit".equals(oper)){
                albumService.edit(album);
            }
            //如果执行的是del 则删除操作
            if("del".equals(oper)){
                albumService.del(album);
            }
            //操作成功返回一个true
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            //操作s失败返回一个false
            map.put("status",false);
            //将错误信息存入前台
            map.put("message",e.getMessage());
        }

        return map;
    }


}
