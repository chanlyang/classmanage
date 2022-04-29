package com.jsut.classmanage.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @className Student
 * 学生信息表
 **/
@Data
@Builder
@Accessors(chain = true)
@TableName("student")
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学生学号
     */
    private String userId;
    /**
     * 学生姓名
     */
    private String userName;
    /**
     * 学生电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别
     */
    private Integer sex;

    private Integer isDel;
    private Timestamp createTime;
    private Timestamp updateTime;

}
