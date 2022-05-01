package com.jsut.classmanage.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Data
public class RegisterDTO {

    @NotEmpty(message = "请输入学号")
    @Length(min = 2, max = 15, message = "长度在2-15")
    private String userId;

    private String userName;
    private String phone;
    private Integer roleId;
    private Integer sex;
    private String imgUrl;
    private String email;

    @NotEmpty(message = "请输入密码")
    @Length(min = 6, max = 20, message = "长度在6-20")
    private String pass;

    @NotEmpty(message = "请再次输入密码")
    @Length(min = 6, max = 20, message = "长度在6-20")
    private String checkPass;
}
