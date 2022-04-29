package com.jsut.classmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsut.classmanage.mapper.PunchMapper;
import com.jsut.classmanage.model.Punch;
import com.jsut.classmanage.service.PunchService;
import org.springframework.stereotype.Service;

/**
 * @className PunchServiceImpl
 **/
@Service
public class PunchServiceImpl extends ServiceImpl<PunchMapper, Punch> implements PunchService {
}
