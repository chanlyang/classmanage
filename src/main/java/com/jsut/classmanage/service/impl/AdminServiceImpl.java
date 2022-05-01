package com.jsut.classmanage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.jsut.classmanage.common.api.ApiResult;
import com.jsut.classmanage.common.exception.ApiAsserts;
import com.jsut.classmanage.jwt.JwtUtil;
import com.jsut.classmanage.mapper.AdminMapper;
import com.jsut.classmanage.model.Admin;
import com.jsut.classmanage.model.Student;
import com.jsut.classmanage.model.Techer;
import com.jsut.classmanage.model.dto.LoginDTO;
import com.jsut.classmanage.model.dto.RegisterDTO;
import com.jsut.classmanage.model.dto.UserInfoDto;
import com.jsut.classmanage.service.AdminService;
import com.jsut.classmanage.service.StudentService;
import com.jsut.classmanage.service.TecherService;
import com.jsut.classmanage.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @className AdminServiceImpl
 **/
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private AdminMapper adminMapper;
    @Resource
    private StudentService studentService;
    @Resource
    private TecherService techerService;

    @Override
    public Map<String, String> executeLogin(LoginDTO dto) {

        String token = null;
        try {
            Admin user = adminMapper.selectOne(new QueryWrapper<Admin>().lambda().eq(Admin::getUserId, dto.getUserId()));
            if (Objects.isNull(user)) {
                throw new Exception("用户不存在");
            }
            String encodePwd = MD5Utils.getPwd(dto.getPassword());
            if (!encodePwd.equals(user.getPassword())) {
                throw new Exception("密码错误");
            }
            token = JwtUtil.generateToken(user.getUserId());
            Map<String, String> result = Maps.newHashMapWithExpectedSize(2);
            result.put("token", token);
            result.put("roleId", user.getRoleId().toString());
            return result;
        } catch (Exception e) {
            log.warn("用户不存在or密码验证失败=======>{}", dto.getUserId(), e);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Admin executeRegister(RegisterDTO dto) {

        //查询是否有相同用户名的用户
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getUserId, dto.getUserId());
        Admin user = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(user)) {
            ApiAsserts.fail("该用户已存在！");
        }
        Admin addUser = Admin.builder()
                .userId(dto.getUserId())
                .roleId(dto.getRoleId())
                .password(MD5Utils.getPwd(dto.getPass()))
                .avatar(dto.getImgUrl())
                .build();
        baseMapper.insert(addUser);

        if (Admin.RoleIdEnum.STUDENT.getValue() == dto.getRoleId()) {
            Student student = Student.builder()
                    .userId(dto.getUserId())
                    .userName(dto.getUserName())
                    .phone(dto.getPhone())
                    .sex(dto.getSex())
                    .email(dto.getEmail()).build();
            studentService.save(student);
        } else {
            Techer techer = Techer.builder()
                    .userId(dto.getUserId())
                    .userName(dto.getUserName())
                    .phone(dto.getPhone())
                    .email(dto.getEmail())
                    .build();
            techerService.save(techer);
        }

        return addUser;
    }

    @Override
    public UserInfoDto getUserByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            ApiAsserts.fail("请求参数为空！");
        }
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().lambda()
                .eq(Admin::getUserId, userId));
        if (Objects.isNull(admin)) {
            ApiAsserts.fail("用户信息不存在！");
        }
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserId(admin.getUserId());
        userInfoDto.setImageUrl(admin.getAvatar());
        if (Admin.RoleIdEnum.STUDENT.getValue() == admin.getRoleId()) {
            Student student = studentService.getOne(new QueryWrapper<Student>().lambda()
                    .eq(Student::getUserId, userId));
            if (Objects.isNull(student)) {
                ApiAsserts.fail("学生信息不存在！");
            }
            userInfoDto.setUserName(student.getUserName());
            userInfoDto.setPhone(student.getPhone());
            userInfoDto.setEmail(student.getEmail());
        } else {
            Techer techer = techerService.getOne(new QueryWrapper<Techer>().lambda()
                    .eq(Techer::getUserId, userId));
            if (Objects.isNull(techer)) {
                ApiAsserts.fail("教师信息不存在！");
            }
            userInfoDto.setUserName(techer.getUserName());
            userInfoDto.setPhone(techer.getPhone());
            userInfoDto.setEmail(techer.getEmail());
        }
        return userInfoDto;
    }

    @Override
    public void updateInfo(RegisterDTO dto) {
        log.info("修改信息入参:{}", JSONObject.toJSONString(dto));
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().lambda()
                .eq(Admin::getUserId, dto.getUserId()));
        if (Objects.isNull(admin)) {
            ApiAsserts.fail("用户没找到");
        }
        LambdaUpdateWrapper<Student> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Student::getUserId, dto);
        if (StringUtils.isEmpty(dto.getUserName())) {
            wrapper.set(Student::getUserName, dto.getUserName());
        }
        if (StringUtils.isEmpty(dto.getPhone())) {
            wrapper.set(Student::getPhone, dto.getPhone());
        }
        if (StringUtils.isEmpty(dto.getEmail())) {
            wrapper.set(Student::getEmail, dto.getEmail());
        }
        studentService.update(wrapper);
    }
}
