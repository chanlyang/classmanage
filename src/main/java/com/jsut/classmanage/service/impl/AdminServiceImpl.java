package com.jsut.classmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsut.classmanage.mapper.AdminMapper;
import com.jsut.classmanage.model.Admin;
import com.jsut.classmanage.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * @className AdminServiceImpl
 **/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
