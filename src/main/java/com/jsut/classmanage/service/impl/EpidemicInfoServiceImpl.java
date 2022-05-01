package com.jsut.classmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsut.classmanage.mapper.EpidemicInfoMapper;
import com.jsut.classmanage.model.EpidemicInfo;
import com.jsut.classmanage.service.EpidemicInfoService;
import org.springframework.stereotype.Service;

/**
 * @className EpidemicInfoServiceImpl
 **/
@Service("epidemicInfoService")
public class EpidemicInfoServiceImpl extends ServiceImpl<EpidemicInfoMapper, EpidemicInfo> implements EpidemicInfoService {
}
