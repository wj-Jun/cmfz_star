package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

//明星实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain =true)
public class Star implements Serializable {
    @Id//拥通用MapperID注解
    private String id;//编号
    private String nickname;//艺名名称
    private String realname;//真名
    private String photo;//头像
    private String sex;//性别
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bir;//生日
 }
