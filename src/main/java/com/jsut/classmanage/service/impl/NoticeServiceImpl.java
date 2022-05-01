package com.jsut.classmanage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jsut.classmanage.common.YesOrNoEnum;
import com.jsut.classmanage.common.exception.ApiAsserts;
import com.jsut.classmanage.mapper.NoticeMapper;
import com.jsut.classmanage.mapper.StudentMapper;
import com.jsut.classmanage.mapper.StudentNoticeMapper;
import com.jsut.classmanage.model.Notice;
import com.jsut.classmanage.model.Student;
import com.jsut.classmanage.model.StudentNotice;
import com.jsut.classmanage.model.vo.NoticeUserVo;
import com.jsut.classmanage.model.vo.NoticeVo;
import com.jsut.classmanage.model.vo.UserNoticeVo;
import com.jsut.classmanage.service.NoticeService;
import com.jsut.classmanage.service.StudentService;
import com.jsut.classmanage.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * @className NoticeServiceImpl
 **/
@Slf4j
@Service("noticeService")
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private StudentNoticeMapper studentNoticeMapper;


    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public PageUtils<UserNoticeVo> queryTodayNotice(Integer pageNo, Integer size, String userId) {

        log.info("查询通知信息入参,pageNo:{},size:{},userId:{}", pageNo, size, userId);
        Student student = studentMapper.selectOne(new QueryWrapper<Student>().lambda().eq(Student::getUserId, userId));
        if (Objects.isNull(student)) {
            ApiAsserts.fail("用户信息不存在！");
        }
        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();

        LocalDateTime time = LocalDate.now().minusDays(3).atStartOfDay();
        Page<Notice> page = new Page<>(pageNo, size);
        IPage<Notice> noticeIPage = noticeMapper.selectPage(page, new QueryWrapper<Notice>().lambda()
                // .lt(Notice::getCreateTime, endTime) //<
                .gt(Notice::getCreateTime, time)); // >  这三天所有的公告

        List<Notice> notices = noticeIPage.getRecords();

        List<UserNoticeVo> userNoticeVos = Lists.transform(notices, it -> {
            UserNoticeVo userNoticeVo = new UserNoticeVo();
            userNoticeVo.setNoticeId(it.getId());
            userNoticeVo.setUserId(student.getUserId());
            userNoticeVo.setUserName(student.getUserName());
            userNoticeVo.setTitle(it.getTitle());
            userNoticeVo.setContent(it.getContent());
            userNoticeVo.setCreateTime(df.format(it.getCreateTime().toLocalDateTime()));
            StudentNotice studentNotice = studentNoticeMapper.selectOne(new QueryWrapper<StudentNotice>().lambda()
                    .eq(StudentNotice::getUserId, student.getUserId())
                    .eq(StudentNotice::getNoticeId, it.getId()));
            userNoticeVo.setIsRead(Objects.isNull(studentNotice) ? YesOrNoEnum.NO.getValue() : YesOrNoEnum.YES.getValue());

            return userNoticeVo;

        });

        return new PageUtils<UserNoticeVo>(userNoticeVos, Long.valueOf(noticeIPage.getTotal()).intValue(), Long.valueOf(noticeIPage.getSize()).intValue(), Long.valueOf(noticeIPage.getCurrent()).intValue());
    }


    @Override
    public boolean addNotice(NoticeVo noticeVo) {
        log.info("添加公告入参:{}", JSONObject.toJSONString(noticeVo));

        Notice notice = Notice.builder()
                .userId(noticeVo.getUserId())
                .title(noticeVo.getTitle())
                .content(noticeVo.getContent())
                .build();
        int flag = noticeMapper.insert(notice);
        return flag > 0;
    }

    @Override
    public PageUtils<NoticeVo> pageNotice(Integer pageNo, Integer size) {
        Page<Notice> page = new Page<>(pageNo, size);
        IPage<Notice> noticeIPage = noticeMapper.selectPage(page, new QueryWrapper<Notice>().lambda()
                .orderByDesc(Notice::getCreateTime));
        List<NoticeVo> noticeVos = Lists.transform(noticeIPage.getRecords(), it -> {
            NoticeVo noticeVo = new NoticeVo();
            noticeVo.setId(it.getId());
            noticeVo.setUserId(it.getUserId());
            noticeVo.setTitle(it.getTitle());
            noticeVo.setContent(it.getContent());
            noticeVo.setCreateTime(df.format(it.getCreateTime().toLocalDateTime()));
            return noticeVo;
        });
        return new PageUtils<NoticeVo>(noticeVos, Long.valueOf(noticeIPage.getTotal()).intValue(), Long.valueOf(noticeIPage.getSize()).intValue(), Long.valueOf(noticeIPage.getCurrent()).intValue());
    }

    @Override
    public PageUtils<NoticeUserVo> pageUserNoticeInfo(Integer pageNo, Integer size, Long noitceId) {
        log.info("学生接受公告入参：{}", noitceId);
        Page<Student> page = new Page<>(pageNo, size);
        IPage<Student> studentIPage = studentMapper.selectPage(page, new QueryWrapper<Student>().lambda().eq(Student::getIsDel, YesOrNoEnum.NO.getValue()));
        List<NoticeUserVo> userVoList = Lists.transform(studentIPage.getRecords(), it -> {
            NoticeUserVo noticeUserVo = new NoticeUserVo();
            noticeUserVo.setUserId(it.getUserId());
            noticeUserVo.setUserName(it.getUserName());
            StudentNotice studentNotice = studentNoticeMapper.selectOne(new QueryWrapper<StudentNotice>().lambda()
                    .eq(StudentNotice::getNoticeId, noitceId)
                    .eq(StudentNotice::getUserId, it.getUserId()));
            noticeUserVo.setStatus(Objects.isNull(studentNotice) ? YesOrNoEnum.NO.getValue() : YesOrNoEnum.YES.getValue());

            return noticeUserVo;

        });

        return new PageUtils<NoticeUserVo>(userVoList, Long.valueOf(studentIPage.getTotal()).intValue(), Long.valueOf(studentIPage.getTotal()).intValue(), Long.valueOf(studentIPage.getCurrent()).intValue());
    }
}
