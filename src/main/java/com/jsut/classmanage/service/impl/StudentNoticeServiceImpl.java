package com.jsut.classmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsut.classmanage.mapper.StudentMapper;
import com.jsut.classmanage.mapper.StudentNoticeMapper;
import com.jsut.classmanage.model.Student;
import com.jsut.classmanage.model.StudentNotice;
import com.jsut.classmanage.service.StudentNoticeService;
import com.jsut.classmanage.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @className StudentNoticeServiceImpl
 **/
@Service("studentNoticeService")
public class StudentNoticeServiceImpl extends ServiceImpl<StudentNoticeMapper, StudentNotice> implements StudentNoticeService {
}
