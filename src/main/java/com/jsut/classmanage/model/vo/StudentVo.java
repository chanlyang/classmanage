package com.jsut.classmanage.model.vo;

import lombok.Data;

/**
 * @className StudentVo
 **/
@Data
public class StudentVo {

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
    private String sex;
}
