package com.jsut.classmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsut.classmanage.mapper.StudentMapper;
import com.jsut.classmanage.model.Student;
import com.jsut.classmanage.service.StudentService;
import com.jsut.classmanage.util.PageUtils;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @className StudentServiceImpl
 **/
@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {


    @Resource
    private StudentMapper studentMapper;


    @Override
    public PageUtils<Student> pageList(Integer pageNo, Integer size) {

        Page<Student> page = new Page<>(pageNo, size);
        IPage<Student> studentIPage = studentMapper.selectPage(page, new QueryWrapper<Student>()
                .lambda()
                .orderByDesc(Student::getCreateTime));

        return new PageUtils<Student>(studentIPage);
    }
}
