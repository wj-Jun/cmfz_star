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
//专辑实体来
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Album implements Serializable {
    @Id//通用mapper的ID注解
    private String id;//编号
    private String name;//专辑名称
    private String cover;//专辑封面
    private Integer count;//音乐数量
    private String brief;//简介
    @JSONField(format = "yyyy-MM-dd")//将日期转为JSon格式
    @DateTimeFormat(pattern = "yyyy-MM-dd")//反序列化
    private Date createdate;//上传日期
    private String starid;//所属明星
    private Star star;
}
