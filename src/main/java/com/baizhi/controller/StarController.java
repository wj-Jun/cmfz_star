package com.baizhi.controller;

import com.baizhi.entity.Pic;
import com.baizhi.entity.Star;
import com.baizhi.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//明星控制层
@RequestMapping("star")
@Controller
public class StarController {
    //注入service
    @Autowired
    private StarService starService;

    //分页查询
    @RequestMapping("selectAll")
    @ResponseBody
    public Map<String,Object> selectAll(Integer page,Integer rows){
        //调用业务层方法
        Map<String, Object> map = starService.selectAll(page,rows);
        return map;
    }


    //上传文件
    @RequestMapping("upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile photo, String id, HttpServletRequest request){
        //创建Map集合
        Map<String,Object> map =new HashMap<>();
        try {
            //获取文件的相对路径
            photo.transferTo(new File(request.getServletContext().getRealPath("star/img"),photo.getOriginalFilename()));
            //创建star对象
            Star star = new Star();
            star.setId(id);
            star.setPhoto(photo.getOriginalFilename());
            starService.edit(star);
            //如果上传图片成功 返回一个true
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
    @ResponseBody
    public Map<String, Object> edit(String oper, Star star, HttpServletRequest request){
        //创建一个mAp集合
      Map<String,Object> map =new HashMap<>();

        try {
            //如果执行的是add 则添加操作
            if("add".equals(oper)){
                String id = starService.add(star);
                map.put("message",id);
            }
            //如果执行的是edit 则修改操作
            if("edit".equals(oper)){
                starService.edit(star);
            }
            //如果执行的是del 则删除操作
            if("del".equals(oper)){
                starService.del(star);
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


        //查询所有明星姓名（艺名）
        @RequestMapping("starName")
        public void getAllStarForSelect(HttpServletResponse response) throws IOException {
            List<Star> stars = starService.selectAllOnd();
            StringBuilder sb = new StringBuilder();
            sb.append("<select>");
            stars.forEach(star -> {
                //每次遍历出一个明星对象就创建一个option
                sb.append("<option value=").append(star.getId()).append(">").append(star.getNickname()).append("</option>");
            });
            sb.append("</select>");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(sb.toString());

        }

}
