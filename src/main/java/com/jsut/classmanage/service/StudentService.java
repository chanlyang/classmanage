package com.jsut.classmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsut.classmanage.model.Student;
import com.jsut.classmanage.util.PageUtils;

/**
 * @className StudentService
 **/
public interface StudentService extends IService<Student> {
    /**
     * 查询学生列表
     * @param pageNo
     * @param size
     * @return
     */
    PageUtils<Student> pageList(Integer pageNo, Integer size);
}
