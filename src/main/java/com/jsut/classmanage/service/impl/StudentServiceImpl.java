package com.jsut.classmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jsut.classmanage.mapper.StudentMapper;
import com.jsut.classmanage.model.Student;
import com.jsut.classmanage.model.vo.StudentVo;
import com.jsut.classmanage.service.StudentService;
import com.jsut.classmanage.util.PageUtils;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className StudentServiceImpl
 **/
@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {


    @Resource
    private StudentMapper studentMapper;


    @Override
    public PageUtils<StudentVo> pageList(Integer pageNo, Integer size) {

        Page<Student> page = new Page<>(pageNo, size);
        IPage<Student> studentIPage = studentMapper.selectPage(page, new QueryWrapper<Student>()
                .lambda()
                .orderByDesc(Student::getCreateTime));

        List<StudentVo> result = Lists.transform(studentIPage.getRecords(), it ->{
            StudentVo studentVo = new StudentVo();
            studentVo.setUserId(it.getUserId());
            studentVo.setUserName(it.getUserName());
            studentVo.setPhone(it.getPhone());
            studentVo.setEmail(it.getEmail());
            studentVo.setSex(it.getSex() == 1 ? "男" : "女");

            return studentVo;
        });



        return new PageUtils<StudentVo>(result,Long.valueOf(studentIPage.getTotal()).intValue(),Long.valueOf(studentIPage.getSize()).intValue(),Long.valueOf(studentIPage.getCurrent()).intValue());
    }
}
