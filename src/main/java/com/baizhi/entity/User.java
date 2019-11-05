package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
//用户实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

public class User implements Serializable {
    @Id//拥通用MapperID注解
    @Excel(name ="用户编号")
    private String id;//用户编号
    @Excel(name ="用户密码")
    private String username;//用户名
    @Excel(name ="用户密码")
    private String password;//密码
    private String salt;//盐
    @Excel(name ="用户昵称")
    private String nickname;//昵称
    @Excel(name ="用户密码")
    private String phone;//电话
    @Excel(name ="用户地址")
    private String province;//省份地址
    @Excel(name ="用户城市")
    private String city;//城市
    @Excel(name ="签名")
    private String sign;//签名
    @Excel(name ="头像",type = 2)
     private String photo;//用户头像
    @Excel(name ="性别")
    private String sex;//用户性别
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name ="创建时间",format = "yyyy-MM-dd")
    private Date createdate;//
    @Excel(name ="明星id")
    private String starid;//明星ID

}
