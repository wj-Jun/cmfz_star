package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import it.sauronsoftware.jave.Encoder;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

//章节控制层
@RequestMapping("chapter")
@RestController
public class ChapterController {
    //注入service
    @Autowired
    private ChapterService chapterService;

    @Autowired
    private AlbumService albumService;

    //分页查询所有专辑
    @RequestMapping("selectAll")
    public Map<String,Object> selectAll(Integer page,Integer rows,String albumId){
        Map<String, Object> map = chapterService.selectAll(page, rows, albumId);
        return map;
    }

    //增删改

    @RequestMapping("edit")
    public Map<String, Object> edit(String oper, Chapter chapter, HttpServletRequest request){
        //创建一个mAp集合
        Map<String,Object> map =new HashMap<>();

        try {
            //如果执行的是add 则添加操作
            if("add".equals(oper)){
                String id = chapterService.add(chapter);
                map.put("message",id);
            }
            //如果执行的是edit 则修改操作
            if("edit".equals(oper)){
                chapterService.edit(chapter);
            }
            //如果执行的是del 则删除操作
            if("del".equals(oper)){
                chapterService.del(chapter);
            }
            //操作成功返回true
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            //操作失败返回false
            map.put("status",false);
            //将错误信息存入前台
            map.put("message",e.getMessage());
        }

        return map;
    }


//文件上传操作
    @RequestMapping("upload")
    public Map<String,Object> upload(MultipartFile name, String id, String albumId, HttpServletRequest request){
        Map<String,Object> map =new HashMap<>();
        //处理文件上传操作
        try {
            File file = new File(request.getServletContext().getRealPath("album/imge"), name.getOriginalFilename());
            name.transferTo(file);
            //修改文件名称name属性
            //创建chapter对象
            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setName(name.getOriginalFilename());
            //获取文件大小
            BigDecimal size = new BigDecimal(name.getSize());
            BigDecimal mod = new BigDecimal(1024);
            //计算文件到MB  除两个1024  保存两个小数点
            BigDecimal realSize = size.divide(mod).divide(mod).setScale(2, BigDecimal.ROUND_HALF_UP);
            //为文件大小赋值
            chapter.setSizes(realSize+"MB");
            //获取文件时长
            Encoder encoder = new Encoder();
            long duration = encoder.getInfo(file).getDuration();
            chapter.setDuration(duration/1000/60+":"+duration/1000%60);
            chapterService.edit(chapter);
               //修改专辑中的数量
            Album album = albumService.selectOne(albumId);
            album.setCount(album.getCount()+1);
            albumService.edit(album);
            //上传成功返回true
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status",false);
        }
        return map;
    }


}
