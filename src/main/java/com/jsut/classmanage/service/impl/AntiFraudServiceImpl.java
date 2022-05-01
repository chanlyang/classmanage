package com.jsut.classmanage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jsut.classmanage.common.YesOrNoEnum;
import com.jsut.classmanage.mapper.AntiFraudMapper;
import com.jsut.classmanage.mapper.PunchMapper;
import com.jsut.classmanage.mapper.StudentMapper;
import com.jsut.classmanage.model.AntiFraud;
import com.jsut.classmanage.model.Punch;
import com.jsut.classmanage.model.Student;
import com.jsut.classmanage.model.StudentNotice;
import com.jsut.classmanage.model.vo.AntiFraudVo;
import com.jsut.classmanage.model.vo.NoticeUserVo;
import com.jsut.classmanage.service.AntiFraudService;
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
 * @className AntiFraudServiceImpl
 **/
@Slf4j
@Service("antiFraudService")
public class AntiFraudServiceImpl extends ServiceImpl<AntiFraudMapper, AntiFraud> implements AntiFraudService {


    @Resource
    private AntiFraudMapper antiFraudMapper;
    @Resource
    private PunchMapper punchMapper;
    @Resource
    private StudentMapper studentMapper;


    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public AntiFraudVo queryTodayInfo(String userId) {

        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();

        AntiFraud antiFraud = antiFraudMapper.selectOne(new QueryWrapper<AntiFraud>().lambda()
                .gt(AntiFraud::getCreateTime, startTime)
                .lt(AntiFraud::getCreateTime, endTime));

        if (Objects.isNull(antiFraud)) {
            antiFraud = antiFraudMapper.selectOne(new QueryWrapper<AntiFraud>().lambda()
                    .orderByDesc(AntiFraud::getCreateTime).last("limit 1"));
        }
        Punch punch = punchMapper.selectOne(new QueryWrapper<Punch>().lambda()
                .eq(Punch::getUserId, userId)
                .eq(Punch::getAntiFraudId, antiFraud.getId()));


        AntiFraudVo antiFraudVo = new AntiFraudVo();
        antiFraudVo.setId(antiFraud.getId());
        antiFraudVo.setTitle(antiFraud.getTitle());
        antiFraudVo.setContent(antiFraud.getContent());
        antiFraudVo.setUserId(antiFraud.getUserId());
        antiFraudVo.setCreateTime(df.format(antiFraud.getCreateTime().toLocalDateTime()));
        antiFraudVo.setIsPunch(Objects.isNull(punch) ? YesOrNoEnum.NO.getValue() : YesOrNoEnum.YES.getValue());

        return antiFraudVo;
    }

    @Override
    public void userPunch(Long anitId, String userId) {
        log.info("反诈打卡:{}", anitId);
        Punch punch = new Punch();
        punch.setUserId(userId);
        punch.setAntiFraudId(anitId);
        punchMapper.insert(punch);
    }

    @Override
    public void publishAntiInfo(AntiFraud antiFraud) {
        log.info("发布反诈信息:{}", JSONObject.toJSONString(antiFraud));
        antiFraudMapper.insert(antiFraud);
    }

    @Override
    public PageUtils<AntiFraudVo> pageList(Integer pageNo, Integer size) {

        Page<AntiFraud> page = new Page<>(pageNo, size);
        IPage<AntiFraud> antiFraudIPage = antiFraudMapper.selectPage(page, new QueryWrapper<AntiFraud>().lambda()
                .orderByDesc(AntiFraud::getCreateTime));

        List<AntiFraudVo> result = Lists.transform(antiFraudIPage.getRecords(), it -> {
            AntiFraudVo antiFraudVo = new AntiFraudVo();
            antiFraudVo.setId(it.getId());
            antiFraudVo.setTitle(it.getTitle());
            antiFraudVo.setContent(it.getContent());
            antiFraudVo.setUserId(it.getUserId());
            antiFraudVo.setCreateTime(df.format(it.getCreateTime().toLocalDateTime()));
            return antiFraudVo;
        });

        return new PageUtils<AntiFraudVo>(result, Long.valueOf(antiFraudIPage.getTotal()).intValue(), Long.valueOf(antiFraudIPage.getSize()).intValue(), Long.valueOf(antiFraudIPage.getCurrent()).intValue());
    }

    @Override
    public PageUtils<NoticeUserVo> pageUserAntiInfo(Integer pageNo, Integer size, Long antiId) {

        log.info("学生学习打卡入参：{}", antiId);
        Page<Student> page = new Page<>(pageNo, size);
        IPage<Student> studentIPage = studentMapper.selectPage(page, new QueryWrapper<Student>().lambda().eq(Student::getIsDel, YesOrNoEnum.NO.getValue()));
        List<NoticeUserVo> userVoList = Lists.transform(studentIPage.getRecords(), it -> {
            NoticeUserVo noticeUserVo = new NoticeUserVo();
            noticeUserVo.setUserId(it.getUserId());
            noticeUserVo.setUserName(it.getUserName());
            Punch punch = punchMapper.selectOne(new QueryWrapper<Punch>().lambda()
                    .eq(Punch::getUserId, it.getUserId())
                    .eq(Punch::getAntiFraudId, antiId));
            noticeUserVo.setStatus(Objects.isNull(punch) ? YesOrNoEnum.NO.getValue() : YesOrNoEnum.YES.getValue());

            return noticeUserVo;

        });

        return new PageUtils<NoticeUserVo>(userVoList, Long.valueOf(studentIPage.getTotal()).intValue(), Long.valueOf(studentIPage.getTotal()).intValue(), Long.valueOf(studentIPage.getCurrent()).intValue());
    }
}
