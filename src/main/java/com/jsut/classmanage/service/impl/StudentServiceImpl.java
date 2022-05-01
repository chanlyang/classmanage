package com.jsut.classmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsut.classmanage.mapper.StudentMapper;
import com.jsut.classmanage.model.Student;
import com.jsut.classmanage.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @className StudentServiceImpl
 **/
@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
