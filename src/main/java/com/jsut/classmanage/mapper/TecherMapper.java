package com.jsut.classmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsut.classmanage.model.Techer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @className TecherMapper
 **/
@Mapper
public interface TecherMapper extends BaseMapper<Techer> {
}
