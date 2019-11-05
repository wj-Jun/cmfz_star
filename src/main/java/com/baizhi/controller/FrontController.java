package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Pic;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrontController {
    //注入轮播图service
    @Autowired
    private PicService picService;
    //注入专辑sevice
    @Autowired
    private AlbumService albumService;
    //注入文章sevice
    @Autowired
    private ArticleService articleService;

    public Map<String,Object> firstPage(String uid, String type, String sub_type){
        Map<String,Object> map =new HashMap<>();
        //如果为all则为（首页)
        if("all".equals(type)){
            //轮播图查所有
            List<Pic> pics = picService.selectAll();
            //专辑查所有
            List<Album> albums = albumService.selectAll();
            //文章查所有

        }
        //如果为wen 则点击的是闻
        if("wen".equals(type)){


        }
        //如果为si 则点击的为思
        if("si".equals(type)){

        }

        return map;
    }

}
