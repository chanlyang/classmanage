package com.jsut.classmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsut.classmanage.mapper.AntiFraudMapper;
import com.jsut.classmanage.model.AntiFraud;
import com.jsut.classmanage.service.AntiFraudService;
import org.springframework.stereotype.Service;

/**
 * @className AntiFraudServiceImpl
 **/
@Service("antiFraudService")
public class AntiFraudServiceImpl extends ServiceImpl<AntiFraudMapper, AntiFraud> implements AntiFraudService {
}
