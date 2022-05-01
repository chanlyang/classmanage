package com.jsut.classmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsut.classmanage.common.exception.ApiAsserts;
import com.jsut.classmanage.mapper.NoticeMapper;
import com.jsut.classmanage.mapper.StudentMapper;
import com.jsut.classmanage.mapper.StudentNoticeMapper;
import com.jsut.classmanage.model.Notice;
import com.jsut.classmanage.model.Student;
import com.jsut.classmanage.model.StudentNotice;
import com.jsut.classmanage.service.StudentNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @className StudentNoticeServiceImpl
 **/
@Slf4j
@Service("studentNoticeService")
public class StudentNoticeServiceImpl extends ServiceImpl<StudentNoticeMapper, StudentNotice> implements StudentNoticeService {

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private StudentNoticeMapper studentNoticeMapper;

    @Override
    public boolean acceptNotice(Long noitceId, String userId) {
        log.info("接受通知入参:{},userId:{}", noitceId, userId);
        Student student = studentMapper.selectOne(new QueryWrapper<Student>().lambda()
                .eq(Student::getUserId, userId));
        Notice notice = noticeMapper.selectOne(new QueryWrapper<Notice>().lambda()
                .eq(Notice::getId, noitceId));

        if (Objects.isNull(student) || Objects.isNull(notice)) {
            ApiAsserts.fail("无学生或公告信息");
        }
        StudentNotice studentNotice = StudentNotice.builder()
                .noticeId(notice.getId())
                .userId(student.getUserId())
                .title(notice.getTitle())
                .build();

        int flag = studentNoticeMapper.insert(studentNotice);

        return flag > 0;
    }
}
