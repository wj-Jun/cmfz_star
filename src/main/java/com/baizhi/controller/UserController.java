package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.User;
import com.baizhi.service.Userservice;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//用户控制层
@RequestMapping("user")
@RestController
public class UserController {
    //注入Userservice
    @Autowired
    private Userservice userservice;

    //分页查询
    @RequestMapping("UserselectAll")
    public Map<String,Object> selectAll(Integer page,Integer rows,String starId){
        Map<String, Object> map = userservice.selectAll(page, rows, starId);
        return map;
    }

    //数据导出
    @RequestMapping("select")
    public void select(HttpServletResponse response){
        //调用业务层查询所有方法
        List<User> users = userservice.select();

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息","用户簿"), User.class, users);
        String fileName = "用户报表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xls";
        //处理中文下载名乱码
        try {
            fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
            //设置 response
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-disposition","attachment;filename="+fileName);
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //
    @RequestMapping("queryAll")
    public Map<String,Object> queryAll(){
        //创建MAP集合
        Map<String,Object> map =new HashMap<>();
        //
        Integer[] nan = userservice.queryAll("男");
        map.put("nan",nan);
        Integer[] integers = userservice.queryAll("女");
        map.put("nv",integers);
        return map;
    }



}
