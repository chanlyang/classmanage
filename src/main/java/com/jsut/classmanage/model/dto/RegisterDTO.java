package com.jsut.classmanage.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@ApiModel(description = "注册参数")
@Data
public class RegisterDTO {

    @ApiModelProperty(value = "学号",example = "001")
    @NotEmpty(message = "请输入学号")
    @Length(min = 2, max = 15, message = "长度在2-15")
    private String userId;

    @ApiModelProperty(value = "姓名",example = "张三")
    private String userName;
    @ApiModelProperty(value = "手机号",example = "13433234323")
    private String phone;
    @ApiModelProperty(value = "角色id",example = "1")
    private Integer roleId;
    @ApiModelProperty(value = "性别,1男，2女",example = "1")
    private Integer sex;
    @ApiModelProperty(value = "头像链接",example = "/imga/xx.jpg")
    private String imgUrl;
    @ApiModelProperty(value = "邮箱",example = "ee@gmail.com")
    private String email;

    @ApiModelProperty(value = "密码",example = "123456")
    @NotEmpty(message = "请输入密码")
    @Length(min = 6, max = 20, message = "长度在6-20")
    private String pass;

    @ApiModelProperty(value = "再次输入密码",example = "123456")
    @NotEmpty(message = "请再次输入密码")
    @Length(min = 6, max = 20, message = "长度在6-20")
    private String checkPass;
}
