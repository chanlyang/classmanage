package com.jsut.classmanage.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@ApiModel(description = "用户登录参数")
public class LoginDTO {

    @ApiModelProperty(value = "学号", example = "0001")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 15, message = "登录用户名长度在2-15")
    private String userId;

    @ApiModelProperty(value = "密码", example = "12345")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "登录密码长度在6-20")
    private String password;

    @ApiModelProperty(value = "记住我", example = "true")
    private Boolean rememberMe;
}
