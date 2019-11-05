package com.baizhi.controller;

import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.awt.image.PNGImageDecoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.SocketTimeoutException;
//验证码
@RequestMapping("code")
@Controller
public class CodeController {

    @RequestMapping("getCode")
    public void Code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取验证码
        String securityCode = SecurityCode.getSecurityCode();
        System.out.println(securityCode);
        //获取session作用域并将验证码存入session作用域
        HttpSession session =request.getSession();
        session.setAttribute("securityCode",securityCode);
        BufferedImage image = SecurityImage.createImage(securityCode);
        //设置文件格式
        response.setContentType("image/png");
        //将验证码打印图片至前台
        ImageIO.write(image,"png",response.getOutputStream());
    }

    //安全退出
    @RequestMapping("cance")
    public String cance(HttpServletRequest request){
        HttpSession session =request.getSession(true);
        session.invalidate();
        return "redirect:/login/login.jsp";
    }


}
