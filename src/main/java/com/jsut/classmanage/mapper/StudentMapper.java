package com.jsut.classmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsut.classmanage.model.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @className StudentMapper
 **/
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
