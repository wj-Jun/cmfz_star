package com.baizhi.service.Impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    public void login(Admin admin, String inputCode, HttpServletRequest request) {
        //获取session作用域中存的验证码
        HttpSession session = request.getSession();
        String inputCode1 = (String) session.getAttribute("securityCode");
        //判断验证码是否正确
        //生成的code和前台输入的验证码做比较
     if(inputCode1.equals(inputCode)){
         //获取查询数据库中的用户名和密码
         Admin admin1 = adminDao.selectOne(admin);
         //判断数据库中的用户名和密码和前台输入的验证码做比较，是否正确
         if(admin1 !=null){
             session.setAttribute("admin1",admin1);
         }else {
             throw new RuntimeException("用户名或密码错误，请重新输入~");
         }
     }else{
         throw new RuntimeException("验证码错误！");
     }
    }
}
