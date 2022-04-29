package com.jsut.classmanage.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @className Admin
 * <p>
 * 用户登录表
 **/
@Data
@Builder
@Accessors(chain = true)
@TableName("admin")
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户登录id
     */
    private String userId;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像链接
     */
    private String avatar;
    /**
     * token
     */
    private String token;
    /**
     * 用户类型, 1- 学生，2- 管理员
     */
    private Integer roleId;

    private Integer isDel;
    private Timestamp createTime;
    private Timestamp updateTime;


    @AllArgsConstructor
    @Getter
    public enum RoleIdEnum {

        STUDENT(1, "学生"),
        TECHER(2, "老师");
        private int value;
        private String desc;

    }
}
