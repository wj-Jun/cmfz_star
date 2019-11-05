package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

//轮播图实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pic implements Serializable {
    @Id//拥通用MapperID注解
    private String id;
    private String name;
    private String cover;
    private String description;
    private String status;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdate;
}
