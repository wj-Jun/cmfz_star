package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
//管理员表实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="admin")
public class Admin implements Serializable {
    @Id//拥通用MapperID注解
    private String id;
    private String username;
    private String password;
    private String nikname;
}
