package com.jsut.classmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsut.classmanage.model.Admin;
import com.jsut.classmanage.model.dto.LoginDTO;
import com.jsut.classmanage.model.dto.RegisterDTO;
import com.jsut.classmanage.model.dto.UserInfoDto;

import java.util.Map;

/**
 * @className AdminService
 **/
public interface AdminService extends IService<Admin> {

    /**
     * 用户登录操作
     *
     * @param dto
     * @return
     */
    Map<String, String> executeLogin(LoginDTO dto);

    /**
     * 用户注册操作
     * @param dto
     * @return
     */
    Admin executeRegister(RegisterDTO dto);

    /**
     * 根据UserId获取用户信息
     * @param userId
     * @return
     */
    UserInfoDto getUserByUserId(String userId);

    /**
     * 修改信息
     * @param dto
     */
    void updateInfo(RegisterDTO dto);
}
