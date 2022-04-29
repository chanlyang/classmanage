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
 * @className Techer
 *
 *  教师表（管理员)
 **/
@Data
@Builder
@Accessors(chain = true)
@TableName("techer")
@NoArgsConstructor
@AllArgsConstructor
public class Techer {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 教师编号，用于登录的ID
     */
    private String userId;

    /**
     * 教师姓名
     */
    private String userName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 教师邮箱
     */
    private String email;

    private Integer isDel;
    private Timestamp createTime;
    private Timestamp updateTime;

}
