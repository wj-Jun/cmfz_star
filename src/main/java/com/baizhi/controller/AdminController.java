package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
//管理员控制层
@RequestMapping("admin")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public Map<String,Object> login(Admin admin, String inputCode, HttpServletRequest request){
       Map<String,Object> map = new HashMap<>();
        try {
            //接收前台传递的参数
            adminService.login(admin,inputCode,request);
            //如果成功标记为ture
            map.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            //把异常信息存入集合
            map.put("status",false);
            map.put("message",e.getMessage());
        }
    return map;
    }



}
