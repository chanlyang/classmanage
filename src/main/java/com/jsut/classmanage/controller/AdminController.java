package com.jsut.classmanage.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.jsut.classmanage.common.api.ApiResult;
import com.jsut.classmanage.model.Admin;
import com.jsut.classmanage.model.dto.LoginDTO;
import com.jsut.classmanage.model.dto.RegisterDTO;
import com.jsut.classmanage.model.dto.UserInfoDto;
import com.jsut.classmanage.service.AdminService;
import com.jsut.classmanage.util.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.jsut.classmanage.jwt.JwtUtil.USER_NAME;

/**
 * @className UserController
 **/
@Api(tags = "用户管理")
@Slf4j
@RestController
@RequestMapping("/admin/user")
public class AdminController {

    @Resource
    private AdminService adminService;


    @ApiOperation(value = "用户登录", notes = "登录，学生老师都用此接口，用角色区分")
    @PostMapping(value = "/login")
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        log.info("登录入参:{}", JSONObject.toJSONString(dto));
        Map<String, String> result = adminService.executeLogin(dto);
        if (CollectionUtil.isEmpty(result)) {
            return ApiResult.failed("账号密码错误");
        }
        return ApiResult.success(result, "登录成功");
    }


    @ApiOperation(value = "用户注册", notes = "注册，学生老师都用此接口，用角色区分")
    @PostMapping(value = "/register")
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {
        JSONObject.toJSONString(dto);
        log.info("注册入参:{}", JSONObject.toJSONString(dto));
        Admin user = adminService.executeRegister(dto);
        if (ObjectUtils.isEmpty(user)) {
            return ApiResult.failed("账号注册失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", user);
        return ApiResult.success(map);
    }


    @ApiOperation(value = "上传图片")
    @PostMapping("/uploadImg")
    public ApiResult<String> graphicImg(@RequestParam("file") MultipartFile file) {
        log.info("上传图片");
        if (file.isEmpty()) {
            return ApiResult.failed("上传的文件为空");
        }
        // 文件名
        String fileName = file.getOriginalFilename();
        // 获取文件后缀名
        String extension = fileName.substring(fileName.indexOf("."));
        // 上传文件的路径
        String uploadFolder = ClassUtils.getDefaultClassLoader().getResource("static/").getPath();
        fileName = UUID.randomUUID().toString().replaceAll("-", "") + extension;

        File dest = new File(uploadFolder + fileName);
        try {
            file.transferTo(dest);
            String imgUrl = "/uploadImg/" + fileName;
            return ApiResult.success(imgUrl, "成功");
        } catch (IOException e) {
            log.error("上传失败，", e);
            return ApiResult.failed("上传失败");
        }
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping(value = "/info")
    public ApiResult<UserInfoDto> getUser(@RequestHeader(value = USER_NAME) String userId) {
        log.info("获取用户信息入参:{}", userId);
        UserInfoDto user = adminService.getUserByUserId(userId);
        return ApiResult.success(user);
    }

    @ApiOperation(value = "更新用户信息")
    @PostMapping("/updateInfo")
    public ApiResult updateInfo(@RequestBody RegisterDTO dto,
                                @RequestHeader(value = USER_NAME) String userId) {

        log.info("修改信息入参:{}", JSONObject.toJSONString(dto));
        dto.setUserId(userId);
        adminService.updateInfo(dto);
        return ApiResult.success();

    }

    @ApiOperation(value = "退出登录")
    @GetMapping(value = "/logout")
    public ApiResult<Object> logOut() {
        return ApiResult.success(null, "注销成功");
    }


    public static void main(String[] args) {
        //e10adc3949ba59abbe56e057f20f883e
        System.out.println(MD5Utils.getPwd("123456"));

        System.out.println(ClassUtils.getDefaultClassLoader().getResource("static"));
    }
}
