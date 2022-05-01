package com.jsut.classmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsut.classmanage.mapper.TecherMapper;
import com.jsut.classmanage.model.Techer;
import com.jsut.classmanage.service.TecherService;
import org.springframework.stereotype.Service;

/**
 * @className TecherServiceImpl
 **/
@Service("techerService")
public class TecherServiceImpl extends ServiceImpl<TecherMapper, Techer> implements TecherService {
}
